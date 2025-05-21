import styles from '@/components/Common/index.less';
import { queryTenantList } from '@/services/tenant/TenantController';
import {
  ActionType,
  PageContainer,
  ProColumns,
  ProTable,
} from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Button, Space } from 'antd';
import React, { useRef, useState } from 'react';
import DrawerModalForm from './components/DrawerModalForm';
import EditModalForm from './components/EditModalForm';

const TenantModule: React.FC = () => {
  // Table Action
  const tableActionRef = useRef<ActionType>();
  // Intl API
  const intl = useIntl();
  // Edit tenant
  const [editFormVisible, setEditFormVisible] = useState<boolean>(false);
  // Tenant information
  const tenantInfoRef = useRef<any>({});
  const drawerInfoRef = useRef<any>({});
  // Editorial Members
  const [drawerFormVisible, setDrawerFormVisible] = useState<boolean>(false);
  // Table Columns
  const columns: ProColumns<API.TenantInfo, 'text'>[] = [
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
            <section style={{ color: '#999' }}>
              {record?.gmtCreate || '-'}
            </section>
            <section style={{ color: '#999' }}>
              {record?.gmtModified || '-'}
            </section>
          </div>
        );
      },
    },
    {
      title: intl.formatMessage({
        id: 'tenant.module.text.tenant.name',
      }),
      dataIndex: 'tenantName',
      valueType: 'text',
      align: 'center',
    },
    {
      title: intl.formatMessage({
        id: 'tenant.module.text.tenant.description',
      }),
      dataIndex: 'tenantDesc',
      hideInSearch: true,
      valueType: 'text',
      align: 'center',
    },
    {
      title: intl.formatMessage({
        id: 'tenant.module.text.member.number',
      }),
      dataIndex: 'memberCount',
      hideInSearch: true,
      valueType: 'text',
      align: 'center',
      render: (_, record: API.TenantInfo) => (
        <Space size={'small'}>
          <Button
            disabled={record.status !== 'valid'}
            type={'link'}
            onClick={(): void => {
              setDrawerFormVisible(true);
              drawerInfoRef.current = {
                ...record,
              };
            }}
          >
            {record?.memberCount}
          </Button>
        </Space>
      ),
    },
    {
      title: intl.formatMessage({
        id: 'cloudAccount.extend.title.cloud.operate',
      }),
      dataIndex: 'option',
      valueType: 'option',
      align: 'center',
      render: (_, record: API.TenantInfo) => (
        <Space size={'small'}>
          <Button
            type={'link'}
            onClick={(): void => {
              setEditFormVisible(true);
              tenantInfoRef.current = {
                ...record,
              };
            }}
          >
            {intl.formatMessage({
              id: 'common.button.text.edit',
            })}
          </Button>
        </Space>
      ),
    },
  ];

  return (
    <PageContainer ghost title={false} breadcrumbRender={false}>
      <ProTable<API.TenantInfo>
        headerTitle={
          <div className={styles['customTitle']}>
            {intl.formatMessage({
              id: 'tenant.module.text.tenant.inquiry',
            })}
          </div>
        }
        actionRef={tableActionRef}
        rowKey="id"
        search={{
          span: 6,
          defaultCollapsed: false, // Default Expand
          collapseRender: false, // Hide expand/close button
          labelWidth: 0,
        }}
        toolBarRender={() => [
          <Button
            key="create"
            type="primary"
            onClick={(): void => {
              tenantInfoRef.current = {};
              setEditFormVisible(true);
            }}
          >
            {intl.formatMessage({
              id: 'tenant.extend.text.add',
            })}
          </Button>,
        ]}
        request={async (params) => {
          const { tenantName, pageSize, current } = params;
          const postBody = {
            tenantName,
            page: current!,
            size: pageSize!,
            pageLimit: true,
          };
          const { content, code } = await queryTenantList(postBody);
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
        }}
      />

      <EditModalForm // New | Edit
        editFormVisible={editFormVisible}
        setEditFormVisible={setEditFormVisible}
        tenantInfo={tenantInfoRef.current}
        tableActionRef={tableActionRef}
      />

      <DrawerModalForm // Tenant members
        drawerFormVisible={drawerFormVisible}
        setDrawerFormVisible={setDrawerFormVisible}
        drawerInfo={drawerInfoRef.current}
        tableActionRef={tableActionRef}
      />
    </PageContainer>
  );
};

export default TenantModule;
