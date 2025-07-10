import ThemeTag from '@/components/Common/ThemeTag';
import styleType from '@/components/Common/index.less';
import Disposition from '@/components/Disposition';
import DispositionPro from '@/components/DispositionPro';
import { RiskLevelList } from '@/utils/const';
import {
  obtainFirstProperty,
  obtainPlatformEasyIcon,
  obtainRiskLevel,
  valueListAsValueEnum,
} from '@/utils/shared';
import {
  MinusOutlined,
  PlayCircleOutlined,
} from '@ant-design/icons';
import {
  ActionType,
  ProColumns,
  ProFormInstance,
  ProTable,
} from '@ant-design/pro-components';
import { useIntl, useModel } from '@umijs/max';
import {
  Breakpoint,
  Button,
  Form,
  Grid,
  message,
  Popconfirm,
  Space,
  Tooltip,
} from 'antd';
import { MessageType } from 'antd/es/message/interface';
import { isEmpty } from 'lodash';
import React, { useRef, useState, useEffect } from 'react';
import { queryTenantSelectRuleList, removeTenantSelectRule, scanByRule, scanRuleList, batchDeleteTenantSelectRule } from '@/services/rule/RuleController';

const { useBreakpoint } = Grid;

interface SelectedRulesProps {
  form: any;
  platformList: any;
  resourceTypeList: any[];
  ruleGroupList: any;
  ruleTypeList: any;
  allRuleList: any;
  queryTrigger?: number;
}

