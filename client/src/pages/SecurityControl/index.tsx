import ExpandIcon from '@/components/Common/ExpandIcon';
import styleType from '@/components/Common/index.less';
import CapacityCard from '@/pages/SecurityControl/components/CapacityCard';
import {
  DEFAULT_OVERALL_POSTURE_LIST,
  obtainFilterVisible,
  obtainOverallPostureName,
  SECURITY_ABILITY_STATUS_LIST,
  STATISTIC_TYPE,
  statisticFormatter,
} from '@/pages/SecurityControl/const';
import { cloudAccountBaseInfoListV2 } from '@/services/account/AccountController';
import {
  queryOverallPosture,
  querySecurityProductPostureList,
} from '@/services/security/SecurityController';
import {
  obtainPlatformEasyIcon,
  valueListAddIcon,
  valueListAsValueEnum,
} from '@/utils/shared';
import {
  ActionType,
  PageContainer,
  ProCard,
  ProColumns,
  ProFormInstance,
  ProTable,
} from '@ant-design/pro-components';
import { useIntl, useModel, useRequest } from '@umijs/max';
import {
  Badge,
  Breakpoint,
  Col,
  Flex,
  Grid,
  Row,
  Space,
  Spin,
  Statistic,
  Typography,
} from 'antd';
import { cloneDeep, debounce, isEmpty } from 'lodash';
import React, { useEffect, useMemo, useRef, useState } from 'react';
import styles from './index.less';
const { useBreakpoint } = Grid;
const { Text } = Typography;

