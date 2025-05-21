import { queryMember, removeUser } from '@/services/tenant/TenantController';
import { changeUserStatus } from '@/services/user/UserController';
import { UserTypeList } from '@/utils/const';
import { showTotalIntlFunc, valueListAsValueEnum } from '@/utils/shared';
import {
  ActionType,
  DrawerForm,
  ProColumns,
  ProTable,
} from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Button, Popconfirm, Space, Switch, message } from 'antd';
import React, { Dispatch, SetStateAction, useRef, useState } from 'react';
import AddTenantMember from './AddTenantMember';

interface IDrawerFormProps {
  drawerFormVisible: boolean;
  setDrawerFormVisible: Dispatch<SetStateAction<boolean>>;
  drawerInfo: Record<string, any>;
  tableActionRef: React.RefObject<ActionType | undefined>;
}

// Edit tenant member information
const DrawerModalForm: React.FC<IDrawerFormProps> = (props) => {
  // Table Action
  const drawerTableActionRef = useRef<ActionType>();
  // Message API
  const [messageApi] = message.useMessage();
  // Intl API
  const intl = useIntl();

  const [addFormVisible, setAddFormVisible] = useState<boolean>(false);
  // Tenant Info
  const addTenantInfoRef = useRef<any>({});

  // Component Props
  const {
    drawerFormVisible,
    drawerInfo,
    setDrawerFormVisible,
    tableActionRef,
  } = props;

  const initDrawer = async (): Promise<void> => {
    setDrawerFormVisible(false);
    await tableActionRef.current?.reloadAndRest?.();
  };

  const onClickFishDrawerForm = async (): Promise<void> => {
    await initDrawer();
  };

  // Delete a user from the current tenant
  const onClickDelTenantUser = async (userId: number): Promise<void> => {
    const body = {
      userId,
      tenantId: drawerInfo.id,
    };
    const res = await removeUser(body);
    if (res.code === 200 || res.msg === 'success') {
      drawerTableActionRef.current?.reloadAndRest?.();
    }
  };

  const onClickChangeUserStatus = async (
    id: number,
    status: string,
  ): Promise<void> => {
    const postBody = {
      id,
      status,
    };
    const res = await changeUserStatus(postBody);
    if (res.code === 200 || res.msg === 'success') {
      messageApi.success(
        intl.formatMessage({ id: 'common.message.text.edit.success' }),
      );
      drawerTableActionRef.current?.reloadAndRest?.();
    }
  };

  // Table Columns
  const columns: ProColumns<API.UserInfo, 'text'>[] = [
    {
      title: intl.formatMessage({
        id: 'user.module.title.user.name',
      }),
      dataIndex: 'username',
      valueType: 'text',
      align: 'center',
    },
    {
      title: intl.formatMessage({
        id: 'user.module.title.user.id',
      }),
      dataIndex: 'userId',
      valueType: 'text',
      align: 'center',
    },
    {
      title: intl.formatMessage({
        id: 'user.module.title.user.role',
      }),
      dataIndex: 'roleName',
      valueType: 'select',
      hideInSearch: true,
      align: 'center',
      valueEnum: valueListAsValueEnum(UserTypeList),
    },
    {
      title: intl.formatMessage({
        id: 'cloudAccount.extend.title.createTime',
      }),
      dataIndex: 'gmtCreate',
      valueType: 'dateTime',
      hideInSearch: true,
      align: 'center',
    },
    {
      title: intl.formatMessage({
        id: 'cloudAccount.extend.title.updateTime',
      }),
      dataIndex: 'gmtModified',
      valueType: 'dateTime',
      hideInSearch: true,
      align: 'center',
    },
    {
      title: intl.formatMessage({
        id: 'cloudAccount.extend.title.cloud.operate',
      }),
      dataIndex: 'option',
      valueType: 'option',
      align: 'center',
      render: (_, record: API.UserInfo) => (
        <Space size={'small'}>
          <Popconfirm
            title={intl.formatMessage({
              id: 'tenant.extend.member.delete.user',
            })}
            onConfirm={() => onClickDelTenantUser(record.id!)}
            okText={intl.formatMessage({
              id: 'common.button.text.ok',
            })}
            cancelText={intl.formatMessage({
              id: 'common.button.text.cancel',
            })}
          >
            <Button type="link" danger>
              {intl.formatMessage({
                id: 'common.button.text.delete',
              })}
            </Button>
          </Popconfirm>
          <Switch
            checkedChildren={intl.formatMessage({
              id: 'common.button.text.normal',
            })}
            unCheckedChildren={intl.formatMessage({
              id: 'common.button.text.disable',
            })}
            checked={record?.status === 'valid'}
            onClick={() =>
              onClickChangeUserStatus(
                record.id!,
                record?.status === 'valid' ? 'invalid' : 'valid',
              )
            }
          />
        </Space>
      ),
    },
  ];

  return (
    <>
      <DrawerForm
        title={intl.formatMessage({
          id: 'tenant.extend.member.edit',
        })}
        width={'50%'}
        drawerProps={{
          destroyOnClose: true,
          onClose: () => initDrawer(),
          styles: {
            body: { padding: '12px 0' },
          },
        }}
        open={drawerFormVisible}
        onFinish={onClickFishDrawerForm}
      >
        <ProTable<API.UserInfo>
          actionRef={drawerTableActionRef}
          rowKey="id"
          search={false}
          options={false}
          toolBarRender={() => [
            <Button
              key="create"
              type="primary"
              onClick={(): void => {
                setAddFormVisible(true);
                addTenantInfoRef.current = {
                  ...drawerInfo,
                };
              }}
            >
              {intl.formatMessage({
                id: 'tenant.extend.member.add',
              })}
            </Button>,
          ]}
          request={async (params) => {
            const { pageSize, current } = params;
            const postBody = {
              id: drawerInfo.id,
              page: current!,
              size: pageSize!,
            };
            const { content, code } = await queryMember(postBody);
            return {
              data: content?.data || [],
              total: content?.total || 0,
              success: code === 200 || false,
            };
          }}
          columns={columns}
          pagination={{
            showQuickJumper: false,
            showSizeChanger: true,
            defaultPageSize: 10,
            defaultCurrent: 1,
            showTotal: (total: number, range: [number, number]): string =>
              showTotalIntlFunc(total, range, intl?.locale),
          }}
        />
      </DrawerForm>

      <AddTenantMember // Add tenant members
        addFormVisible={addFormVisible}
        setAddFormVisible={setAddFormVisible}
        addTenantInfo={addTenantInfoRef?.current}
        drawerTableActionRef={drawerTableActionRef as any}
      />
    </>
  );
};

export default DrawerModalForm;
