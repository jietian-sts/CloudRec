import DISCOVER from '@/assets/images/DISCOVER.svg';
import SCAN from '@/assets/images/SCAN.svg';
import Disposition from '@/components/Disposition';
import CheckInform from '@/pages/RiskManagement/components/CheckInform';
import EvaluateDrawer from '@/pages/RiskManagement/components/EvaluateDrawer';
import LogInformation from '@/pages/RiskManagement/components/LogInformation';
import ResourceDrawer from '@/pages/RiskManagement/components/ResourceDrawer';
import {
  IgnoreReasonTypeList,
  RiskStatusList,
} from '@/pages/RiskManagement/const';
import { queryRiskDetailById } from '@/services/risk/RiskController';
import { IValueType } from '@/utils/const';
import { obtainPlatformIcon, obtainRiskStatus } from '@/utils/shared';
import { ProfileOutlined } from '@ant-design/icons';
import { ActionType, ProCard } from '@ant-design/pro-components';
import { useIntl, useModel, useRequest } from '@umijs/max';
import {
  Button,
  ConfigProvider,
  Drawer,
  Flex,
  Form,
  Space,
  Tag,
  Tooltip,
  Typography,
} from 'antd';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import styles from '../index.less';
const { Text } = Typography;

interface IRiskDrawerProps {
  riskDrawerVisible: boolean;
  setRiskDrawerVisible: Dispatch<SetStateAction<boolean>>;
  riskDrawerInfo: Record<string, any>;
  tableActionRef?: React.RefObject<ActionType | undefined>;
  locate: 'risk' | 'asset';
}

