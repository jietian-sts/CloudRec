import identity from '@/assets/images/identity.svg';
import ExpandIcon from '@/components/Common/ExpandIcon';
import styleType from '@/components/Common/index.less';
import LevelTag from '@/components/Common/LevelTag';
import Disposition from '@/components/Disposition';
import IdentityExpandRow from '@/pages/AssetManagement/components/IdentityExpandRow';
import { cloudAccountBaseInfoListV2 } from '@/services/account/AccountController';
import {
  queryGroupTagList,
  queryIdentityCardList,
  queryIdentityList,
} from '@/services/asset/AssetController';
import {
  obtainPlatformEasyIcon,
  valueListAddIcon,
  valueListAsValueEnum,
} from '@/utils/shared';
import { CheckCard } from '@ant-design/pro-card';
import { CheckGroupValueType } from '@ant-design/pro-card/es/components/CheckCard/Group';
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
  Breakpoint,
  Button,
  Col,
  Empty,
  Flex,
  Grid,
  Image,
  Row,
  Space,
  Spin,
  Tag,
  Typography,
} from 'antd';
import { debounce, isEmpty } from 'lodash';
import React, { useEffect, useMemo, useRef, useState } from 'react';
import styles from './index.less';
const { useBreakpoint } = Grid;
const { Text } = Typography;

