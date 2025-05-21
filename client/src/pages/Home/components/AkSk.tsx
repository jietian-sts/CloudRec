import BasicProCard from '@/components/Common/BasicProCard';
import { queryAccessKeyAndAclSituation } from '@/services/home/homeController';
import { obtainPlatformIcon } from '@/utils/shared';
import { ProCard } from '@ant-design/pro-components';
import { Chart } from '@antv/g2';
import { useIntl, useModel, useRequest } from '@umijs/max';
import { Carousel, Empty, Flex, Spin } from 'antd';
import { isEmpty } from 'lodash';
import React, { useEffect, useRef } from 'react';
import styles from '../index.less';

interface IChartComponent {
  data: { list: any[]; total: number; platform?: string };
}

const ChartComponent: React.FC<IChartComponent> = (props) => {
  // Component Props
  const { data } = props;
  // Intl API
  const intl = useIntl();
  // DRAW CONTAINER
  const mountNodeRef: React.MutableRefObject<HTMLDivElement | null> =
    useRef(null);
  // Chart Instance
  const chart = useRef<any>(null);

  // Ring chart rendering
  const renderG2Chart = (data: { list: any[]; total: number }): void => {
    const { list = [] } = data;
    let total = data.total || 0;

    if (!isEmpty(list) && !isEmpty(mountNodeRef.current)) {
      // Complete display of legend risk labels
      let isBitValue: boolean = false;
      if (list?.[0]?.value < 10 && list?.[1]?.value < 10) {
        isBitValue = true;
      }

      chart.current = new Chart({
        container: mountNodeRef.current!, // Canvas Container
        autoFit: true, // If you want the width and height of the chart to be consistent with the container, you can set option.autoFit to true, which takes priority over the specified width and height.
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
        .scale('color', { range: ['#7EA3FF', '#FF7576'] })
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
            id: 'home.module.akSk.total',
          }),
        )
        .style('x', '50%')
        .style('y', '50%')
        .style('dy', 22)
        .style('fontSize', 14)
        .style('fill', '#999')
        .style('textAlign', 'center')
        .tooltip(false);

      // Chart rendering
      chart?.current?.render();
    }
  };

  useEffect((): void => {
    renderG2Chart(data);
  }, [data]);

  useEffect(() => {
    return (): void => {
      chart?.current?.destroy();
    };
  }, []);

  return <div ref={mountNodeRef} style={{ width: '100%', height: '98%' }} />;
};

// AkSk
const AkSk: React.FC = () => {
  // Platform List
  const { platformList } = useModel('rule');
  // Intl API
  const intl = useIntl();
  // Obtain high-risk, medium risk, and low-risk data to be processed
  const {
    data: accessKeyData,
    run: requestAccessKeyAndAclSituation,
    loading: accessKeyDataLoading,
  } = useRequest(
    () => {
      return queryAccessKeyAndAclSituation({});
    },
    {
      manual: true,
      formatResult: (r: API.Result_AccessKeyInfo) => {
        return (
          r?.content?.map((item) => {
            const {
              accessKeyCount,
              accessKeyExistAclCount,
              accessKeyNotExistAclCount,
              platform,
            } = item;
            const list = [
              {
                legend: `${intl.formatMessage({
                  id: 'home.module.akSk.exist.ACL',
                })} ${accessKeyExistAclCount}`,
                name: `${intl.formatMessage({
                  id: 'home.module.akSk.exist.ACL',
                })}`,
                value: accessKeyExistAclCount,
              },
              {
                legend: `${intl.formatMessage({
                  id: 'home.module.akSk.no.ACL',
                })} ${accessKeyNotExistAclCount}`,
                name: `${intl.formatMessage({
                  id: 'home.module.akSk.no.ACL',
                })}`,
                value: accessKeyNotExistAclCount,
              },
            ];

            return {
              list,
              total: accessKeyCount,
              platform,
            };
          }) || []
        );
      },
    },
  );

  useEffect(() => {
    requestAccessKeyAndAclSituation();
  }, []);

  return (
    <ProCard className={styles['bannerWrap']} bodyStyle={{ padding: 0 }}>
      {accessKeyDataLoading ? (
        <Flex
          style={{ width: '100%', height: '100%' }}
          align={'center'}
          justify={'center'}
        >
          <Spin spinning={accessKeyDataLoading} />
        </Flex>
      ) : (
        <>
          {accessKeyData && accessKeyData?.length > 0 ? (
            <Carousel
              arrows={false}
              autoplay={true}
              autoplaySpeed={3000}
              infinite={true}
              draggable={true}
            >
              {accessKeyData?.map((item, index) => {
                return (
                  <BasicProCard
                    className={styles['akCard']}
                    title={intl.formatMessage({
                      id: 'home.module.akSk.number',
                    })}
                    key={index}
                    extra={
                      <Flex wrap={'nowrap'}>
                        {obtainPlatformIcon(item.platform!, platformList)}
                      </Flex>
                    }
                  >
                    <ChartComponent key={index} data={item} />
                  </BasicProCard>
                );
              })}
            </Carousel>
          ) : (
            <Flex
              style={{ width: '100%', height: '100%' }}
              vertical={true}
              align={'center'}
              justify={'center'}
            >
              <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
            </Flex>
          )}
        </>
      )}
    </ProCard>
  );
};

export default AkSk;
