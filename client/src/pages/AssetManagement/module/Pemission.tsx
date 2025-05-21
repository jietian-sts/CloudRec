import ExpandIcon from '@/components/Common/ExpandIcon';
import PermissionExpandRow from '@/pages/AssetManagement/module/PermissionExpandRow';
import { queryIdentityDetailById } from '@/services/asset/AssetController';
import { ProCard } from '@ant-design/pro-components';
import { useIntl, useLocation, useRequest } from '@umijs/max';
import { ConfigProvider, Table } from 'antd';
import React, { useEffect } from 'react';

// Permission
const Permission: React.FC = () => {
  // Get query parameters
  const location = useLocation();
  const queryParameters: URLSearchParams = new URLSearchParams(location.search);
  const id = queryParameters.get('id');
  // Intl API
  const intl = useIntl();

  // Query identity detail
  const {
    run: requestIdentityDetailById,
    data: identityDetailInfo,
    loading: identityDetailInfoLoading,
  } = useRequest(
    (id: number) =>
      queryIdentityDetailById({
        id: id,
      }),
    {
      manual: true,
      formatResult: (r) => r?.content,
    },
  );

  useEffect((): void => {
    if (id) requestIdentityDetailById(Number(id));
  }, [id]);

  const columns = [
    {
      title: intl.formatMessage({ id: 'asset.module.text.policy.name' }),
      dataIndex: 'policyName',
      key: 'policyName',
    },
    {
      title: intl.formatMessage({ id: 'asset.module.text.policy.description' }),
      dataIndex: 'description',
      key: 'description',
    },
    {
      title: intl.formatMessage({ id: 'asset.module.text.policy.type' }),
      dataIndex: 'policyType',
      key: 'policyType',
    },
    {
      title: intl.formatMessage({ id: 'asset.module.text.source' }),
      dataIndex: 'source',
      key: 'source',
      render: (_: any, record: API.IPolicy) => record?.source || '-',
    },
  ];

  return (
    <ProCard
      loading={identityDetailInfoLoading}
      title={intl.formatMessage({ id: 'asset.module.text.permission.policy' })}
    >
      <ConfigProvider
        theme={{
          components: {
            Table: {
              headerBg: '#FFF',
            },
          },
        }}
      >
        <Table
          pagination={{
            size: 'small',
          }}
          dataSource={identityDetailInfo?.policies || []}
          columns={columns}
          expandable={{
            expandedRowRender: (record) => (
              <PermissionExpandRow record={record} />
            ),
            columnTitle: <div style={{ width: 30, textAlign: 'center' }} />,
            columnWidth: 30,
            rowExpandable: (): boolean => true,
            expandIcon: ExpandIcon,
          }}
        />
      </ConfigProvider>
    </ProCard>
  );
};

export default Permission;
