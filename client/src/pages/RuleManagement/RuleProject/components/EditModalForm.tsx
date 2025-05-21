import { queryTypeList } from '@/services/resource/ResourceController';
import { saveRule } from '@/services/rule/RuleController';
import { RiskLevelList } from '@/utils/const';
import {
  ActionType,
  ModalForm,
  ProFormDependency,
  ProFormRadio,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-components';
import { useIntl, useModel } from '@umijs/max';
import { Empty, Form, FormInstance, Spin, message } from 'antd';
import { isEmpty } from 'lodash';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';

interface IEditFormProps {
  editFormVisible: boolean;
  setEditFormVisible: Dispatch<SetStateAction<boolean>>;
  groupInfo: Record<string, any>;
  tableActionRef: React.RefObject<ActionType | undefined>;
}

/**
 * New | Edit Rules.md
 * Note: Not yet used
 */

const EditModalForm: React.FC<IEditFormProps> = (props) => {
  // Global Props
  const { platformList, ruleGroupList } = useModel('rule');
  // Message API
  const [messageApi, contextHolder] = message.useMessage();
  // Form Instance
  const [form] = Form.useForm<FormInstance>();
  // Intl API
  const intl = useIntl();
  // List of Resource Types
  const [resourceList, setResourceList] = useState<any[]>([]);
  // Resource Type List Request Loading
  const [resourceListLoading, setResourceListLoading] =
    useState<boolean>(false);
  // Component Props
  const { editFormVisible, groupInfo, setEditFormVisible, tableActionRef } =
    props;

  const onClickFishEditForm = async (formData: any): Promise<void> => {
    const postBody = {
      ...formData,
    };
    if (groupInfo.id) postBody.id = groupInfo.id;
    const res: API.Result_String_ = await saveRule(postBody);
    if (res.msg === 'success' || [200].includes(res.code!)) {
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
      tableActionRef.current?.reloadAndRest?.();
    }
  };

  // List of corresponding resource types requested based on platform selection
  const requestResourceList = async (platform: string): Promise<void> => {
    if (!platform.trim()) return;
    setResourceListLoading(true);
    const res = await queryTypeList({ platform });
    setResourceListLoading(false);
    if (isEmpty(res.content)) {
      messageApi.error(
        intl.formatMessage({ id: 'cloudAccount.message.text.no.assets' }),
      );
      setResourceList([]);
    } else {
      setResourceList(res.content);
    }
  };

  useEffect((): void => {
    if (editFormVisible && !isEmpty(groupInfo)) {
      form.setFieldsValue({
        ...groupInfo,
      });
      if (groupInfo.platform) requestResourceList(groupInfo.platform);
    }
  }, [editFormVisible, groupInfo]);

  const initForm = (): void => {
    form.resetFields();
  };

  const onCancel = (): void => {
    setEditFormVisible(false);
    initForm();
  };

  return (
    <>
      {contextHolder}
      <ModalForm
        title={groupInfo.id ? '编辑规则' : '新建规则'}
        width={640}
        labelCol={{ span: 4 }}
        wrapperCol={{ span: 20 }}
        form={form}
        modalProps={{
          destroyOnClose: true,
          onCancel: () => onCancel(),
        }}
        layout={'horizontal'}
        open={editFormVisible}
        onFinish={onClickFishEditForm}
      >
        <ProFormSelect
          label="平台"
          name={'platform'}
          placeholder="请选择平台"
          rules={[{ required: true }]}
          options={platformList || []}
          onChange={async (value: string): Promise<void> => {
            await requestResourceList(value);
            // @ts-ignore
            form.setFieldValue('resourceType', null);
          }}
        />

        <ProFormDependency name={['platform']}>
          {({ platform }) => {
            if (isEmpty(platform)) return <></>;
            return (
              <ProFormSelect
                label="资产类型"
                name={'resourceType'}
                placeholder="请选择资产类型"
                options={resourceList || []}
                fieldProps={{
                  fieldNames: {
                    label: 'resourceName',
                    value: 'resourceType',
                  },
                  showSearch: true,
                  filterOption: true,
                  notFoundContent: resourceListLoading ? (
                    <Spin size="small" />
                  ) : (
                    <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
                  ),
                }}
                rules={[{ required: true }]}
              />
            );
          }}
        </ProFormDependency>

        <ProFormSelect
          label="规则组"
          name={'ruleGroupId'}
          placeholder="请选择规则组"
          rules={[{ required: true }]}
          options={ruleGroupList || []}
        />

        <ProFormText
          name="ruleName"
          label="规则名称"
          rules={[{ required: true }]}
          placeholder="请输入规则名称"
        />
        <ProFormText
          name="ruleDesc"
          label="规则描述"
          rules={[{ required: true }]}
          placeholder="请输入规则描述"
        />

        <ProFormRadio.Group
          name="riskLevel"
          label="风险等级"
          rules={[{ required: true }]}
          initialValue={'Low'}
          options={
            RiskLevelList?.map((item) => ({
              label: item.text,
              value: item.value,
            })) || []
          }
        />

        <ProFormTextArea
          name="advice"
          label="修复建议"
          placeholder="请输入修复建议"
        />

        <ProFormText
          name="link"
          label="参考链接"
          placeholder="请输入参考链接"
          rules={[
            {
              pattern: new RegExp(
                '^(https?|ftp):\\/\\/[^\\s/$.?#].[^\\s]*$',
                'i',
              ),
              message: '请输入正确的链接',
            },
          ]}
        />
      </ModalForm>
    </>
  );
};

export default EditModalForm;
