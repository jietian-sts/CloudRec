import DEFAULT_RESOURCE_ICON from '@/assets/images/DEFAULT_RESOURCE_ICON.svg';
import BasicProCard from '@/components/Common/BasicProCard';
import { queryPlatformResourceData } from '@/services/home/homeController';
import { obtainPlatformIcon } from '@/utils/shared';
import { ProCard } from '@ant-design/pro-components';
import { useIntl, useModel, useRequest } from '@umijs/max';
import {
  Breakpoint,
  Carousel,
  Col,
  Empty,
  Flex,
  Grid,
  Image,
  Row,
  Spin,
} from 'antd';
import React, { useEffect } from 'react';
import styles from '../index.less';
const { useBreakpoint } = Grid;

// Merry-go-round
const Banner: React.FC = () => {
  const { platformList } = useModel('rule');
  // Ant Design Provide monitoring of screen width changes
  const breakpoints: Partial<Record<Breakpoint, boolean>> = useBreakpoint();
  // Intl API
  const intl = useIntl();
  // Obtain data on currently supported platforms and displayed resources
  const {
    data: platformResourceData,
    run: requestPlatformResourceData,
    loading: platformResourceDataLoading,
  } = useRequest(
    () => {
      return queryPlatformResourceData({});
    },
    {
      manual: true,
      formatResult: (r: API.Result_ResourceInfo) => {
        return r.content;
      },
    },
  );

  useEffect((): void => {
    requestPlatformResourceData();
  }, []);

  return (
    <ProCard bodyStyle={{ padding: 0 }} className={styles['bannerWrap']}>
      {platformResourceDataLoading ? (
        <Flex
          style={{ width: '100%', height: '100%' }}
          align={'center'}
          justify={'center'}
        >
          <Spin spinning={platformResourceDataLoading} />
        </Flex>
      ) : (
        <>
          {platformResourceData && platformResourceData?.length > 0 ? (
            <Carousel
              arrows={false}
              autoplay={true}
              autoplaySpeed={3000}
              infinite={true}
              draggable={true}
            >
              {platformResourceData?.map((item, index) => {
                return (
                  <BasicProCard
                    title={intl.formatMessage({
                      id: 'home.module.cloud.assets',
                    })}
                    key={index}
                    extra={
                      <Flex wrap={'nowrap'}>
                        {obtainPlatformIcon(item.platform!, platformList)}
                        &nbsp;
                        {intl.formatMessage({
                          id: 'home.module.assets.number',
                        })}
                        {item.total}
                      </Flex>
                    }
                    className={styles['bannerItem']}
                  >
                    <Row gutter={[0, 0]}>
                      {item?.resouceDataList?.map((elem, index) => {
                        return (
                          <Col
                            key={index}
                            span={12}
                            style={{
                              paddingRight:
                                index % 2 === 0
                                  ? breakpoints.xxl
                                    ? '12px'
                                    : '6px'
                                  : 0,
                              paddingLeft:
                                index % 2 === 1
                                  ? breakpoints.xxl
                                    ? '18px'
                                    : '12px'
                                  : 0,
                            }}
                            className={
                              index % 2 === 1
                                ? styles['bannerItemColCommon']
                                : index % 2 !== 1 && index === 0
                                ? styles['bannerItemColLeftFirst']
                                : index % 2 !== 1 &&
                                  index === item?.resouceDataList?.length - 1
                                ? styles['bannerItemColLeftLast']
                                : styles['bannerItemColLeft']
                            }
                          >
                            <a
                              href={`/assetManagement/assetList?platform=${item.platform}&resourceGroupType=${elem?.resourceGroupType}&resourceType=${elem?.resourceType}`}
                              style={{ color: 'unset' }}
                              rel={'prefetch'}
                            >
                              <Flex
                                align={'center'}
                                justify={'space-between'}
                                style={{ width: '100%' }}
                              >
                                <div className={styles['bannerItemLeft']}>
                                  <Image
                                    src={elem?.icon || DEFAULT_RESOURCE_ICON}
                                    className={styles['agentResourceIcon']}
                                    fallback={DEFAULT_RESOURCE_ICON} // 加载失败时暂时默认图标
                                    preview={false}
                                    alt="DEFAULT_ICON"
                                  />
                                  <span className={styles['agentResourceName']}>
                                    {elem.resourceGroupTypeName || '-'}
                                  </span>
                                  <span className={styles['agentResourceType']}>
                                    {elem.resourceType || '-'}
                                  </span>
                                </div>
                                <span className={styles['agentResourceCount']}>
                                  {elem.count || 0}
                                </span>
                              </Flex>
                            </a>
                          </Col>
                        );
                      })}
                    </Row>
                  </BasicProCard>
                );
              })}
            </Carousel>
          ) : (
            <ProCard className={styles['bannerEmpty']}>
              <Flex
                style={{ width: '100%', height: '100%' }}
                vertical={true}
                align={'center'}
                justify={'center'}
              >
                <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
              </Flex>
            </ProCard>
          )}
        </>
      )}
    </ProCard>
  );
};

export default Banner;
