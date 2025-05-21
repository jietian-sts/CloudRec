import styleType from '@/components/Common/index.less';
import PolymerizeCard from '@/pages/AssetManagement/components/PolymerizeCard';
import { cloudAccountBaseInfoList } from '@/services/account/AccountController';
import { queryAggregateAssets } from '@/services/asset/AssetController';
import { queryGroupTypeList } from '@/services/resource/ResourceController';
import { showTotalIntlFunc, valueListAddIcon } from '@/utils/shared';
import { useMediaQuerySize } from '@/utils/useMediaQuery';
import { PageContainer, ProCard } from '@ant-design/pro-components';
import { useIntl, useModel, useRequest } from '@umijs/max';
import {
  Button,
  Cascader,
  Checkbox,
  Col,
  Empty,
  Flex,
  Form,
  Input,
  Pagination,
  Row,
  Select,
  Space,
  Spin,
} from 'antd';
import { debounce, isEmpty } from 'lodash';
import React, { useEffect, useMemo, useState } from 'react';
import styles from './index.less';

const DEFAULT_PAGE_NUMBER = 1;
const DEFAULT_PAGE_SIZE = 12;

const AssetPolymerize: React.FC = () => {
  const { SHOW_CHILD } = Cascader;
  // Platform Rule Group List
  const { platformList } = useModel('rule');
  // Current Media Size
  const mediaSize = useMediaQuerySize({});
  // From Instance
  const [form] = Form.useForm();
  // Intl API
  const intl = useIntl();
  // CurrentPage
  const [current, setCurrent] = useState<number>(DEFAULT_PAGE_NUMBER);
  // PageSize
  const [pageSize, setPageSize] = useState<number>(DEFAULT_PAGE_SIZE);
  // List of Resource Types
  const [resourceTypeList, setResourceTypeList] = useState([]);

  // Asset aggregation list data
  const {
    data: aggregateAssetsData,
    run: requestAggregateAssetsList,
    loading: aggregateAssetsListLoading,
  } = useRequest(
    (params: API.AssetInfo) => {
      return queryAggregateAssets(params);
    },
    {
      manual: true,
      formatResult: (r) => r.content,
    },
  );

  useEffect((): void => {
    requestAggregateAssetsList({
      page: current,
      size: pageSize,
    });
  }, []);

  // Reset Form
  const onClickToReset = (): void => {
    form.resetFields();
  };

  // Search Result
  const onClickToFinish = (formData: Record<string, any>): void => {
    setCurrent(DEFAULT_PAGE_NUMBER);
    setPageSize(DEFAULT_PAGE_SIZE);
    requestAggregateAssetsList({
      ...formData,
      page: DEFAULT_PAGE_NUMBER,
      size: DEFAULT_PAGE_SIZE,
    });
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

  // Cloud account list data
  const {
    data: baseCloudAccountList,
    run: requestCloudAccountBaseInfoList,
    loading: fetching,
  } = useRequest(
    (params: { cloudAccountSearch?: string; platformList?: string[] }) => {
      return cloudAccountBaseInfoList({ ...params });
    },
    {
      formatResult: (r) => {
        if (r.msg === 'success') {
          return (
            r?.content?.accountAliasList?.map((item: string) => ({
              label: item,
              value: item,
            })) || []
          );
        }
      },
    },
  );

  // Cloud account list filtering
  const debounceFetcher = useMemo(() => {
    const loadOptions = (fuzzy: string): void => {
      // if (isEmpty(fuzzy)) return;
      requestCloudAccountBaseInfoList({
        platformList: form.getFieldValue('platformList') || [],
        cloudAccountSearch: fuzzy,
      });
    };
    return debounce(loadOptions, 500);
  }, [cloudAccountBaseInfoList]);

  return (
    <PageContainer
      ghost
      title={false}
      className={styles['rulePageContainer']}
      breadcrumbRender={false}
    >
      <ProCard style={{ marginBottom: 16 }}>
        <Form form={form} onFinish={onClickToFinish}>
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
                    requestCloudAccountBaseInfoList({
                      platformList: (checkedValue as string[]) || [],
                    });
                  }}
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
                  placeholder={intl.formatMessage({
                    id: 'common.select.text.placeholder',
                  })}
                  options={resourceTypeList}
                  multiple
                  showCheckedStrategy={SHOW_CHILD}
                  allowClear
                  showSearch
                />
              </Form.Item>
            </Col>
            <Col span={6}>
              <Form.Item
                name="cloudAccountId"
                label={intl.formatMessage({
                  id: 'common.select.label.cloudAccount',
                })}
                style={{ marginBottom: 0, width: '100%' }}
              >
                <Select
                  allowClear
                  showSearch
                  placeholder={intl.formatMessage({
                    id: 'common.select.query.text.placeholder',
                  })}
                  notFoundContent={fetching && <Spin size="small" />}
                  onSearch={debounceFetcher}
                  options={baseCloudAccountList || []}
                />
              </Form.Item>
            </Col>
            <Col span={6}>
              <Form.Item
                name="searchParam"
                label={intl.formatMessage({
                  id: 'common.input.text.assetIdAndName',
                })}
                style={{ marginBottom: 0, width: '100%' }}
              >
                <Input
                  placeholder={intl.formatMessage({
                    id: 'common.input.text.placeholder',
                  })}
                  allowClear
                />
              </Form.Item>
            </Col>
            <Col span={6}>
              <Form.Item
                name="address"
                label={intl.formatMessage({
                  id: 'asset.module.input.text.ip',
                })}
                style={{ marginBottom: 0, width: '100%' }}
              >
                <Input
                  placeholder={intl.formatMessage({
                    id: 'common.input.text.placeholder',
                  })}
                  allowClear
                />
              </Form.Item>
            </Col>
            <Col span={6} push={18}>
              <Flex style={{ width: '100%' }} justify={'flex-end'}>
                <Form.Item style={{ marginBottom: 0 }}>
                  <Space size={8}>
                    <Button onClick={onClickToReset}>
                      {intl.formatMessage({
                        id: 'common.button.text.reset',
                      })}
                    </Button>
                    <Button
                      type={'primary'}
                      htmlType="submit"
                      loading={aggregateAssetsListLoading}
                    >
                      {intl.formatMessage({
                        id: 'common.button.text.query',
                      })}
                    </Button>
                  </Space>
                </Form.Item>
              </Flex>
            </Col>
          </Row>
        </Form>
      </ProCard>

      <ProCard
        style={{ minHeight: 488 }}
        title={
          <div className={styleType['customTitle']}>
            {intl.formatMessage({
              id: 'asset.module.text.asset.polymerize',
            })}
          </div>
        }
      >
        <Row className={styles['polymerizeWrap']}>
          <Spin spinning={aggregateAssetsListLoading}>
            {!isEmpty(aggregateAssetsData?.data) ? (
              <Row gutter={[16, 16]}>
                {aggregateAssetsData?.data?.map(
                  (
                    aggregateAsset: API.BaseAggregateAssetInfo,
                    index: number,
                  ) => (
                    <Col
                      span={
                        ['xxLProMax']?.includes(mediaSize)
                          ? 4
                          : ['xxLFullHD', 'xxLPro']?.includes(mediaSize)
                          ? 6
                          : ['xxL', 'xL']?.includes(mediaSize)
                          ? 8
                          : ['lg', 'md']?.includes(mediaSize)
                          ? 12
                          : 24
                      }
                      key={index}
                    >
                      <PolymerizeCard
                        key={index}
                        aggregateAsset={aggregateAsset}
                        cloudAccountId={form?.getFieldValue('cloudAccountId')}
                      />
                    </Col>
                  ),
                )}
              </Row>
            ) : (
              <Flex
                align={'center'}
                justify={'center'}
                style={{ width: ' 100%', minHeight: 356 }}
              >
                <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
              </Flex>
            )}
          </Spin>
        </Row>

        <Row>
          <Flex justify={'center'} style={{ width: '100%', marginTop: '16px' }}>
            <Pagination
              onChange={(current: number, pageSize: number): void => {
                setCurrent(current);
                setPageSize(pageSize);
                requestAggregateAssetsList({
                  ...form.getFieldsValue(),
                  page: current,
                  size: pageSize,
                });
              }}
              current={current}
              pageSize={pageSize}
              size={'small'}
              showTotal={(total: number, range: [number, number]): string =>
                showTotalIntlFunc(total, range, intl?.locale)
              }
              total={aggregateAssetsData?.total || 0}
              showSizeChanger={true}
              pageSizeOptions={[12, 24]}
            />
          </Flex>
        </Row>
      </ProCard>
    </PageContainer>
  );
};

export default AssetPolymerize;