const SelectedRules: React.FC<SelectedRulesProps> = ({
                                                       form,
                                                       platformList,
                                                       resourceTypeList,
                                                       ruleGroupList,
                                                       ruleTypeList,
                                                       allRuleList,
                                                       queryTrigger,
                                                     }) => {
  // Message API
  const [messageApi, contextHolder] = message.useMessage();
  // Ant Design Provide monitoring of screen width changes
  const breakpoints: Partial<Record<Breakpoint, boolean>> = useBreakpoint();
  // Table Action
  const tableActionRef = useRef<ActionType>();
  // Form Action
  const formActionRef = useRef<ProFormInstance>();
  // Intl API
  const intl = useIntl();
  // Select status Table Row
  const [activeRow, setActiveRow] = useState<number>();
  // Scanning Loading
  const [scanLoading, setScanLoading] = useState<any>({});
  // Detection Loading
  const [detectLoading, setDetectLoading] = useState<any>({});
  // Remove Loading
  const [removeLoading, setRemoveLoading] = useState<any>({});
  // Selected Row Keys
  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);
  // Batch Scan Loading
  const [batchScanLoading, setBatchScanLoading] = useState<boolean>(false);
  // Batch Remove Loading
  const [batchRemoveLoading, setBatchRemoveLoading] = useState<boolean>(false);

  // Current activation item Row
  const activeRowType = (record: Record<string, any>): string => {
    return record.id === activeRow ? 'ant-table-row-selected' : '';
  };

  // 监听查询触发器变化，重新加载表格数据
  useEffect(() => {
    if (queryTrigger !== undefined && queryTrigger > 0) {
      tableActionRef.current?.reload();
    }
  }, [queryTrigger]);

  // Detection
  const onClickScanByRule = async (id: number): Promise<void> => {
    setScanLoading({ ...scanLoading, [id]: true });
    const hide: MessageType = messageApi.loading(
      intl.formatMessage({ id: 'common.message.text.execute.loading' }),
    );
    const result = await scanByRule({ id });
    setScanLoading({ ...scanLoading, [id]: false });
    hide();
    if (result.code === 200 || result.msg === 'success') {
      messageApi.success(
        intl.formatMessage({ id: 'common.message.text.execute.success' }),
      );
      tableActionRef.current?.reloadAndRest?.();
    }
  };

  // Remove from selected
  const onClickRemoveFromSelected = async (record: any): Promise<void> => {
    const id = record.id;
    setRemoveLoading({ ...removeLoading, [id]: true });
    const hide: MessageType = messageApi.loading('正在移出自选规则...');

    try {
      const result = await removeTenantSelectRule({ ruleCode: record.ruleCode });

      if (result.code === 200 || result.msg === 'success') {
        messageApi.success('已移出自选规则');
        tableActionRef.current?.reloadAndRest?.();
      } else {
        const errorMsg = result.msg || result.errorMsg || '移出失败';
        messageApi.error(errorMsg);
      }
    } catch (error) {
      messageApi.error('移出失败，请稍后重试');
    } finally {
      setRemoveLoading({ ...removeLoading, [id]: false });
      hide();
    }
  };

  // Batch scan rules
  const onClickBatchScan = async (): Promise<void> => {
    if (selectedRowKeys.length === 0) {
      messageApi.warning('请先选择要检测的规则');
      return;
    }

    setBatchScanLoading(true);
    const hide: MessageType = messageApi.loading('正在批量检测规则...');

    try {
      const idList = selectedRowKeys.map(key => Number(key));
      const result = await scanRuleList({ idList });

      if (result.code === 200 || result.msg === 'success') {
        messageApi.success('批量检测已启动');
        setSelectedRowKeys([]);
        tableActionRef.current?.reloadAndRest?.();
      } else {
        const errorMsg = result.msg || result.errorMsg || '批量检测失败';
        messageApi.error(errorMsg);
      }
    } catch (error) {
      messageApi.error('批量检测失败，请稍后重试');
    } finally {
      setBatchScanLoading(false);
      hide();
    }
  };

  // Batch remove from selected
  const onClickBatchRemove = async (): Promise<void> => {
    if (selectedRowKeys.length === 0) {
      messageApi.warning('请先选择要移出的规则');
      return;
    }

    setBatchRemoveLoading(true);
    const hide: MessageType = messageApi.loading('正在批量移出自选规则...');

    try {
      // 通过重新查询获取选中行的ruleCode
      const formValues = form.getFieldsValue();
      const requestParams: API.ListRuleRequest = {
        page: 1,
        size: 1000, // 获取足够多的数据
        platformList: formValues.platformList,
        riskLevelList: formValues.riskLevelList,
        ruleCodeList: formValues.ruleCodeList,
        resourceTypeList: formValues.resourceTypeList,
        ruleGroupIdList: formValues.ruleGroupIdList,
        ruleTypeIdList: formValues.ruleTypeIdList,
      };

      const response = await queryTenantSelectRuleList(requestParams);
      const allData = response?.content?.data || [];
      const selectedData = allData.filter((item: any) => selectedRowKeys.includes(item.id));
      const ruleCodeList = selectedData.map((item: any) => item.ruleCode);

      if (ruleCodeList.length === 0) {
        messageApi.warning('未找到选中的规则数据');
        return;
      }

      const result = await batchDeleteTenantSelectRule({ ruleCodeList });

      if (result.code === 200 || result.msg === 'success') {
        messageApi.success(`已成功移出 ${selectedRowKeys.length} 条自选规则`);
        setSelectedRowKeys([]);
        tableActionRef.current?.reloadAndRest?.();
      } else {
        const errorMsg = result.msg || result.errorMsg || '批量移出失败';
        messageApi.error(errorMsg);
      }
    } catch (error) {
      messageApi.error('批量移出失败，请稍后重试');
    } finally {
      setBatchRemoveLoading(false);
      hide();
    }
  };

  // Request selected rules data
  const requestSelectedRulesData = async (
    params: Record<string, any>,
    sort: Record<string, any>,
  ) => {
    const { pageSize, current, ...searchParams } = params;

    try {
      // 获取表单筛选条件
      const formValues = form.getFieldsValue();

      // 构建请求参数
      const requestParams: API.ListRuleRequest = {
        page: current || 1,
        size: pageSize || 10,
        // 合并表单筛选条件
        platformList: formValues.platformList,
        riskLevelList: formValues.riskLevelList,
        ruleCodeList: formValues.ruleCodeList,
        resourceTypeList: formValues.resourceTypeList,
        ruleGroupIdList: formValues.ruleGroupIdList,
        ruleTypeIdList: formValues.ruleTypeIdList,
        ...searchParams,
      };

      // 处理排序参数
      if (sort && Object.keys(sort).length > 0) {
        const sortKey = Object.keys(sort)[0];
        const sortOrder = sort[sortKey];
        requestParams.sortParam = sortKey;
        requestParams.sortType = sortOrder === 'ascend' ? 'ASC' : 'DESC';
      }

      const response = await queryTenantSelectRuleList(requestParams);

      if (response?.content) {
        return {
          data: response.content.data || [],
          total: response.content.total || 0,
          success: true,
        };
      } else {
        return {
          data: [],
          total: 0,
          success: false,
        };
      }
    } catch (error) {
      return {
        data: [],
        total: 0,
        success: false,
      };
    }
  };

  const columns: ProColumns<any, 'text'>[] = [
    {
      title: intl.formatMessage({
        id: 'home.module.inform.columns.ruleName',
      }),
      dataIndex: 'ruleName',
      valueType: 'text',
      align: 'left',
      hideInSearch: true,
      render: (_, record) => {
        return (
          <div>
            <DispositionPro
              placement={'topLeft'}
              maxWidth={breakpoints?.xxl ? 600 : 400}
              rows={1}
              text={record?.ruleName || '-'}
              tooltipText={
                <div>
                  <div>
                    {intl.formatMessage({
                      id: 'rule.module.text.rule.code',
                    })}
                    &nbsp;: {record?.ruleCode || '-'}
                  </div>
                  <div>
                    {intl.formatMessage({
                      id: 'home.module.inform.columns.ruleName',
                    })}
                    &nbsp;: {record?.ruleName || '-'}
                  </div>
                  <div>
                    {intl.formatMessage({
                      id: 'rule.module.text.rule.describe',
                    })}
                    &nbsp;: {record?.ruleDesc || '-'}
                  </div>
                </div>
              }
              style={{
                fontWeight: 500,
                color: 'rgb(58, 58, 58)',
              }}
            />
            <Disposition
              placement={'topLeft'}
              maxWidth={breakpoints?.xxl ? 600 : 400}
              rows={1}
              text={record?.ruleTypeNameList?.toString() || '-'}
              style={{
                color: 'rgb(166, 167, 167)',
                fontSize: 13,
              }}
            />
          </div>
        );
      },
    },
    {
      title: intl.formatMessage({
        id: 'cloudAccount.extend.title.asset.type',
      }),
      dataIndex: 'resourceTypeStr',
      valueType: 'text',
      align: 'left',
      hideInSearch: true,
      render: (_, record) => {
        return <ThemeTag text={record?.resourceTypeStr || '-'} />;
      },
    },
    {
      title: intl.formatMessage({
        id: 'home.module.inform.columns.riskLevel',
      }),
      dataIndex: 'riskLevel',
      valueType: 'select',
      valueEnum: valueListAsValueEnum(RiskLevelList),
      align: 'center',
      hideInSearch: true,
      render: (_, record) => {
        return obtainRiskLevel(RiskLevelList, record?.riskLevel as string);
      },
    },
    {
      title: intl.formatMessage({
        id: 'common.table.columns.platform',
      }),
      dataIndex: 'platform',
      valueType: 'select',
      valueEnum: valueListAsValueEnum(platformList),
      align: 'center',
      hideInSearch: true,
      render: (_, record) => {
        return obtainPlatformEasyIcon(record.platform!, platformList);
      },
    },
    {
      title: '风险数量',
      dataIndex: 'riskCount',
      valueType: 'text',
      hideInSearch: true,
      align: 'center',
      sorter: true,
      render: (_, record) => (
        <Button
          type={'link'}
          size={'small'}
          href={`/riskManagement/riskList?platform=${record?.platform}&ruleCode=${record?.ruleCode}`}
        >
          {record?.riskCount}
        </Button>
      ),
    },
    {
      title: intl.formatMessage({
        id: 'cloudAccount.extend.title.cloud.operate',
      }),
      dataIndex: 'option',
      valueType: 'option',
      align: 'center',
      fixed: 'right',
      render: (_, record) => {
        return (
          <Space size={'small'}>
            <Button
              block
              loading={scanLoading[Number(record.id)]}
              onClick={() => onClickScanByRule(record.id!)}
              type="default"
              target={'_blank'}
              size={'small'}
              icon={<PlayCircleOutlined />}
            >
              {intl.formatMessage({
                id: 'common.button.text.test',
              })}
            </Button>

            <Popconfirm
              title="确定要移出自选规则吗？"
              onConfirm={() => onClickRemoveFromSelected(record)}
              okText="确定"
              cancelText="取消"
            >
              <Button
                danger
                size="small"
                loading={removeLoading[Number(record.id)]}
                icon={<MinusOutlined />}
              >

              </Button>
            </Popconfirm>
          </Space>
        );
      },
    },
  ];

  return (
    <>
      {contextHolder}
      <ProTable<any>
        scroll={{ x: 'max-content' }}
        rowSelection={{
          type: 'checkbox',
          selectedRowKeys: selectedRowKeys,
          onChange: (selectedRowKeys) => setSelectedRowKeys(selectedRowKeys),
          preserveSelectedRowKeys: true,
        }}
        headerTitle={
          <div className={styleType['customTitle']}>
            <Space>
              自选规则
            </Space>
          </div>
        }
        actionRef={tableActionRef}
        formRef={formActionRef}
        rowClassName={activeRowType}
        rowKey="id"
        search={false}
        toolBarRender={() => [
          <Button
            key="BATCH_DETECT"
            type="primary"
            loading={batchScanLoading}
            disabled={selectedRowKeys.length === 0}
            onClick={onClickBatchScan}
          >
            批量检测
          </Button>,
          <Button
            key="BATCH_REMOVE"
            danger
            loading={batchRemoveLoading}
            disabled={selectedRowKeys.length === 0}
            onClick={onClickBatchRemove}
          >
            批量移出自选
          </Button>,
        ]}
        request={requestSelectedRulesData}
        columns={columns}
        pagination={{
          showQuickJumper: false,
          showSizeChanger: true,
          defaultPageSize: 10,
          defaultCurrent: 1,
        }}
      />
    </>
  );
};

export default SelectedRules;