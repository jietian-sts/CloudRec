import styleType from '@/components/Common/index.less';
import { queryGroupTypeList } from '@/services/resource/ResourceController';
import {
  checkExistNewRule,
  queryExportRuleList,
  loadRuleFromGithub,
} from '@/services/rule/RuleController';
import { RiskLevelList } from '@/utils/const';
import {
  BlobExportZIPFn,
  valueListAddIcon,
  valueListAddTag,
} from '@/utils/shared';
import {
  PageContainer,
  ProCard,
} from '@ant-design/pro-components';
import { useIntl, useLocation, useModel, useRequest } from '@umijs/max';
import {
  Button,
  Cascader,
  Checkbox,
  Col,
  Form,
  message,
  Popover,
  Row,
  Select,
  Space,
  Tabs,
} from 'antd';
import { SearchOutlined, ReloadOutlined } from '@ant-design/icons';
import { isEmpty } from 'lodash';
import React, { useEffect, useState } from 'react';
import RuleMarket from './components/RuleMarket';
import SelectedRules from './components/SelectedRules';
import styles from './index.less';
const { SHOW_CHILD } = Cascader;

// Rule Name Custom-Filter-Option
const filterOption = (
  input: string,
  item: { [key: string]: string },
): boolean => item.label?.includes(input);