// risk management
const SecurityControl: React.FC = () => {
  // Ant Design Provide monitoring of screen width changes
  const breakpoints: Partial<Record<Breakpoint, boolean>> = useBreakpoint();
  // Global State
  const { securityPlatformList } = useModel('security');
  const DEFAULT_PLATFORM = securityPlatformList[0].value;
  // Cloud Platform
  const [platform, setPlatform] = useState<string>(DEFAULT_PLATFORM);
  // Table Action
  const tableActionRef = useRef<ActionType>();
  // Form Action
  const formActionRef = useRef<ProFormInstance>();
  // Intl API
  const intl = useIntl();
  // Risk situation list
  const [overallPostureList, setOverallPostureList] = useState(
    DEFAULT_OVERALL_POSTURE_LIST,
  );

  // Query risk situation
  const { run: requestOverallPostureList, loading: overallPostureListLoading } =
    useRequest((params: API.BaseSecurityInfo) => queryOverallPosture(params), {
      manual: true,
      formatResult: (r): void => {
        const securityList = r?.content?.securityProductOverallList || [];
        const dataList: any[] = [
          {
            ...DEFAULT_OVERALL_POSTURE_LIST[0],
            total: r?.content?.cloudAccountCount || 0,
          },
        ];
        for (let i = 0; i < securityList.length; i++) {
          const item = securityList[i];
          const element = DEFAULT_OVERALL_POSTURE_LIST.find(
            (i) => i.type === item['productType'],
          );
          dataList.push({
            ...element,
            open: item?.protectedCount,
            close: item?.unprotectedCount,
          });
        }
        setOverallPostureList(dataList);
      },
    });

  // Cloud account list data
  const {
    data: baseCloudAccountList,
    run: requestCloudAccountBaseInfoList,
    loading: fetching,
  } = useRequest(
    (params: { cloudAccountSearch?: string; platformList?: string[] }) => {
      return cloudAccountBaseInfoListV2({ ...params });
    },
    {
      manual: true,
      formatResult: (r) => r?.content,
    },
  );

  // Cloud account list filtering
  const debounceFetcher = useMemo(() => {
    const loadOptions = (fuzzy: string): void => {
      // if (isEmpty(fuzzy)) return;
      requestCloudAccountBaseInfoList({
        platformList: [platform],
        cloudAccountSearch: fuzzy,
      });
    };
    return debounce(loadOptions, 500);
  }, [cloudAccountBaseInfoListV2]);

  useEffect((): void => {
    requestCloudAccountBaseInfoList({
      cloudAccountSearch: undefined,
      platformList: [DEFAULT_PLATFORM],
    });
    requestOverallPostureList({ platform: DEFAULT_PLATFORM });
  }, []);

  // Table Columns
  const columns: ProColumns<API.BaseSecurityInfo, 'text'>[] = [
    {
      title: intl.formatMessage({
        id: 'common.select.label.cloudAccount',
      }),
      dataIndex: 'cloudAccountId',
      valueType: 'select',
      fieldProps: {
        placeholder: intl.formatMessage({
          id: 'common.select.query.text.placeholder',
        }),
        showSearch: true,
        notFoundContent: fetching && <Spin size="small" />,
        onSearch: debounceFetcher,
        options: baseCloudAccountList || [],
        fieldNames: {
          label: 'alias',
          value: 'cloudAccountId',
        },
      },
      align: 'left',
      render: (_, record) => {
        return (
          <div>
            <section style={{ color: 'rgb(51, 51, 51)' }}>
              {record?.cloudAccountId || '-'}
            </section>
            <Flex style={{ fontSize: '12px', color: '#999' }}>
              {obtainPlatformEasyIcon(record.platform!, securityPlatformList)}
              {record?.alias || '-'}
            </Flex>
          </div>
        );
      },
    },
    {
      title: intl.formatMessage({
        id: 'security.table.columns.resource.count',
      }),
      dataIndex: 'total',
      valueType: 'text',
      hideInSearch: true,
    },
    {
      title: intl.formatMessage({
        id: 'security.table.columns.Tenant',
      }),
      dataIndex: 'tenantName',
      valueType: 'text',
      hideInSearch: true,
    },
    {
      title: intl.formatMessage({
        id: 'security.table.columns.security.ability.open.status',
      }),
      dataIndex: 'securityAbility',
      hideInSearch: true,
      render: (_, record) => (
        <Space>
          {record?.productPostureList?.map((item, index) => (
            <Badge
              key={index}
              color={['close']?.includes(item.status!) ? 'red' : 'green'}
              text={obtainOverallPostureName(item.productType!)}
            />
          ))}
        </Space>
      ),
    },
    {
      title: intl.formatMessage({
        id: 'cloudAccount.extend.title.updateTime',
      }),
      dataIndex: 'gmtCreated',
      valueType: 'text',
      align: 'left',
      hideInSearch: true,
      render: (_, record) => {
        return (
          <div style={{ color: '#999' }}>{record?.gmtModified || '-'}</div>
        );
      },
    },
    {
      title: intl.formatMessage({
        id: 'security.module.text.ddos.name',
      }),
      fieldProps: {
        placeholder: intl.formatMessage({
          id: 'security.table.search.ability.status',
        }),
      },
      dataIndex: DEFAULT_OVERALL_POSTURE_LIST[1].type,
      valueType: 'select',
      hideInTable: true,
      hideInSearch: obtainFilterVisible(
        overallPostureList,
        DEFAULT_OVERALL_POSTURE_LIST[1].type,
      ),
      valueEnum: valueListAsValueEnum(SECURITY_ABILITY_STATUS_LIST),
    },
    {
      title: intl.formatMessage({
        id: 'security.module.text.waf.name',
      }),
      fieldProps: {
        placeholder: intl.formatMessage({
          id: 'security.table.search.ability.status',
        }),
      },
      dataIndex: DEFAULT_OVERALL_POSTURE_LIST[2].type,
      valueType: 'select',
      hideInTable: true,
      hideInSearch: obtainFilterVisible(
        overallPostureList,
        DEFAULT_OVERALL_POSTURE_LIST[2].type,
      ),
      valueEnum: valueListAsValueEnum(SECURITY_ABILITY_STATUS_LIST),
    },
    {
      title: intl.formatMessage({
        id: 'security.module.text.firewall.name',
      }),
      fieldProps: {
        placeholder: intl.formatMessage({
          id: 'security.table.search.ability.status',
        }),
      },
      dataIndex: DEFAULT_OVERALL_POSTURE_LIST[3].type,
      valueType: 'select',
      hideInTable: true,
      hideInSearch: obtainFilterVisible(
        overallPostureList,
        DEFAULT_OVERALL_POSTURE_LIST[3].type,
      ),
      valueEnum: valueListAsValueEnum(SECURITY_ABILITY_STATUS_LIST),
    },
    {
      title: intl.formatMessage({
        id: 'security.module.text.sas.name',
      }),
      fieldProps: {
        placeholder: intl.formatMessage({
          id: 'security.table.search.ability.status',
        }),
      },
      dataIndex: DEFAULT_OVERALL_POSTURE_LIST[4].type,
      valueType: 'select',
      hideInTable: true,
      hideInSearch: obtainFilterVisible(
        overallPostureList,
        DEFAULT_OVERALL_POSTURE_LIST[4].type,
      ),
      valueEnum: valueListAsValueEnum(SECURITY_ABILITY_STATUS_LIST),
    },
  ];

  const requestTableList = async (params: Record<string, any>) => {
    const { pageSize, current } = params;
    const {
      cloudAccountId, // cloudAccountId
      ...reset
    } = formActionRef.current?.getFieldsValue();
    const postBody: API.BaseSecurityInfo = {
      page: current,
      size: pageSize,
      cloudAccountId,
      platform,
    };
    if (!isEmpty(reset) && JSON.stringify(cloneDeep(reset)) !== '{}')
      postBody.statusMap = {
        ...reset,
      };
    const { content, code } = await querySecurityProductPostureList(postBody);
    return {
      data: content?.data || [],
      total: content?.total || 0,
      success: code === 200 || false,
    };
  };

  return (
    <PageContainer
      title={false}
      className={styles['securityControl']}
      breadcrumbRender={false}
    >
      <ProCard
        style={{ marginBottom: 16 }}
        tabs={{
          activeKey: platform,
          destroyInactiveTabPane: true,
          items:
            (valueListAddIcon(securityPlatformList).map((item) => ({
              label: item.label,
              key: item.value,
              children: (
                <Spin spinning={overallPostureListLoading}>
                  <Row justify={'space-around'}>
                    {overallPostureList?.map((item, index: number) => {
                      return (
                        <Col key={index}>
                          <Statistic
                            className={styles['securityStatistic']}
                            title={
                              <Text type="secondary">{item.title || '-'}</Text>
                            }
                            value={item as unknown as number}
                            formatter={statisticFormatter}
                            style={{
                              paddingRight:
                                item.type === STATISTIC_TYPE.TOTAL
                                  ? breakpoints.xxl
                                    ? 64
                                    : 32
                                  : 0,
                              borderInlineEnd:
                                item.type === STATISTIC_TYPE.TOTAL &&
                                overallPostureList.length > 1
                                  ? '1px solid #f0f0f0'
                                  : undefined,
                            }}
                          />
                        </Col>
                      );
                    })}
                  </Row>
                </Spin>
              ),
            })) as any) || [],
          onChange: (key): void => {
            setPlatform(key as string);
            formActionRef.current?.resetFields();
            requestCloudAccountBaseInfoList({
              cloudAccountSearch: undefined,
              platformList: [key],
            });
            requestOverallPostureList({ platform: key });
            tableActionRef.current?.reload();
          },
        }}
      />

      <ProTable
        rowKey={(_, index) => index!}
        search={{
          span: 6,
          labelWidth: 0,
        }}
        headerTitle={
          <div className={styleType['customTitle']}>
            {intl.formatMessage({
              id: 'security.module.text.security.inquiry',
            })}
          </div>
        }
        actionRef={tableActionRef}
        formRef={formActionRef}
        columns={columns}
        request={requestTableList}
        pagination={{
          showQuickJumper: false,
          showSizeChanger: true,
          defaultPageSize: 10,
          defaultCurrent: 1,
        }}
        expandable={{
          expandedRowRender: (record) => (
            <Row gutter={[16, 16]}>
              {record?.productPostureList?.map((item, index) => (
                <CapacityCard
                  key={index}
                  tableActionRef={tableActionRef}
                  record={item}
                />
              ))}
            </Row>
          ),
          columnTitle: <div style={{ width: 30, textAlign: 'center' }} />,
          columnWidth: 30,
          rowExpandable: (): boolean => true,
          expandIcon: ExpandIcon,
        }}
      />
    </PageContainer>
  );
};

export default SecurityControl;