// Risk Details
const RiskDrawer: React.FC<IRiskDrawerProps> = (props) => {
  // Component Props
  const { riskDrawerVisible, riskDrawerInfo, setRiskDrawerVisible, locate } =
    props;
  // Global Props
  const { platformList } = useModel('rule');
  const { tenantListAll } = useModel('tenant');
  // Intl API
  const intl = useIntl();
  // Testing situation
  const [evaluateDrawerVisible, setEvaluateDrawerVisible] =
    useState<boolean>(false);
  // Asset Details
  const [resourceDrawerVisible, setResourceDrawerVisible] =
    useState<boolean>(false);

  const initDrawer = (): void => {
    setRiskDrawerVisible(false);
  };

  // Risk detail data
  const {
    data: riskInfo,
    run: requestRiskDetailById,
    loading: riskDetailLoading,
  }: any = useRequest(
    (id) => {
      return queryRiskDetailById({ riskId: id });
    },
    {
      manual: true,
      formatResult: (r: any) => {
        return r.content || {};
      },
    },
  );

  const onClickCloseDrawerForm = (): void => {
    initDrawer();
  };

  useEffect((): void => {
    if (riskDrawerVisible && riskDrawerInfo?.id) {
      requestRiskDetailById(riskDrawerInfo.id);
    }
  }, [riskDrawerVisible, riskDrawerInfo]);

  return (
    <>
      <Drawer
        title={intl.formatMessage({
          id: 'risk.module.text.detail.info',
        })}
        width={'50%'}
        open={riskDrawerVisible}
        onClose={onClickCloseDrawerForm}
        loading={riskDetailLoading}
      >
        <ProCard
          style={{ marginBottom: 20 }}
          bodyStyle={{
            backgroundColor: '#f9f9f9',
            padding: '16px 20px',
          }}
        >
          <Flex
            justify={'space-between'}
            align={'center'}
            style={{ marginBottom: 10 }}
          >
            <span>
              <Text style={{ marginRight: 12 }}>
                <Button
                  type={'link'}
                  href={`/ruleManagement/ruleProject/edit?id=${riskInfo?.ruleId}`}
                  target={'_blank'}
                  style={{ padding: '4px 0 4px 4px', fontSize: '18px' }}
                >
                  {riskInfo?.ruleVO?.ruleName || '-'}
                </Button>
              </Text>

              <Space>
                {/* Risk status */}
                <span>
                  {obtainRiskStatus(RiskStatusList, riskInfo?.status)}
                </span>
                {riskInfo?.ignoreReasonType && (
                  <span>
                    <Text
                      style={{
                        marginRight: 8,
                        color: 'rgba(127, 127, 127, 1)',
                      }}
                    >
                      {intl.formatMessage({
                        id: 'risk.module.text.ignore.type',
                      })}
                      &nbsp;:&nbsp;
                    </Text>
                    <Tag color="geekblue">
                      {IgnoreReasonTypeList.find(
                        (item: IValueType): boolean =>
                          item.value === riskInfo?.ignoreReasonType,
                      )?.label || '-'}
                    </Tag>
                  </span>
                )}
                {riskInfo?.ignoreReason && (
                  <>
                    <Text
                      style={{
                        marginRight: 8,
                        color: 'rgba(127, 127, 127, 1)',
                      }}
                    >
                      {intl.formatMessage({
                        id: 'risk.module.text.ignore.reason',
                      })}
                      &nbsp;:&nbsp;
                    </Text>
                    <Disposition
                      rows={1}
                      text={riskInfo?.ignoreReason}
                      maxWidth={64}
                    />
                  </>
                )}
              </Space>
            </span>
            {/*<Button*/}
            {/*  type={'link'}*/}
            {/*  onClick={() => setEvaluateDrawerVisible(true)}*/}
            {/*>*/}
            {/*  <Flex align={'center'}>*/}
            {/*    <img*/}
            {/*      src={RISK_EVALUATE}*/}
            {/*      style={{ width: 18, height: 18, marginRight: 4 }}*/}
            {/*      alt="RISK_EVALUATE_ICON"*/}
            {/*    />*/}
            {/*    <span style={{ textDecoration: 'underline', color: '#457aff' }}>*/}
            {/*      检测情况*/}
            {/*    </span>*/}
            {/*  </Flex>*/}
            {/*</Button>*/}
          </Flex>
          <Flex vertical={true} gap={10}>
            <Flex align={'center'}>
              <img
                src={SCAN}
                alt="DISCOVER_ICON"
                style={{ width: 14, height: 14 }}
              />
              <span
                style={{
                  color: 'rgba(127, 127, 127, 1)',
                  margin: '0 8px 0 6px',
                }}
              >
                {intl.formatMessage({
                  id: 'risk.module.text.recently.scanned.hits',
                })}
                &nbsp;:&nbsp;
              </span>
              <span style={{ color: 'rgba(51, 51, 51, 1)' }}>
                {riskInfo?.gmtModified}
              </span>
            </Flex>
            <Text>
              <img
                src={DISCOVER}
                alt="DISCOVER_ICON"
                style={{ width: 14, height: 14 }}
              />
              <span
                style={{
                  color: 'rgba(127, 127, 127, 1)',
                  margin: '0 8px 0 6px',
                }}
              >
                {intl.formatMessage({
                  id: 'risk.module.text.first.discovery.time',
                })}
                &nbsp;:&nbsp;
              </span>
              <span style={{ color: 'rgba(51, 51, 51, 1)' }}>
                {riskInfo?.gmtCreate}
              </span>
            </Text>
          </Flex>

          <Flex
            justify={'start'}
            align={'center'}
            style={{ margin: '10px 0 6px 0' }}
          >
            <span style={{ marginRight: 5, color: 'rgba(127, 127, 127, 1)' }}>
              {obtainPlatformIcon(riskInfo?.platform, platformList)}
            </span>
            <Text style={{ marginRight: 20, color: 'rgba(127, 127, 127, 1)' }}>
              {riskInfo?.resourceType || '-'}
            </Text>
            <Flex align={'center'}>
              <span
                style={{
                  marginRight: 4,
                  color: 'rgba(127, 127, 127, 1)',
                }}
              >
                {riskInfo?.resourceName + ' / ' + riskInfo?.resourceId}
              </span>

              {locate === 'risk' && (
                <Tooltip
                  title={intl.formatMessage({
                    id: 'asset.extend.text.detail',
                  })}
                >
                  <span
                    className={styles['iconWrap']}
                    onClick={() => setResourceDrawerVisible(true)}
                  >
                    <ProfileOutlined className={styles['resourceInstance']} />
                  </span>
                </Tooltip>
              )}
            </Flex>
            <Text style={{ color: 'rgba(127, 127, 127, 1)', margin: '0 12px' }}>
              {tenantListAll?.find(
                (item: IValueType) => item.value === riskInfo?.tenantId,
              )?.label || '-'}
            </Text>
          </Flex>
        </ProCard>

        <ConfigProvider
          theme={{
            components: {
              Form: {
                itemMarginBottom: 8,
                labelColor: 'rgba(127, 127, 127, 1)',
                labelColonMarginInlineEnd: 16,
              },
            },
          }}
        >
          <Form>
            <Form.Item
              label={intl.formatMessage({
                id: 'rule.module.text.repair.suggestions',
              })}
            >
              <span style={{ color: 'rgb(51, 51, 51)' }}>
                {riskInfo?.ruleVO?.advice || '-'}
              </span>
            </Form.Item>
            <Form.Item
              label={intl.formatMessage({
                id: 'risk.module.text.reference.link',
              })}
            >
              <span style={{ color: 'rgb(51, 51, 51)' }}>
                {riskInfo?.ruleVO?.link || '-'}
              </span>
            </Form.Item>
            <Form.Item
              label={intl.formatMessage({
                id: 'rule.module.text.rule.describe',
              })}
            >
              <span style={{ color: 'rgb(51, 51, 51)' }}>
                {riskInfo?.ruleVO?.ruleDesc || '-'}
              </span>
            </Form.Item>
          </Form>
        </ConfigProvider>

        {/** Testing situation **/}
        <CheckInform riskDrawerInfo={riskDrawerInfo} />

        {/** Logging - Add Log **/}
        <LogInformation riskDrawerInfo={riskDrawerInfo} />
      </Drawer>

      <EvaluateDrawer // Testing situation
        evaluateDrawerVisible={evaluateDrawerVisible}
        setEvaluateDrawerVisible={setEvaluateDrawerVisible}
        riskDrawerInfo={riskDrawerInfo}
      />

      <ResourceDrawer // Asset Details
        resourceDrawerVisible={resourceDrawerVisible}
        setResourceDrawerVisible={setResourceDrawerVisible}
        riskDrawerInfo={riskDrawerInfo}
      />
    </>
  );
};

export default RiskDrawer;