// Security Control
const SecurityControl: React.FC = () => {
  // Global State
  const { identityPlatformList } = useModel('asset');
  const DEFAULT_PLATFORM = identityPlatformList[0].value;
  // Cloud Platform
  const [platform, setPlatform] = useState<string>(DEFAULT_PLATFORM);
  // Table Action
  const tableActionRef = useRef<ActionType>();
  // Form Action
  const formActionRef = useRef<ProFormInstance>();
  // Intl API
  const intl = useIntl();
  // Ant Design Provide monitoring of screen width changes
  const breakpoints: Partial<Record<Breakpoint, boolean>> = useBreakpoint();
  // Check Card List
  const [checkCardList, setCheckCardList] = useState<Array<number | string>>(
    [],
  );
  // Check Card Change CallBack
  const handleCheckCardChange = (value: CheckGroupValueType) => {
    setCheckCardList(value as Array<number | string>);
  };

  // Query risk card list
  const {
    run: requestOverallRiskCardList,
    data: riskCardList,
    loading: overallRiskCardListLoading,
  } = useRequest(
    (platform: string) =>
      queryIdentityCardList({
        platformList: [platform],
      }),
    {
      manual: true,
      formatResult: (r) => r?.content,
    },
  );

  // Query group tag list
  const { run: requestGroupTagList, data: groupTagList } = useRequest(
    () => queryGroupTagList({}),
    {
      manual: true,
      formatResult: (r) =>
        r?.content?.map((item) => ({ label: item, value: item })),
    },
  );

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
    requestOverallRiskCardList(DEFAULT_PLATFORM);
    requestCloudAccountBaseInfoList({
      cloudAccountSearch: undefined,
      platformList: [DEFAULT_PLATFORM],
    });
    requestGroupTagList();
  }, []);

  // Table Columns
  const columns: ProColumns<API.BaseIdentity, 'text'>[] = [
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
              {obtainPlatformEasyIcon(record.platform!, identityPlatformList)}
              {record?.resourceTypeGroup || '-'}
            </Flex>
          </div>
        );
      },
    },
    {
      title: intl.formatMessage({
        id: 'asset.table.columns.access.type',
      }),
      dataIndex: 'accessType',
      hideInSearch: true,
    },
    {
      title: intl.formatMessage({
        id: 'asset.table.columns.unused.permission',
      }),
      dataIndex: 'limit',
      hideInSearch: true,
    },
    {
      title: intl.formatMessage({
        id: 'asset.table.columns.text.label',
      }),
      valueType: 'select',
      valueEnum: valueListAsValueEnum(groupTagList as any[]),
      dataIndex: 'tags',
      fieldProps: {
        mode: 'multiple',
      },
      render: (_, record) =>
        !isEmpty(record.tags)
          ? record.tags?.map((item, index) => (
              <Tag color="geekblue" key={index}>
                {item}
              </Tag>
            ))
          : '-',
    },
    {
      title: 'Access Key',
      dataIndex: 'accessKeyIds',
      valueType: 'select',
      fieldProps: {
        placeholder: intl.formatMessage({
          id: 'asset.input.text.placeholder.access.key',
        }),
        mode: 'tags',
        suffixIcon: <></>,
      },
      hideInTable: true,
    },
    {
      title: intl.formatMessage({
        id: 'cloudAccount.extend.title.cloud.operate',
      }),
      dataIndex: 'option',
      valueType: 'option',
      align: 'center',
      fixed: 'right',
      render: (_, record: API.BaseIdentity) => (
        <Button
          key={'IDENTITY_ASSOCIATE'}
          size={'small'}
          type="link"
          href={`/assetManagement/identityAssociate?id=${record?.id}`}
        >
          {intl.formatMessage({
            id: 'common.button.text.detail',
          })}
        </Button>
      ),
    },
  ];

  const requestTableList = async (params: Record<string, any>) => {
    const { pageSize, current, cloudAccountId, tags, accessKeyIds } = params;
    const postBody: API.BaseIdentity = {
      page: current,
      size: pageSize,
      cloudAccountId,
      platform: platform,
      tags: tags?.toString(),
      accessKeyIds: accessKeyIds?.toString(),
    };
    if (!isEmpty(checkCardList)) postBody.ruleIds = checkCardList?.toString();
    const { content, code } = await queryIdentityList(postBody);
    return {
      data: content.data || [],
      total: content?.total || 0,
      success: code === 200 || false,
    };
  };

  // Click ProTable Reset Button
  const onClickResetTable = (): void => {
    setCheckCardList([]);
    // @ts-ignore
    tableActionRef.current?.reloadAndRest();
  };

  return (
    <PageContainer
      title={false}
      className={styles['securityControl']}
      breadcrumbRender={false}
    >
      <ProCard
        className={styles['tabProCard']}
        tabs={{
          activeKey: platform,
          destroyInactiveTabPane: true,
          items:
            (valueListAddIcon(identityPlatformList).map((item) => ({
              label: item.label,
              key: item.value,
              children: (
                <Spin spinning={overallRiskCardListLoading}>
                  {!isEmpty(riskCardList) ? (
                    <CheckCard.Group
                      multiple
                      value={checkCardList}
                      onChange={handleCheckCardChange}
                      style={{ display: 'block' }}
                    >
                      <Row gutter={[16, 0]}>
                        {riskCardList?.map((item) => {
                          return (
                            <Col
                              key={item.ruleId}
                              span={breakpoints?.xxl ? 4 : 6}
                            >
                              <CheckCard
                                value={item.ruleId}
                                style={{ maxWidth: '100%' }}
                                title={
                                  <Disposition
                                    text={item.ruleName}
                                    rows={1}
                                    maxWidth={160}
                                  />
                                }
                                extra={
                                  <LevelTag
                                    style={{ marginRight: 0 }}
                                    level={item.riskLevel?.toUpperCase() as any}
                                    text={item.riskLevel?.toUpperCase()}
                                  />
                                }
                                description={
                                  <Space size={4}>
                                    <Image
                                      src={identity}
                                      preview={false}
                                      width={15}
                                    />
                                    <Text strong>{item.userCount}</Text>
                                    <span>
                                      {intl.formatMessage({
                                        id: 'asset.module.text.identity',
                                      })}
                                    </span>
                                  </Space>
                                }
                              />
                            </Col>
                          );
                        })}
                      </Row>
                    </CheckCard.Group>
                  ) : (
                    <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
                  )}
                </Spin>
              ),
            })) as any) || [],
          onChange: (key): void => {
            setPlatform(key as string);
            formActionRef.current?.resetFields();
            setCheckCardList([]);
            requestCloudAccountBaseInfoList({
              cloudAccountSearch: undefined,
              platformList: [key],
            });
            requestOverallRiskCardList(key);
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
              id: 'asset.module.text.identity.inquiry',
            })}
          </div>
        }
        onReset={() => onClickResetTable()}
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
          expandedRowRender: (record) => <IdentityExpandRow record={record} />,
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
