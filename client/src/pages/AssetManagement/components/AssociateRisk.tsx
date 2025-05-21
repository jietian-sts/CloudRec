import Disposition from '@/components/Disposition';
import RiskDrawer from '@/pages/RiskManagement/components/RiskDrawer';
import { RiskStatusList } from '@/pages/RiskManagement/const';
import { queryRiskList } from '@/services/risk/RiskController';
import { IValueType, RiskLevelList } from '@/utils/const';
import {
  ActionType,
  ProCard,
  ProColumns,
  ProFormInstance,
  ProTable,
} from '@ant-design/pro-components';
import { Button, Tag } from 'antd';
import React, { useRef, useState } from 'react';

interface IBasicAsset {
  assetInfo: API.BaseAssetResultInfo;
}

/**
 * Risk Information
 * Note: Not yet used
 */
const AssociateRisk: React.FC<IBasicAsset> = (props) => {
  const { assetInfo } = props;
  // Table Action
  const tableActionRef = useRef<ActionType>();
  // Form Action
  const formActionRef = useRef<ProFormInstance>();
  // Account Drawer
  const [riskDrawerVisible, setRiskDrawerVisible] = useState<boolean>(false);
  // Risk information
  const riskDrawerRef = useRef<any>({});

  // Table Columns
  const columns: ProColumns<API.BaseRiskResultInfo, 'text'>[] = [
    {
      title: '资源名称',
      dataIndex: 'resourceName',
      valueType: 'text',
      align: 'center',
      render: (_, record: API.BaseRiskResultInfo) => {
        return (
          <Disposition
            text={
              !record?.resourceExist
                ? '(不存在) ' + record.resourceName
                : record.resourceName
            }
            maxWidth={280}
          />
        );
      },
    },
    {
      title: '资源类型',
      dataIndex: 'resourceType',
      valueType: 'text',
      align: 'center',
      render: (_, record: API.BaseRiskResultInfo) => {
        return <Tag color="geekblue">{record?.resourceType}</Tag>;
      },
    },
    {
      title: '规则名称',
      dataIndex: 'ruleName',
      valueType: 'text',
      align: 'center',
      render: (_, record: API.BaseRiskResultInfo) => {
        return <span>{record?.ruleVO?.ruleName}</span>;
      },
    },
    {
      title: '规则组',
      dataIndex: 'groupName',
      valueType: 'text',
      align: 'center',
      render: (_, record: API.BaseRiskResultInfo) => {
        return <span>{record?.ruleVO?.groupName}</span>;
      },
    },
    {
      title: '风险等级',
      dataIndex: 'riskLevel',
      valueType: 'text',
      align: 'center',
      render: (_, record: API.BaseRiskResultInfo) => {
        const elem = RiskLevelList?.find(
          (item: IValueType) => item.value === record?.ruleVO?.riskLevel,
        );
        return <Tag color={elem?.color}>{elem?.text || '-'}</Tag>;
      },
    },
    {
      title: '风险状态',
      dataIndex: 'status',
      valueType: 'text',
      align: 'center',
      render: (_, record: API.BaseRiskResultInfo) => {
        const elem = RiskStatusList?.find(
          (item: IValueType) => item.value === record?.status,
        );
        return <Tag color={elem?.color}>{elem?.label || '-'}</Tag>;
      },
    },
    {
      title: '最近扫描命中',
      dataIndex: 'gmtModified',
      valueType: 'text',
      align: 'center',
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      align: 'center',
      render: (_, record: API.BaseRiskResultInfo) => (
        <>
          <Button
            type="link"
            onClick={(): void => {
              riskDrawerRef.current = {
                ...record,
              };
              setRiskDrawerVisible(true);
            }}
          >
            详情
          </Button>
        </>
      ),
    },
  ];

  return (
    <ProCard boxShadow bodyStyle={{ padding: '32px 4px 12px 4px' }}>
      <ProTable
        tableStyle={{ padding: 0 }}
        scroll={{ x: 'max-content' }}
        rowKey={'id'}
        search={false}
        options={false}
        actionRef={tableActionRef}
        formRef={formActionRef}
        columns={columns}
        request={async (params: Record<string, any>): Promise<any> => {
          const {
            pageSize,
            current,
            cloudAccountId, // CloudAccount ID
            resourceId, // Resource ID
            platform, // Cloud Platform
          } = params;
          const postBody = {
            page: current,
            size: pageSize,
            cloudAccountId,
            resourceId,
            platformList: [platform],
          };
          const { content, code } = await queryRiskList(postBody);
          return {
            data: content?.data || [],
            total: content?.total || 0,
            success: code === 200 || false,
          };
        }}
        params={{ ...assetInfo }}
        pagination={{
          showQuickJumper: false,
          showSizeChanger: true,
          defaultPageSize: 10,
          defaultCurrent: 1,
          showTotal: (total: number): string => `共 ${total} 条`,
        }}
      />

      <RiskDrawer // Risk Details
        locate={'asset'}
        riskDrawerVisible={riskDrawerVisible}
        setRiskDrawerVisible={setRiskDrawerVisible}
        riskDrawerInfo={riskDrawerRef.current}
        tableActionRef={tableActionRef}
      />
    </ProCard>
  );
};

export default AssociateRisk;