const RuleProject: React.FC = () => {
  // Global Props
  const { platformList, ruleTypeList, allRuleList, ruleGroupList } =
    useModel('rule');
  // URL Query
  const { search } = useLocation();
  const searchParams: URLSearchParams = new URLSearchParams(search);
  // Rule group query
  let groupIdQuery = searchParams.get('groupId');
  // Rule Name Query
  let ruleCodeQuery = searchParams.get('ruleCode');
  // Platform Query
  let platformQuery = searchParams.get('platform');
  // Message API
  const [messageApi, contextHolder] = message.useMessage();
  // FormInstance
  const [form] = Form.useForm();
  // Intl API
  const intl = useIntl();
  // Sync Rules Popover Visible
  const [popoverVisible, setPopoverVisible] = useState<boolean>(false);
  // Sync Rules Loading Status
  const [syncLoading, setSyncLoading] = useState<boolean>(false);
  // New Rules Count
  const [newRulesCount, setNewRulesCount] = useState<number>(0);
  // Export Loading
  const [exportLoading, setExportLoading] = useState<boolean>(false);
  // Selected Row Keys
  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);
  // List of Resource Types
  const [resourceTypeList, setResourceTypeList] = useState<any[]>([]);
  // Active Tab Key
  const [activeTabKey, setActiveTabKey] = useState<string>('market');
  // Query Loading
  const [queryLoading, setQueryLoading] = useState<boolean>(false);
  // Query Trigger - 用于触发子组件重新加载数据
  const [queryTrigger, setQueryTrigger] = useState<number>(0);

  // Check for new rules
  const checkNewRules = async (): Promise<void> => {
    try {
      const result = await checkExistNewRule();
      if (result.code === 200 && typeof result.content === 'number') {
        setNewRulesCount(result.content);
      }
    } catch (error) {
      console.error('Failed to check new rules:', error);
    }
  };
  
  // According to the cloud platform, obtain a list of resource types
  const { run: requestResourceTypeList } = useRequest(
    (list: string[]) => {
      return queryGroupTypeList({ platformList: list });
    },
    {
      manual: true,
      formatResult: (result): void => {
        const { content } = result;
        setResourceTypeList((content as any) || []);
      },
    },
  );

  const onClickExportRuleList = (selectedRowKeys?: React.Key[]) => {
    setExportLoading(true);

    queryExportRuleList(
      selectedRowKeys ? { idList: selectedRowKeys } as any : undefined,
      { responseType: 'blob' }
    )
      .then((r) => {
        BlobExportZIPFn(
          r,
          `CloudRec ${intl.formatMessage({
            id: 'rule.module.text.rule.data',
          })}`,
        );
        messageApi.success(
          intl.formatMessage({ id: 'common.message.text.export.success' }),
        );
      })
      .finally(() => setExportLoading(false));
  };

  useEffect((): void => {
    // Group Name
    if (!isEmpty(groupIdQuery)) {
      form.setFieldValue('ruleGroupIdList', [Number(groupIdQuery)]);
    }
    // Rule Name
    if (!isEmpty(ruleCodeQuery)) {
      form.setFieldValue('ruleCodeList', [ruleCodeQuery]);
    }
    // Cloud Platform
    if (!isEmpty(platformQuery)) {
      form.setFieldValue('platformList', [platformQuery]);
      requestResourceTypeList([platformQuery!]);
    }
    // Check for new rules on component mount
    checkNewRules();
  }, [groupIdQuery, ruleCodeQuery, platformQuery]);

  // Tab items configuration - 这部分代码已经不需要了，因为我们在下面直接定义了tabs

  return (
    <PageContainer
      ghost
      title={false}
      className={styles['rulePageContainer']}
      breadcrumbRender={false}
    >
      {contextHolder}
      <ProCard
        bodyStyle={{ paddingBottom: 0 }}
        className={styles['customFilterCard']}
      >
        <Form form={form}>
          <Row gutter={[24, 10]}>
            <Col span={24}>
              <Form.Item
                name="platformList"
                label={intl.formatMessage({
                  id: 'common.select.label.cloudPlatform',
                })}
                style={{ marginBottom: 0 }}
              >
                <Checkbox.Group
                  options={valueListAddIcon(platformList)}
                  onChange={(checkedValue): void => {
                    form.setFieldValue('resourceTypeList', null);
                    setResourceTypeList([]);
                    requestResourceTypeList(checkedValue as any);
                  }}
                />
              </Form.Item>
            </Col>
            <Col span={24}>
              <Form.Item
                name="riskLevelList"
                label={intl.formatMessage({
                  id: 'home.module.inform.columns.riskLevel',
                })}
                style={{ marginBottom: 0 }}
              >
                <Checkbox.Group options={valueListAddTag(RiskLevelList)} />
              </Form.Item>
            </Col>
            <Col span={6}>
              <Form.Item
                name="ruleCodeList"
                label={intl.formatMessage({
                  id: 'home.module.inform.columns.ruleName',
                })}
                style={{ marginBottom: 0 }}
              >
                <Select
                  // @ts-ignore
                  filterOption={filterOption}
                  placeholder={intl.formatMessage({
                    id: 'common.select.text.placeholder',
                  })}
                  allowClear
                  options={allRuleList}
                  mode={'multiple'}
                />
              </Form.Item>
            </Col>
            <Col span={6}>
              <Form.Item
                name="resourceTypeList"
                label={intl.formatMessage({
                  id: 'cloudAccount.extend.title.asset.type',
                })}
                style={{ marginBottom: 0, width: '100%' }}
              >
                <Cascader
                  options={resourceTypeList}
                  multiple
                  placeholder={intl.formatMessage({
                    id: 'common.select.text.placeholder',
                  })}
                  showCheckedStrategy={SHOW_CHILD}
                  allowClear
                  showSearch
                />
              </Form.Item>
            </Col>
            <Col span={6}>
              <Form.Item
                name="ruleGroupIdList"
                label={intl.formatMessage({
                  id: 'layout.routes.title.ruleGroup',
                })}
                style={{ marginBottom: 0, width: '100%' }}
              >
                <Select
                  placeholder={intl.formatMessage({
                    id: 'common.select.text.placeholder',
                  })}
                  options={ruleGroupList}
                  allowClear
                  mode={'multiple'}
                />
              </Form.Item>
            </Col>
            <Col span={6}>
              <Form.Item
                name="ruleTypeIdList"
                label={intl.formatMessage({
                  id: 'home.module.inform.columns.ruleTypeName',
                })}
                style={{ marginBottom: 0, width: '100%' }}
              >
                <Cascader
                  placeholder={intl.formatMessage({
                    id: 'common.select.text.placeholder',
                  })}
                  showCheckedStrategy={SHOW_CHILD}
                  allowClear
                  showSearch
                  fieldNames={{
                    label: 'typeName',
                    value: 'id',
                    children: 'childList',
                  }}
                  multiple
                  options={ruleTypeList || []}
                />
              </Form.Item>
            </Col>
            <Col span={24}>
              <Form.Item style={{ marginBottom: 0, textAlign: 'right' }}>
                <Space>
                  <Button
                    type="primary"
                    icon={<SearchOutlined />}
                    loading={queryLoading}
                    onClick={() => {
                      setQueryLoading(true);
                      // 触发子组件重新加载数据
                      setQueryTrigger(prev => prev + 1);
                      setTimeout(() => {
                        setQueryLoading(false);
                      }, 500);
                    }}
                  >
                    {intl.formatMessage({
                      id: 'common.button.text.query',
                    }) || '查询'}
                  </Button>
                  <Button
                    icon={<ReloadOutlined />}
                    onClick={() => {
                      form.resetFields();
                      setResourceTypeList([]);
                      // 重置后触发查询
                      setQueryLoading(true);
                      setQueryTrigger(prev => prev + 1);
                      setTimeout(() => {
                        setQueryLoading(false);
                      }, 500);
                    }}
                  >
                    {intl.formatMessage({
                      id: 'common.button.text.reset',
                    }) || '重置'}
                  </Button>
                </Space>
              </Form.Item>
            </Col>
          </Row>
        </Form>
      </ProCard>
      
      <ProCard style={{ marginTop: 16 }}>
        <Tabs
          activeKey={activeTabKey}
          onChange={(key) => {
            setActiveTabKey(key);
            // 切换tab时触发数据重新加载
            setQueryTrigger(prev => prev + 1);
          }}
          items={[
            {
              key: 'market',
              label: '规则市场',
              children: (
                <RuleMarket
                  form={form}
                  platformList={platformList}
                  resourceTypeList={resourceTypeList}
                  ruleGroupList={ruleGroupList}
                  ruleTypeList={ruleTypeList}
                  allRuleList={allRuleList}
                  checkNewRules={checkNewRules}
                  newRulesCount={newRulesCount}
                  queryTrigger={queryTrigger}
                />
              ),
            },
            {
              key: 'selected',
              label: '自选规则',
              children: (
                <SelectedRules
                  form={form}
                  platformList={platformList}
                  resourceTypeList={resourceTypeList}
                  ruleGroupList={ruleGroupList}
                  ruleTypeList={ruleTypeList}
                  allRuleList={allRuleList}
                  queryTrigger={queryTrigger}
                />
              ),
            },
          ]}
        />
      </ProCard>
    </PageContainer>
  );
};

export default RuleProject;
