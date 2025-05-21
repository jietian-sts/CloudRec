import { IValueType } from '@/utils/const';
import { ExportOutlined } from '@ant-design/icons';
import {
  ActionType,
  EditableFormInstance,
  EditableProTable,
  ProCard,
  ProColumns,
  ProForm,
} from '@ant-design/pro-components';
import { FormattedMessage, useIntl } from '@umijs/max';
import { Button, Cascader, Drawer, Flex, Space } from 'antd';
import { cloneDeep, isEmpty } from 'lodash';
import React, {
  Dispatch,
  SetStateAction,
  useEffect,
  useRef,
  useState,
} from 'react';
const { SHOW_CHILD } = Cascader;

interface linkModalProps {
  linkDrawerVisible: boolean;
  setLinkDrawerVisible: Dispatch<SetStateAction<boolean>>;
  linkFormRef: any;
  resourceTypeList: Array<any>;
  form: any;
  formData: { [key: string]: string };
  setFormData: Dispatch<SetStateAction<any>>;
}

type linkDataSourceType = {
  idx: React.Key;
  key?: string;
  keyName?: string;
  operator?: string;
  value?: string;
  children?: linkDataSourceType[];
};

// Associative Mode
const AssociativeModeList: Array<IValueType> = [
  {
    label: <FormattedMessage id={'rule.module.text.only.associate.once'} />,
    value: '仅关联一次',
  },
  {
    label: <FormattedMessage id={'rule.module.text.related.multiple.times'} />,
    value: '关联多次',
  },
  {
    label: <FormattedMessage id={'rule.module.text.no.associated.fields'} />,
    value: '无关联字段',
  },
];

