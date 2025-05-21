import NoticeModalForm from '@/pages/PivotManagement/InvolveModule/components/NoticeModalForm';
import RealTimeModalForm from '@/pages/PivotManagement/InvolveModule/components/RealTimeModalForm';
import TimingModalForm from '@/pages/PivotManagement/InvolveModule/components/TimingModalForm';
import { CHECK_BOX_OPTIONS_LIST } from '@/pages/PivotManagement/InvolveModule/utils/const';
import {
  querySubscriptionDetailById,
  saveSubscriptionConfig,
} from '@/services/Involve/involveController';
import { valueListAsValueEnum } from '@/utils/shared';
import { PlusOutlined } from '@ant-design/icons';
import {
  ActionType,
  EditableFormInstance,
  EditableProTable,
  ProCard,
  ProColumns,
  ProForm,
  ProFormDependency,
  ProFormInstance,
  ProFormText,
} from '@ant-design/pro-components';
import { useIntl, useModel, useRequest } from '@umijs/max';
import { Button, Drawer, Flex, Popconfirm, Spin, message } from 'antd';
import { cloneDeep, isEmpty } from 'lodash';
import React, {
  Dispatch,
  SetStateAction,
  useEffect,
  useRef,
  useState,
} from 'react';

interface IEvaluateDrawerProps {
  involveDrawerVisible: boolean;
  setInvolveDrawerVisible: Dispatch<SetStateAction<boolean>>;
  involveDrawerInfo: Record<string, any>;
  tableActionRef: React.RefObject<ActionType | undefined>;
}

type RuleDataSourceType = {
  idx: React.Key;
  key?: string;
  keyName?: string;
  operator?: string;
  value?: string;
  children?: RuleDataSourceType[];
};

type ActionDataSourceType = {
  idx: React.Key;
  name?: 'timing' | 'realtime'; // Timed | Real time notifications
  action?: string;
  actionType?: string;
  children?: RuleDataSourceType[];
};

