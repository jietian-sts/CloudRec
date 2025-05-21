import BasicProCard from '@/components/Common/BasicProCard';
import { queryRiskLevelData } from '@/services/home/homeController';
import { Chart } from '@antv/g2';
import { useIntl, useRequest } from '@umijs/max';
import { Flex, Spin } from 'antd';
import { isEmpty } from 'lodash';
import React, { useEffect, useRef } from 'react';
import styles from '../index.less';

// Pending risks [high risk, medium risk, low risk]
const Risk: React.FC = () => {
  // Draw container
  const mountNodeRef: React.MutableRefObject<HTMLDivElement | null> =
    useRef(null);
  // Chart Instance
  const chart = useRef<any>(null);
  // Intl API
  const intl = useIntl();

  const renderG2Text = (chart: any, total: number): void => {
    if (!isEmpty(chart.current)) {
      chart.current
        .text()
        .style('text', `${total}`)
        .style('x', '50%')
        .style('y', '50%')
        .style('dy', -4)
        .style('fontSize', 28)
        .style('fill', '#000')
        .style('textAlign', 'center')
        .tooltip(false);

      chart.current
        .text()
        .style(
          'text',
          intl.formatMessage({
            id: 'home.module.risk.total',
          }),
        )
        .style('x', '50%')
        .style('y', '50%')
        .style('dy', 22)
        .style('fontSize', 14)
        .style('fill', '#999')
        .style('textAlign', 'center')
        .tooltip(false);
    }
  };

  // Ring chart rendering
  const renderG2Chart = (data: { list: any[]; total: number }): void => {
    const { list = [] } = data;
    let total = data.total || 0;

    if (!isEmpty(list) && !isEmpty(mountNodeRef.current)) {
      // Complete display of legend risk labels
      let isBitValue: boolean = false;
      if (
        list?.[0]?.value < 10 &&
        list?.[1]?.value < 10 &&
        list?.[2]?.value < 10
      ) {
        isBitValue = true;
      }
      // ChartInstance
      chart.current = new Chart({
        container: mountNodeRef.current!, // Canvas Container
        autoFit: true, // If you want the width and height of the chart to be consistent with the container, you can set option.autoFit to true, which takes priority over the specified width, height, and height.
      });

      chart.current.coordinate({
        type: 'theta',
        outerRadius: 1,
        innerRadius: 0.6,
      });

      chart.current
        .interval()
        .data(list)
        .transform({ type: 'stackY' })
        .encode('y', 'value')
        .style('stroke', 'white')
        .style('inset', 0.2)
        .style('radius', 2)
        .encode('color', 'legend')
        .scale('color', { range: ['#ec4344', '#ff7a01', 'rgb(254, 192, 11)'] })
        .interaction({
          legendFilter: false,
        })
        .legend('color', {
          position: 'right',
          layout: { justifyContent: 'center' },
          itemLabelFontSize: 13,
          itemLabelColor: '#1F2024',
          itemLabelText: (datum: Record<string, any>) => {
            return datum?.['id']?.split(' ')?.[0];
          },
          itemValueFill: 'rgb(0, 0, 0)',
          itemValueFontSize: 15,
          itemValueFontWeight: 500,
          itemValueText: (datum: Record<string, any>) => {
            return datum?.['id']?.split(' ')?.[1];
          },
          rowPadding: 14,
          itemSpan: isBitValue ? [100, 40] : [150, 100],
        })
        .tooltip((item: Record<string, any>) => ({
          name: item.name,
          value: item.value,
        }));

      renderG2Text(chart, total);
      // Chart rendering
      chart.current.render();
    }
  };

  // Obtain high-risk, medium risk, and low-risk data to be processed
  const {
    data: riskLevelData,
    run: requestRiskLevelData,
    loading: riskLevelDataLoading,
  } = useRequest(
    () => {
      return queryRiskLevelData({});
    },
    {
      manual: true,
      formatResult: (r: API.Result_RiskLevelInfo) => {
        const {
          highLevelRiskCount = 0,
          mediumLevelRiskCount = 0,
          lowLevelRiskCount = 0,
        } = r.content;

        const list = [
          {
            legend: `${intl.formatMessage({
              id: 'home.module.risk.high',
            })} ${highLevelRiskCount}`,
            name: `${intl.formatMessage({
              id: 'home.module.risk.high',
            })}`,
            value: highLevelRiskCount,
          },
          {
            legend: `${intl.formatMessage({
              id: 'home.module.risk.middle',
            })} ${mediumLevelRiskCount}`,
            name: `${intl.formatMessage({
              id: 'home.module.risk.middle',
            })}`,
            value: mediumLevelRiskCount,
          },
          {
            legend: `${intl.formatMessage({
              id: 'home.module.risk.low',
            })} ${lowLevelRiskCount}`,
            name: `${intl.formatMessage({
              id: 'home.module.risk.low',
            })}`,
            value: lowLevelRiskCount,
          },
        ];
        const total: number =
          highLevelRiskCount + mediumLevelRiskCount + lowLevelRiskCount;

        return {
          list,
          total,
        };
      },
    },
  );

  useEffect(() => {
    requestRiskLevelData();

    return (): void => {
      chart?.current?.destroy();
    };
  }, []);

  useEffect((): void => {
    if (!isEmpty(riskLevelData)) renderG2Chart(riskLevelData);
  }, [riskLevelDataLoading]);

  return (
    <BasicProCard
      className={styles['riskCard']}
      title={intl.formatMessage({
        id: 'home.module.risk.todo',
      })}
    >
      <div ref={mountNodeRef} style={{ width: '100%', height: '98%' }}>
        {riskLevelDataLoading && (
          <Flex
            style={{ width: '100%', height: '100%' }}
            align={'center'}
            justify={'center'}
          >
            <Spin spinning={riskLevelDataLoading} />
          </Flex>
        )}
      </div>
    </BasicProCard>
  );
};

export default Risk;
