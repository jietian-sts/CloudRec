import BasicProCard from '@/components/Common/BasicProCard';
import { queryRiskTrend } from '@/services/home/homeController';
import { Chart } from '@antv/g2';
import { useIntl, useRequest } from '@umijs/max';
import { Empty, Flex, Spin } from 'antd';
import { cloneDeep, isEmpty } from 'lodash';
import React, { useEffect, useRef } from 'react';
import styles from '../index.less';

const PROCESSED = 'rgba(69,122,255,1)';
const UNTREATED = 'rgba(255,117,118,1)';

// Recent Trends in Line Chart
const TrendChart: React.FC = () => {
  // Draw container
  const mountNodeRef: React.MutableRefObject<HTMLDivElement | null> =
    useRef(null);
  // Chart Instance
  const chart = useRef<any>(null);
  // Intl API
  const intl = useIntl();

  // Format the returned data
  const formatData = (data: Array<API.BaseRiskTrendInfo>) => {
    const dataList = cloneDeep(data);
    const range: Array<string> = [];
    const array: Array<API.BaseRiskTrendInfo> = [];
    // Processed
    const processed = dataList?.some((element) => element.type === '已处理');
    // Untreated
    const untreated = dataList?.some((element) => element.type === '未处理');
    if (processed) range.push(PROCESSED);
    if (untreated) range.push(UNTREATED);
    dataList?.forEach((item) => {
      if (item.type === '已处理')
        array.push({
          ...item,
          type: intl.formatMessage({
            id: 'home.module.trend.handled',
          }),
        });
    });
    dataList?.forEach((item) => {
      if (item.type === '未处理')
        array.push({
          ...item,
          type: intl.formatMessage({
            id: 'home.module.trend.untreated',
          }),
        });
    });
    return { range, array };
  };

  // Line chart rendering
  const renderG2Chart = (data: Array<API.BaseRiskTrendInfo>): void => {
    if (!isEmpty(data) && !isEmpty(mountNodeRef.current)) {
      const { range, array } = formatData(data);
      chart.current = new Chart({
        container: mountNodeRef.current!, // Canvas Container
        autoFit: true, // If you want the width and height of the chart to be consistent with the container, you can set option.autoFit to true, which takes priority over the specified width, height, and height.
        paddingLeft: 36,
        paddingRight: 24,
        paddingTop: 24,
        paddingBottom: 12,
      });

      chart.current
        .data(array)
        .encode('x', 'date')
        .encode('y', 'count')
        .encode('color', 'type')
        .scale('y', {
          nice: true,
          domainMin: 0,
        })
        .scale('color', { range })
        .axis('y', {
          // Title
          title: false,
          // Line
          line: false,
          // Label
          labelFontSize: 12,
          labelFill: 'rgb(133,137,143)',
          // Tick Scale
          tickLength: 4,
          tickStroke: 'rgb(133,137,143)',
          tickStrokeOpacity: 1,
          // Scale value conversion to avoid overlapping between texts. Currently, supports ultra long text abbreviations, hidden overlapping scale values, and automatic rotation
          transform: true,
          labelAutoHide: true,
        })
        .axis('x', {
          // Title
          title: false,
          // Line
          line: true,
          lineStroke: 'rgb(133,137,143)',
          lineStrokeOpacity: 1,
          // Label
          labelFontSize: 12,
          labelFill: 'rgb(133,137,143)',
          // Tick Scale
          tickLength: 4,
          tickStroke: 'rgb(133,137,143)',
          tickStrokeOpacity: 1,
          // Scale value conversion to avoid overlapping between texts. Currently, supports ultra long text abbreviations, hidden overlapping scale values, and automatic rotation
          transform: true,
          labelAutoHide: true,
        })
        // https://g2.antv.antgroup.com/spec/component/legend
        .legend('color', {
          layout: {
            justifyContent: 'flex-end',
          },
        });

      chart.current?.area().style('fillOpacity', 0.06);

      chart.current?.line().style('lineWidth', 1.6).tooltip(false);

      // Line chart rendering
      chart?.current?.render();
    }
  };

  // Obtain the risk trend of the past 7 days
  const {
    data: riskTrendData,
    run: requestRiskTrendData,
    loading: riskTrendDataLoading,
  } = useRequest(
    () => {
      return queryRiskTrend({});
    },
    {
      manual: true,
      formatResult: (r: API.Result_RiskLevelInfo) => {
        return r.content;
      },
    },
  );

  useEffect(() => {
    // Data Request
    requestRiskTrendData();

    return (): void => {
      chart?.current?.destroy();
    };
  }, []);

  useEffect((): void => {
    renderG2Chart(riskTrendData as any);
  }, [riskTrendDataLoading]);

  return (
    <BasicProCard
      title={intl.formatMessage({
        id: 'home.module.trend.data',
      })}
      className={styles['g2ChartCard']}
      bodyStyle={{ padding: '16px' }}
    >
      <div ref={mountNodeRef} style={{ width: '100%', height: '98%' }}>
        {riskTrendDataLoading && (
          <Flex
            style={{ width: '100%', height: '100%' }}
            align={'center'}
            justify={'center'}
          >
            <Spin spinning={riskTrendDataLoading} />
          </Flex>
        )}
        {isEmpty(riskTrendData) && (
          <div style={{ paddingTop: 47 }}>
            <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
          </div>
        )}
      </div>
    </BasicProCard>
  );
};

export default TrendChart;
