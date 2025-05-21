import { queryAggregatedData } from '@/services/home/homeController';
import { overviewURLMap } from '@/utils/const';
import { CaretDownOutlined, CaretUpOutlined } from '@ant-design/icons';
import { ProCard } from '@ant-design/pro-components';
import { useIntl, useRequest } from '@umijs/max';
import { Breakpoint, Col, Flex, Grid, Row } from 'antd';
import React, { useEffect } from 'react';
import styles from '../index.less';
const { useBreakpoint } = Grid;

interface IInstructView {
  nowCount: number | undefined;
  yesterdayCount: number | undefined;
}

const InstructView: React.FC<IInstructView> = (props) => {
  const { nowCount = 0, yesterdayCount = 0 } = props;
  if (nowCount === yesterdayCount) return <></>;
  return (
    <span
      className={
        nowCount >= yesterdayCount
          ? styles['instructViewWrapUp']
          : styles['instructViewWrapDown']
      }
    >
      (
      <span className={styles['instructViewMain']}>
        <span className={styles['instructViewIcon']}>
          {nowCount >= yesterdayCount ? (
            <CaretUpOutlined className={styles['upArrowIcon']} />
          ) : (
            <CaretDownOutlined className={styles['downArrowIcon']} />
          )}
        </span>
        <span className={styles['instructViewCount']}>
          {Math.abs(nowCount - yesterdayCount).toFixed(0)}
        </span>
      </span>
      )
    </span>
  );
};

// Data Overview Card
const Overview: React.FC = () => {
  // Ant Design Provide monitoring of screen width changes
  const breakpoints: Partial<Record<Breakpoint, boolean>> = useBreakpoint();
  // Intl API
  const intl = useIntl();

  // Obtain aggregated data
  const {
    data: aggregatedData,
    run: requestAggregatedData,
    loading: aggregatedDataLoading,
  } = useRequest(
    () => {
      return queryAggregatedData({});
    },
    {
      manual: true,
      formatResult: (r: API.Result_AggregatedInfo) => {
        return r.content;
      },
    },
  );

  useEffect((): void => {
    requestAggregatedData();
  }, []);

  return (
    <ProCard
      bodyStyle={{ padding: 0 }}
      loading={aggregatedDataLoading}
      style={{
        backgroundColor: aggregatedDataLoading ? '#FFF' : 'transparent',
      }}
    >
      <Row gutter={[16, 16]}>
        <Col span={breakpoints.xs ? 24 : 6}>
          <ProCard className={styles['overviewCard']}>
            <Flex
              justify={'space-between'}
              align={'center'}
              style={{ width: '100%' }}
            >
              <a
                className={styles['overviewContent']}
                href={'/cloudAccount/accountList'}
                target={'_self'}
                rel={'prefetch'}
              >
                <div className={styles['overviewContentName']}>
                  {intl.formatMessage({
                    id: 'home.module.overview.platform',
                  })}
                </div>
                <div className={styles['overviewContentValue']}>
                  {aggregatedData?.platformCount || 0}
                  <InstructView
                    nowCount={aggregatedData?.platformCount}
                    yesterdayCount={
                      aggregatedData?.yesterdayHomeAggregatedDataVO
                        ?.platformCount
                    }
                  />
                </div>
              </a>
              <img
                className={styles['overviewIconDefault']}
                src={overviewURLMap['CLOUD_PLATFORM']}
                alt={'CLOUD_PLATFORM'}
              />
            </Flex>
          </ProCard>
        </Col>
        <Col span={breakpoints.xs ? 24 : 6}>
          <ProCard className={styles['overviewCard']}>
            <Flex
              justify={'space-between'}
              align={'center'}
              style={{ width: '100%' }}
            >
              <a
                className={styles['overviewContent']}
                href={'/cloudAccount/accountList'}
                target={'_self'}
                rel={'prefetch'}
              >
                <div className={styles['overviewContentName']}>
                  {intl.formatMessage({
                    id: 'home.module.overview.account',
                  })}
                </div>
                <div className={styles['overviewContentValue']}>
                  {aggregatedData?.cloudAccountCount || 0}
                  <InstructView
                    nowCount={aggregatedData?.cloudAccountCount}
                    yesterdayCount={
                      aggregatedData?.yesterdayHomeAggregatedDataVO
                        ?.cloudAccountCount
                    }
                  />
                </div>
              </a>
              <img
                className={styles['overviewIconDefault']}
                src={overviewURLMap['CLOUD_ACCOUNT']}
                alt={'CLOUD_ACCOUNT'}
              />
            </Flex>
          </ProCard>
        </Col>
        <Col span={breakpoints.xs ? 24 : 6}>
          <ProCard className={styles['overviewCard']}>
            <Flex
              justify={'space-between'}
              align={'center'}
              style={{ width: '100%' }}
            >
              <a
                className={styles['overviewContent']}
                href={'/assetManagement/polymerize'}
                target={'_self'}
                rel={'prefetch'}
              >
                <div className={styles['overviewContentName']}>
                  {intl.formatMessage({
                    id: 'home.module.overview.asset',
                  })}
                </div>
                <div className={styles['overviewContentValue']}>
                  {aggregatedData?.resourceCount || 0}
                  <InstructView
                    nowCount={aggregatedData?.resourceCount}
                    yesterdayCount={
                      aggregatedData?.yesterdayHomeAggregatedDataVO
                        ?.resourceCount
                    }
                  />
                </div>
              </a>
              <img
                className={styles['overviewIconDefault']}
                src={overviewURLMap['CLOUD_ASSET']}
                alt={'CLOUD_ASSET'}
              />
            </Flex>
          </ProCard>
        </Col>
        <Col span={breakpoints.xs ? 24 : 6}>
          <ProCard className={styles['overviewCard']}>
            <Flex
              justify={'space-between'}
              align={'center'}
              style={{ width: '100%' }}
            >
              <a
                className={styles['overviewContent']}
                href={'/riskManagement/riskList'}
                target={'_self'}
                rel={'prefetch'}
              >
                <div className={styles['overviewContentName']}>
                  {intl.formatMessage({
                    id: 'home.module.overview.risk',
                  })}
                </div>
                <div className={styles['overviewContentValue']}>
                  {aggregatedData?.riskCount || 0}
                  <InstructView
                    nowCount={aggregatedData?.riskCount}
                    yesterdayCount={
                      aggregatedData?.yesterdayHomeAggregatedDataVO?.riskCount
                    }
                  />
                </div>
              </a>
              <img
                className={styles['overviewIconDefault']}
                src={overviewURLMap['CLOUD_RISK']}
                alt={'CLOUD_RISK'}
              />
            </Flex>
          </ProCard>
        </Col>
      </Row>
    </ProCard>
  );
};

export default Overview;