// Related assets
const LinkDrawerForm: React.FC<linkModalProps> = (props) => {
  // Component Props
  const {
    linkDrawerVisible,
    setLinkDrawerVisible,
    linkFormRef,
    resourceTypeList,
    form,
    formData,
    setFormData,
  } = props;
  // Intl API
  const intl = useIntl();
  // Conditional configuration actionRef
  const actionRef = useRef<ActionType>();
  // Conditional configuration editable line key
  const [editableKeys, setEditableRowKeys] = useState<React.Key[]>([]);
  // Conditional configuration FormRef
  const editorFormRef = useRef<EditableFormInstance<linkDataSourceType>>();

  // Init
  const initDrawer = (): void => {
    linkFormRef.current?.resetFields();
  };

  const onClickCloseModalForm = (): void => {
    setLinkDrawerVisible(false);
  };

  // Format FormData parameters
  const serializeData = (formData: Record<string, any>) => {
    const data = cloneDeep(formData) || {};
    const { linkedDataList } = data;
    // Format linkedDataArray field values
    if (Array.isArray(linkedDataList) && !isEmpty(linkedDataList)) {
      const linkedDataArray = linkedDataList.map((item) => {
        // eslint-disable-next-line
        const { idx, map_row_parentKey, ...reset } = item;
        return {
          ...reset,
        };
      });
      data.linkedDataList = linkedDataArray;
    }
    return data;
  };

  // Reformat FormData parameter (assignment)
  const deserializeData = (formData: Record<string, any>) => {
    const data: Record<string, any> = cloneDeep(formData) || {};
    const linkedDataList = data;
    const editableKeyList: Array<number> = [];
    // Format the value of the ruleConfig List field
    if (Array.isArray(linkedDataList) && !isEmpty(linkedDataList)) {
      const linkedDataArray = linkedDataList?.map((item, i) => {
        editableKeyList.push(i);
        return {
          ...item,
          idx: i,
        };
      });
      data.linkedDataList = linkedDataArray;
    }
    data.editableKeyList = editableKeyList;
    return data;
  };

  // Related assets
  const onClickFinishForm = async (): Promise<void> => {
    linkFormRef.current
      ?.validateFields()
      .then((values: Record<string, any>): void => {
        const linkedDataList = serializeData(values)?.linkedDataList || [];
        form.setFieldValue('linkedDataList', linkedDataList);
        setFormData({
          ...formData,
          linkedDataList,
        });
        onClickCloseModalForm();
      });
  };

  useEffect((): void => {
    if (linkDrawerVisible) {
      const formData = form.getFieldValue('linkedDataList');
      const { linkedDataList, editableKeyList } = deserializeData(formData);
      if (!isEmpty(linkedDataList) && !isEmpty(editableKeyList)) {
        linkFormRef.current?.setFieldsValue({
          linkedDataList,
        });
        // Set existing conditions to editable state
        setEditableRowKeys(editableKeyList);
      }
    } else {
      initDrawer();
    }
  }, [linkDrawerVisible]);

  const linkConfigColumns: ProColumns<linkDataSourceType>[] = [
    {
      title: intl.formatMessage({ id: 'involve.extend.title.serial.number' }),
      dataIndex: 'id',
      editable: false,
      width: 60,
      align: 'center',
      render: (_, record, index: number) => {
        return index + 1;
      },
    },
    {
      title: intl.formatMessage({ id: 'rule.module.text.associative.mode' }),
      width: 200,
      dataIndex: 'associativeMode',
      formItemProps: {
        rules: [
          {
            required: false,
          },
        ],
      },
      valueType: 'select',
      fieldProps: {
        options: AssociativeModeList,
        multiple: false,
        allowClear: true,
        showSearch: true,
      },
    },
    {
      title: intl.formatMessage({
        id: 'rule.module.text.main.asset.associated.fields',
      }),
      dataIndex: 'linkedKey1',
      formItemProps: {
        rules: [
          {
            required: false,
          },
        ],
      },
    },
    {
      title: intl.formatMessage({
        id: 'rule.module.text.related.asset.type',
      }),
      width: 200,
      dataIndex: 'resourceType',
      formItemProps: {
        rules: [
          {
            required: true,
          },
        ],
      },
      valueType: 'cascader',
      fieldProps: {
        options: resourceTypeList,
        multiple: false,
        allowClear: true,
        showSearch: true,
        showCheckedStrategy: SHOW_CHILD,
      },
    },
    {
      title: intl.formatMessage({
        id: 'rule.module.text.associated.assets.associated.fields',
      }),
      dataIndex: 'linkedKey2',
      formItemProps: {
        rules: [
          {
            required: false,
          },
        ],
      },
    },
    {
      title: intl.formatMessage({
        id: 'rule.module.text.mount.field.name',
      }),
      dataIndex: 'newKeyName',
      formItemProps: {
        rules: [
          {
            required: true,
          },
        ],
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

  return (
    <Drawer
      title={
        <Flex align={'center'}>
          <span style={{ marginRight: 4 }}>
            {intl.formatMessage({
              id: 'rule.module.text.related.assets',
            })}
          </span>
          <Button
            size={'small'}
            type={'link'}
            href={
              'https://cloudrec.yuque.com/org-wiki-cloudrec-iew3sz/hocvhx/apka69usk9a3gf1s#jLQrz'
            }
            target={'_blank'}
            style={{ color: '#2f54eb', gap: 4 }}
          >
            <ExportOutlined />
            <span>
              {intl.formatMessage({
                id: 'common.link.text.help.center',
              })}
            </span>
          </Button>
        </Flex>
      }
      onClose={onClickCloseModalForm}
      destroyOnClose
      open={linkDrawerVisible}
      width={'80%'}
      footer={
        <Flex justify={'flex-end'}>
          <Space>
            <Button onClick={() => onClickCloseModalForm()}>
              {intl.formatMessage({
                id: 'common.button.text.cancel',
              })}
            </Button>
            <Button type="primary" onClick={() => onClickFinishForm()}>
              {intl.formatMessage({
                id: 'common.button.text.ok',
              })}
            </Button>
          </Space>
        </Flex>
      }
    >
      <ProForm<{
        ruleConfigList: linkDataSourceType[];
      }>
        formRef={linkFormRef}
        layout={'horizontal'}
        submitter={false}
      >
        <ProCard
          bodyStyle={{
            backgroundColor: 'rgb(245, 245, 245)',
            padding: '0 20px 16px 20px',
          }}
        >
          <EditableProTable<linkDataSourceType>
            rowKey="idx"
            name="linkedDataList"
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
            columns={linkConfigColumns}
            editable={{
              type: 'multiple',
              editableKeys,
              onChange: setEditableRowKeys,
              actionRender: (row, config, defaultDom) => {
                return [defaultDom.delete];
              },
            }}
          />
        </ProCard>
      </ProForm>
    </Drawer>
  );
};

export default LinkDrawerForm;