// Subscription configuration
const InvolveDrawer: React.FC<IEvaluateDrawerProps> = (props) => {
  const { subConfigList } = useModel('involve');
  const {
    involveDrawerVisible,
    setInvolveDrawerVisible,
    involveDrawerInfo,
    tableActionRef,
  } = props;
  const { id } = involveDrawerInfo;
  // Message Instance
  const [messageApi, contextHolder] = message.useMessage();
  // Intl API
  const intl = useIntl();
  // ProForm Instance
  const formRef = useRef<ProFormInstance<any>>();
  // Conditional Configuration ActionRef
  const actionRef = useRef<ActionType>();
  // Conditional configuration editable line key
  const [editableKeys, setEditableRowKeys] = useState<React.Key[]>([]);
  // Conditional configuration FormRef
  const editorFormRef = useRef<EditableFormInstance<RuleDataSourceType>>();
  // Action configuration FormRef
  const actionFormRef = useRef<EditableFormInstance<ActionDataSourceType>>();
  // Submit button Loading
  const [submitLoading, setSubmitLoading] = useState<boolean>(false);
  // Add action
  const [noticeFormVisible, setNoticeFormVisible] = useState<boolean>(false);
  // Scheduled notification
  const [timingFormVisible, setTimingFormVisible] = useState<boolean>(false);
  // Real time notification
  const [realtimeFormVisible, setRealtimeFormVisible] =
    useState<boolean>(false);
  // Notification pop-up window MessageRef
  const notificationRef = useRef<any>({});

  const initDrawer = (): void => {
    formRef.current?.resetFields();
  };

  const onClickCloseDrawerForm = (): void => {
    setInvolveDrawerVisible(false);
  };

  const onClickAddAction = (): void => {
    setNoticeFormVisible(true);
  };

  // Format FormData parameters
  const serializeData = (formData: Record<string, any>) => {
    const data = cloneDeep(formData);
    const { ruleConfigList, actionList } = data;
    // Format the value of the ruleConfig List field
    if (Array.isArray(ruleConfigList) && !isEmpty(ruleConfigList)) {
      const ruleConfigArray = ruleConfigList.map((item, i) => {
        // eslint-disable-next-line
        const { idx, ...reset } = item;
        return {
          ...reset,
          id: i + 1,
        };
      });
      data.ruleConfigList = ruleConfigArray;
    }
    // Format the value of the actionList field
    if (Array.isArray(actionList) && !isEmpty(actionList)) {
      const actionArray = actionList.map((item) => {
        // eslint-disable-next-line
        const { idx, ...reset } = item;
        return {
          ...reset,
        };
      });
      data.actionList = actionArray;
    }
    return data;
  };

  // Reformat FormData parameter (assignment)
  const deserializeData = (formData: Record<string, any>) => {
    const data: Record<string, any> = cloneDeep(formData);
    const { ruleConfig, actionList } = data;
    const editableKeyList: Array<number> = [];
    // Format the value of the ruleConfig List field
    if (Array.isArray(ruleConfig) && !isEmpty(ruleConfig)) {
      const ruleConfigArray = ruleConfig.map((item) => {
        // eslint-disable-next-line
        const { id, ...reset } = item;
        editableKeyList.push(id);
        return {
          ...reset,
          idx: id,
        };
      });
      data.ruleConfigList = ruleConfigArray;
    }
    data.editableKeyList = editableKeyList;
    // Format the value of the actionList field
    if (Array.isArray(actionList) && !isEmpty(actionList)) {
      const actionArray = actionList.map((item, i) => {
        return {
          ...item,
          idx: i + 1,
        };
      });
      data.actionList = actionArray;
    }
    return data;
  };

  // Submit subscription information
  const onClickFinishForm = async (
    formData: Record<string, any>,
  ): Promise<void> => {
    setSubmitLoading(true);
    const postBody = serializeData(formData);
    if (id) postBody.id = id;
    setSubmitLoading(false);
    const r = await saveSubscriptionConfig(postBody);
    if (r.code === 200 && r.msg === 'success') {
      if (id) {
        messageApi.success(
          intl.formatMessage({ id: 'common.message.text.edit.success' }),
        );
      } else {
        messageApi.success(
          intl.formatMessage({ id: 'common.message.text.add.success' }),
        );
      }
      setInvolveDrawerVisible(false);
      tableActionRef?.current?.reloadAndRest?.();
    }
  };

  // Detecting detailed data
  const {
    loading: subscriptionInfoLoading,
    run: requestSubscriptionDetailById,
  } = useRequest(
    (id) => {
      return querySubscriptionDetailById({ id: id });
    },
    {
      manual: true,
      formatResult: (r: any) => {
        const { content } = r;
        return deserializeData(content) || {};
      },
      onSuccess: (data): void => {
        const { actionList, condition, name, ruleConfigList, editableKeyList } =
          data;
        formRef.current?.setFieldsValue({
          actionList,
          condition,
          name,
          ruleConfigList,
        });
        // Set existing conditions to editable state
        setEditableRowKeys(editableKeyList);
      },
    },
  );

  useEffect((): void => {
    if (involveDrawerVisible && id) {
      requestSubscriptionDetailById(id);
    } else if (!involveDrawerVisible) {
      initDrawer();
    }
  }, [involveDrawerVisible]);

  // Click on Settings
  const onClickSetNotification = (record: ActionDataSourceType): void => {
    if (record.actionType === 'timing') {
      // Scheduled notification
      notificationRef.current = { ...record };
      setTimingFormVisible(true);
    } else if (record.actionType === 'realtime') {
      // Real time notification
      notificationRef.current = { ...record };
      setRealtimeFormVisible(true);
    }
  };

  const ruleConfigColumns: ProColumns<RuleDataSourceType>[] = [
    {
      title: intl.formatMessage({
        id: 'involve.extend.title.serial.number',
      }),
      dataIndex: 'id',
      editable: false,
      width: 100,
      align: 'center',
      render: (_, record, index: number) => {
        return index + 1;
      },
    },
    {
      title: intl.formatMessage({
        id: 'rule.module.text.variable.name',
      }),
      dataIndex: 'key',
      formItemProps: {
        rules: [
          {
            required: true,
          },
        ],
      },
      valueType: 'select',
      fieldProps: (_, { rowIndex }) => {
        const array =
          subConfigList?.map((item) => ({
            label: item.keyName,
            value: item.key,
          })) || [];
        return {
          options: array,
          allowClear: false,
          onSelect: (): void => {
            // Reset [Relationship Selection, Variable Values] parameters every time selected
            editorFormRef.current?.setRowData?.(rowIndex, {
              operator: undefined,
              value: undefined,
            });
          },
        };
      },
    },
    {
      title: intl.formatMessage({
        id: 'involve.extend.title.condition',
      }),
      dataIndex: 'operator',
      formItemProps: {
        rules: [
          {
            required: true,
          },
        ],
      },
      valueType: 'select',
      fieldProps: (form, { rowIndex }) => {
        const key = editorFormRef.current?.getRowData?.(rowIndex)?.key;
        if (!key) return [];
        const array = subConfigList?.find((item) => item.key === key);
        const optionList = array?.operatorList.map((item: string) => ({
          label: item,
          value: item,
        }));
        return { options: optionList || [] };
      },
    },
    {
      title: intl.formatMessage({
        id: 'rule.module.text.variable.value',
      }),
      dataIndex: 'value',
      formItemProps: {
        rules: [
          {
            required: true,
          },
        ],
      },
      valueType: 'select',
      fieldProps: (form, { rowIndex }) => {
        const key = editorFormRef.current?.getRowData?.(rowIndex)?.key;
        if (!key) return [];
        const array = subConfigList?.find((item) => item.key === key);
        const optionList = array?.value.map((item: Record<string, any>) => ({
          label: item?.valueName,
          value: item?.value,
        }));
        return {
          showSearch: true,
          options: optionList || [],
        };
      },
    },
    {
      title: intl.formatMessage({
        id: 'cloudAccount.extend.title.cloud.operate',
      }),
      valueType: 'option',
      width: 140,
      render: () => [],
    },
  ];

  const actionConfigColumns: ProColumns<ActionDataSourceType>[] = [
    {
      title: intl.formatMessage({
        id: 'involve.extend.title.serial.number',
      }),
      dataIndex: 'id',
      editable: false,
      width: 100,
      align: 'center',
      render: (_, record, index: number) => {
        return index + 1;
      },
    },
    {
      title: intl.formatMessage({
        id: 'involve.extend.title.action.title',
      }),
      dataIndex: 'actionType',
      editable: false,
      align: 'center',
      valueType: 'select',
      valueEnum: valueListAsValueEnum(CHECK_BOX_OPTIONS_LIST),
    },
    {
      title: intl.formatMessage({
        id: 'involve.extend.title.action.content',
      }),
      dataIndex: 'action',
      editable: false,
      align: 'center',
      render: (_, record: ActionDataSourceType) => {
        return (
          <Button
            type={'link'}
            size={'small'}
            style={{ fontSize: 12 }}
            onClick={() => onClickSetNotification(record)}
          >
            {record?.action
              ? intl.formatMessage({
                  id: 'involve.button.text.click.edit',
                })
              : intl.formatMessage({
                  id: 'involve.button.text.click.setting',
                })}
          </Button>
        );
      },
    },
    {
      title: intl.formatMessage({
        id: 'cloudAccount.extend.title.cloud.operate',
      }),
      valueType: 'option',
      width: 200,
      render: (text, record) => [
        <Popconfirm
          key={'action'}
          title={intl.formatMessage({
            id: 'common.button.text.delete.confirm',
          })}
          onConfirm={(): void => {
            const actionList = formRef?.current?.getFieldValue('actionList');
            const array = actionList.filter(
              (item: ActionDataSourceType) => item.idx !== record.idx,
            );
            // 注意: 添加和删除时更新idx值
            const initIdxArray = array.map(
              (elem: ActionDataSourceType, i: number) => ({
                ...elem,
                idx: i + 1,
              }),
            );
            formRef?.current?.setFieldValue('actionList', initIdxArray);
          }}
          okText={intl.formatMessage({
            id: 'common.button.text.ok',
          })}
          cancelText={intl.formatMessage({
            id: 'common.button.text.cancel',
          })}
        >
          <Button type={'link'} danger key="delete">
            {intl.formatMessage({
              id: 'common.button.text.delete',
            })}
          </Button>
        </Popconfirm>,
      ],
    },
  ];

  return (
    <Drawer
      title={
        id
          ? intl.formatMessage({
              id: 'involve.extend.title.edit',
            })
          : intl.formatMessage({
              id: 'involve.extend.title.add',
            })
      }
      width={'64%'}
      destroyOnClose
      open={involveDrawerVisible}
      onClose={onClickCloseDrawerForm}
    >
      {contextHolder}
      <Spin spinning={subscriptionInfoLoading}>
        <ProForm<{
          ruleConfigList: RuleDataSourceType[];
        }>
          onFinish={onClickFinishForm}
          formRef={formRef}
          layout={'horizontal'}
          submitter={{
            submitButtonProps: {
              loading: submitLoading,
            },
            render: (props, dom) => (
              <Flex justify={'end'} gap="small">
                <Button onClick={() => onClickCloseDrawerForm()}>
                  {intl.formatMessage({
                    id: 'common.button.text.cancel',
                  })}
                </Button>
                {dom}
              </Flex>
            ),
          }}
        >
          <ProFormText
            label={intl.formatMessage({
              id: 'involve.module.text.involve.title',
            })}
            name={'name'}
            formItemProps={{
              rules: [
                {
                  required: true,
                },
              ],
            }}
          />

          <ProCard
            style={{ marginBottom: 20 }}
            bodyStyle={{
              backgroundColor: 'rgb(245, 245, 245)',
              padding: '0 20px 16px 20px',
            }}
          >
            <EditableProTable<RuleDataSourceType>
              rowKey="idx"
              name="ruleConfigList"
              actionRef={actionRef}
              editableFormRef={editorFormRef}
              headerTitle={
                <div style={{ fontSize: 14 }}>
                  {intl.formatMessage({
                    id: 'involve.extend.title.conditional.config',
                  })}
                </div>
              }
              recordCreatorProps={{
                creatorButtonText: intl.formatMessage({
                  id: 'involve.extend.title.addRowConfig',
                }),
                record: () => ({
                  idx: (Math.random() * 1000000).toFixed(0),
                }),
              }}
              onChange={() =>
                formRef.current?.setFieldValue('condition', undefined)
              }
              columns={ruleConfigColumns}
              editable={{
                type: 'multiple',
                editableKeys,
                onChange: setEditableRowKeys,
                actionRender: (row, config, defaultDom) => {
                  return [defaultDom.delete];
                },
              }}
            />

            <ProFormDependency name={['ruleConfigList']}>
              {({ ruleConfigList }) => {
                return (
                  <ProFormText
                    label={intl.formatMessage({
                      id: 'involve.extend.title.logical',
                    })}
                    name={'condition'}
                    fieldProps={{
                      placeholder: intl.formatMessage({
                        id: 'involve.extend.title.logical.example',
                      }),
                      style: {
                        maxWidth: '64%',
                      },
                    }}
                    rules={[
                      {
                        required: ruleConfigList?.length > 1,
                      },
                    ]}
                  />
                );
              }}
            </ProFormDependency>

            <Button
              type={'primary'}
              onClick={() => onClickAddAction()}
              style={{ fontSize: 13, padding: '4px 10px', gap: 2 }}
            >
              <PlusOutlined />
              {intl.formatMessage({
                id: 'involve.extend.title.addAction',
              })}
            </Button>

            {/* Note: The main purpose of using the EditableProTable component is to maintain linkage with the values of the outer form component */}
            <EditableProTable<ActionDataSourceType>
              rowKey="idx"
              name="actionList"
              editableFormRef={actionFormRef}
              headerTitle={
                <div style={{ fontSize: 14 }}>
                  {intl.formatMessage({
                    id: 'involve.extend.title.action.config',
                  })}
                </div>
              }
              columns={actionConfigColumns}
              recordCreatorProps={false} // Cancel adding a line operation
            />
          </ProCard>
        </ProForm>
      </Spin>

      <NoticeModalForm // Add action
        noticeFormVisible={noticeFormVisible}
        setNoticeFormVisible={setNoticeFormVisible}
        formRef={formRef}
      />

      <TimingModalForm // Scheduled notification
        notificationInfo={notificationRef.current}
        timingFormVisible={timingFormVisible}
        setTimingFormVisible={setTimingFormVisible}
        formRef={formRef}
      />

      <RealTimeModalForm // Real time notification
        notificationInfo={notificationRef.current}
        realtimeFormVisible={realtimeFormVisible}
        setRealtimeFormVisible={setRealtimeFormVisible}
        formRef={formRef}
      />
    </Drawer>
  );
};

export default InvolveDrawer;
