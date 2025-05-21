import { RegoEditor } from '@/components/Editor';
import { saveRule } from '@/services/rule/RuleController';
import { listGlobalVariableConfig } from '@/services/variable/GlobalVariableConfigCroller';
import {
  ActionType,
  ProCard,
  ProColumns,
  ProTable,
} from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Button, Drawer, Flex, Space, message } from 'antd';
import { isEmpty } from 'lodash';
import React, { Dispatch, SetStateAction, useRef, useState } from 'react';

interface INoteDrawer {
  formData: Record<string, any>;
  variableDrawerVisible: boolean;
  setCurrent: Dispatch<SetStateAction<number>>;
  setVariableDrawerVisible: Dispatch<SetStateAction<boolean>>;
  variableDrawerInfo: Record<string, any>;
  requestRuleDetailById: (id: number) => Promise<any>;
  selectedRowKeys: any[];
  setSelectedRowKeys: Dispatch<SetStateAction<Array<any>>>;
}

// Variable information
const VariableDrawer: React.FC<INoteDrawer> = (props) => {
  // Component Props
  const {
    variableDrawerVisible,
    setVariableDrawerVisible,
    variableDrawerInfo = {},
    requestRuleDetailById,
    selectedRowKeys = [],
    setSelectedRowKeys,
    formData,
    setCurrent,
  } = props;

  const { id } = variableDrawerInfo;

  // Message API
  const [messageApi, contextHolder] = message.useMessage();
  // Intl API
  const intl = useIntl();
  // Add Loading
  const [addLoading, setAddLoading] = useState<boolean>(false);

  const initDrawer = (): void => {
    setVariableDrawerVisible(false);
    setSelectedRowKeys(formData?.globalVariableConfigIdList || []);
  };

  // Close Drawer
  const onClickCloseDrawerForm = (): void => {
    initDrawer();
  };

  // Batch Add
  const onClickBatchAdd = async (): Promise<void> => {
    // Edit
    if (id) {
      setAddLoading(true);
      const postBody: any = {
        id,
        globalVariableConfigIdList: selectedRowKeys,
        resourceType: formData?.resourceType,
        ruleTypeIdList: formData.ruleTypeIdList,
      };
      const res: API.Result_String_ = await saveRule(postBody);
      setAddLoading(false);
      if (res.msg === 'success' || [200].includes(res.code!)) {
        messageApi.success(
          intl.formatMessage({ id: 'common.message.text.batch.add.success' }),
        );
        await requestRuleDetailById(id);
        setCurrent(1);
        setVariableDrawerVisible(false);
      }
    } else {
      // Newly added
      setVariableDrawerVisible(false);
    }
  };

  // Cancel Add
  const onClickCancelAdd = async () => {
    initDrawer();
  };

  // Current Table Instance
  const tableActionRef = useRef<ActionType>();

  const columns: ProColumns<API.GlobalVariableConfigInfo, 'text'>[] = [
    {
      title: intl.formatMessage({
        id: 'cloudAccount.extend.title.createAndUpdateTime',
      }),
      dataIndex: 'gmtCreated',
      valueType: 'text',
      align: 'left',
      hideInSearch: true,
      render: (_, record) => {
        return (
          <div>
            <section>{record?.gmtCreate || '-'}</section>
            <section>{record?.gmtModified || '-'}</section>
          </div>
        );
      },
    },
    {
      title: intl.formatMessage({
        id: 'rule.input.text.rule.group.creator',
      }),
      dataIndex: 'username',
      valueType: 'text',
      align: 'left',
      width: 200,
      hideInSearch: true,
    },
    {
      title: intl.formatMessage({
        id: 'rule.module.text.variable.name',
      }),
      dataIndex: 'name',
      valueType: 'text',
      align: 'left',
      width: 150,
    },
    {
      title: intl.formatMessage({
        id: 'rule.module.text.variable.path',
      }),
      dataIndex: 'path',
      valueType: 'text',
      align: 'center',
    },
    {
      title: intl.formatMessage({
        id: 'rule.module.text.reference.rules',
      }),
      dataIndex: 'ruleNameList',
      key: 'ruleNameList',
      hideInSearch: true,
      render: (_, record) => (
        <ul style={{ paddingInlineStart: 16 }}>
          {record?.ruleNameList?.map((ruleName, index) => (
            <li key={index}>{ruleName}</li>
          ))}
        </ul>
      ),
    },
  ];

  const requestTableList = async (
    params: Record<string, any>,
  ): Promise<any> => {
    const { name, path, current, pageSize } = params;
    const postBody = {
      name,
      path,
      page: current!,
      size: pageSize!,
    };
    const { content, code } = await listGlobalVariableConfig(postBody);
    return {
      data: content?.data || [],
      total: content?.total || 0,
      success: code === 200 || false,
    };
  };

  const onSelectChange = (newSelectedRowKeys: React.Key[]): void => {
    setSelectedRowKeys(newSelectedRowKeys);
  };

  const rowSelection = {
    selectedRowKeys,
    onChange: onSelectChange,
    preserveSelectedRowKeys: true,
  };

  return (
    <Drawer
      footer={
        <Flex style={{ width: '100%' }} justify={'end'}>
          <Space>
            <Button type="dashed" onClick={() => onClickCancelAdd()}>
              {intl.formatMessage({
                id: 'common.button.text.cancel',
              })}
            </Button>
            <Button
              loading={addLoading}
              type="primary"
              onClick={() => onClickBatchAdd()}
            >
              {intl.formatMessage({
                id: 'common.button.text.save',
              })}
            </Button>
          </Space>
        </Flex>
      }
      title={intl.formatMessage({
        id: 'rule.extend.text.variable.information',
      })}
      width={'64%'}
      destroyOnClose
      open={variableDrawerVisible}
      onClose={onClickCloseDrawerForm}
      styles={{
        body: {
          padding: 8,
        },
      }}
    >
      {contextHolder}
      <ProTable
        scroll={{ x: true }}
        options={false}
        rowKey={'id'}
        columns={columns}
        actionRef={tableActionRef}
        request={requestTableList}
        pagination={{
          showQuickJumper: false,
          showSizeChanger: true,
          defaultPageSize: 10,
          defaultCurrent: 1,
        }}
        search={{
          // Special Treatment
          labelWidth: intl.locale === 'en-US' ? 110 : 80,
        }}
        rowSelection={rowSelection}
        expandable={{
          expandRowByClick: true,
          expandedRowRender: (record: { [key: string]: any }) => (
            <ProCard direction="column" gutter={[0, 16]}>
              {!isEmpty(record?.data) && (
                <ProCard ghost>
                  <p>
                    {intl.formatMessage({
                      id: 'rule.module.text.variable.value',
                    })}
                  </p>
                  <RegoEditor
                    readOnly={true}
                    editorKey="dataEditor"
                    value={record?.data}
                    editorStyle={{ height: '280px', maxHeight: '280px' }}
                  />
                </ProCard>
              )}
            </ProCard>
          ),
          rowExpandable: (record: { [key: string]: any }) =>
            !isEmpty(record?.data),
        }}
      />
    </Drawer>
  );
};

export default VariableDrawer;
