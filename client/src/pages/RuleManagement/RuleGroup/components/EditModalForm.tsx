import DispositionPro from '@/components/DispositionPro';
import {
  queryRuleGroupDetail,
  saveRuleGroup,
} from '@/services/rule/RuleController';
import { obtainPlatformEasyIcon } from '@/utils/shared';
import {
  ModalForm,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-components';
import { useIntl, useModel, useRequest } from '@umijs/max';
import type { TransferProps } from 'antd';
import { Form, FormInstance, message } from 'antd';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import TableTransfer, { TableTransferProps } from './TableTransfer';

interface IEditFormProps {
  editFormVisible: boolean;
  setEditFormVisible: Dispatch<SetStateAction<boolean>>;
  groupInfo: Record<string, any>;
  requestCurrentData: () => Promise<void>;
}

const filterOption = (
  input: string,
  item: { [key: string]: string },
): boolean => item.ruleName?.includes(input);

// New | Edit Rule Group
const EditModalForm: React.FC<IEditFormProps> = (props) => {
  // Platform Rule Group List
  const { platformList, allRuleList } = useModel('rule');
  // Message API
  const [messageApi, contextHolder] = message.useMessage();
  // Form Instance
  const [form] = Form.useForm<FormInstance>();
  // Intl API
  const intl = useIntl();
  // Component Props
  const { editFormVisible, groupInfo, setEditFormVisible, requestCurrentData } =
    props;
  // Table Columns
  const columns = [
    {
      dataIndex: 'platform',
      title: intl.formatMessage({
        id: 'common.table.columns.platform',
      }),
      render: (_: any, record: { [key: string]: any }) => {
        return obtainPlatformEasyIcon(record.platform!, platformList);
      },
    },
    {
      dataIndex: 'ruleName',
      title: intl.formatMessage({
        id: 'home.module.inform.columns.ruleName',
      }),
      render: (_: any, record: { [key: string]: any }) => (
        <DispositionPro
          rows={1}
          tooltipText={
            <div>
              <div>
                {intl.formatMessage({
                  id: 'rule.module.text.name',
                })}
                &nbsp;:&nbsp; {record?.ruleName || '-'}
              </div>
              <div>
                {intl.formatMessage({
                  id: 'rule.module.text.describe',
                })}
                &nbsp;:&nbsp; {record?.ruleDesc || '-'}
              </div>
            </div>
          }
          text={record?.ruleName}
          maxWidth={180}
        />
      ),
    },
  ];
  // About Rule Keys
  const [targetKeys, setTargetKeys] = useState<TransferProps['targetKeys']>([]);
  // About Rule Keys Change
  const onChange: TableTransferProps['onChange'] = (nextTargetKeys) => {
    setTargetKeys(nextTargetKeys);
  };
  // Submit Form
  const onClickFishEditForm = async (formData: any) => {
    const postBody = {
      ...formData,
      ruleIdList: targetKeys || [],
    };
    if (groupInfo.id) postBody.id = groupInfo.id;
    const res: API.Result_String_ = await saveRuleGroup(postBody);
    if (res.msg === 'success') {
      if (groupInfo.id) {
        messageApi.success(
          intl.formatMessage({ id: 'common.message.text.edit.success' }),
        );
      } else {
        messageApi.success(
          intl.formatMessage({ id: 'common.message.text.create.success' }),
        );
      }
      setEditFormVisible(false);
      await requestCurrentData?.();
    }
  };

  const { run: requestRuleGroupDetail, loading: ruleGroupDetailLoading }: any =
    useRequest(
      (id: number) => {
        return queryRuleGroupDetail({ id });
      },
      {
        manual: true,
        formatResult: (r: any) => {
          const aboutRuleIdList = r?.content?.aboutRuleList?.map(
            (item: { [key: string]: any }) => item.id,
          );
          return {
            ...(r?.content || {}),
            aboutRuleIdList,
          };
        },
        onSuccess: (r): void => {
          form.setFieldsValue({
            // @ts-ignore
            groupName: r?.groupName,
            groupDesc: r?.groupDesc,
          });
          setTargetKeys(r?.aboutRuleIdList || []);
        },
      },
    );

  useEffect((): void => {
    if (editFormVisible && groupInfo.id) requestRuleGroupDetail(groupInfo.id);
  }, [editFormVisible, groupInfo]);

  const onCancel = (): void => {
    setEditFormVisible(false);
  };

  return (
    <>
      {contextHolder}
      <ModalForm
        title={
          groupInfo.id
            ? intl.formatMessage({
                id: 'rule.extend.group.title.edit',
              })
            : intl.formatMessage({
                id: 'rule.extend.group.title.add',
              })
        }
        width={intl.locale === 'en-US' ? 860 : 780}
        form={form}
        modalProps={{
          destroyOnClose: true,
          onCancel: () => onCancel(),
          styles: {
            body: {
              paddingTop: 12,
            },
          },
          afterClose: (): void => {
            form.resetFields();
            setTargetKeys([]);
          },
        }}
        layout={'horizontal'}
        open={editFormVisible}
        onFinish={onClickFishEditForm}
      >
        <ProFormText
          name="groupName"
          label={intl.formatMessage({
            id: 'rule.input.text.rule.group.name',
          })}
          rules={[{ required: true }]}
        />
        <ProFormTextArea
          name="groupDesc"
          label={intl.formatMessage({
            id: 'rule.input.text.rule.group.describe',
          })}
          rules={[{ required: true }]}
        />

        <Form.Item
          label={intl.formatMessage({
            id: 'rule.input.text.transfer',
          })}
        >
          <TableTransfer
            loading={ruleGroupDetailLoading}
            dataSource={allRuleList}
            targetKeys={targetKeys}
            showSearch
            showSelectAll={false}
            onChange={onChange}
            filterOption={filterOption}
            leftColumns={columns}
            rightColumns={columns}
          />
        </Form.Item>
      </ModalForm>
    </>
  );
};

export default EditModalForm;
