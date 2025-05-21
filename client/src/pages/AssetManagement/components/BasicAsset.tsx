import { ProCard } from '@ant-design/pro-components';
import { Button, Empty, Form, Typography } from 'antd';
import { isEmpty } from 'lodash';
import React from 'react';
import { useAccess } from 'umi';
const { Text } = Typography;

interface IBasicAsset {
  assetInfo: API.BaseAssetResultInfo;
}

/**
 *
 * Configuration details
 * Delete Configuration
 * Note: Not yet used
 * */
const ExtraDom: React.FC<IBasicAsset> = (props) => {
  const access = useAccess();
  const { isAdmin } = access;
  const { assetInfo } = props;
  return (
    <div>
      {isAdmin ? (
        <Button
          type={'link'}
          size={'small'}
          href={`/assetManagement/asseConfig?id=${assetInfo?.id}`}
          target={'_blank'}
        >
          配置详情
        </Button>
      ) : (
        <></>
      )}
    </div>
  );
};

// Asset Information
const BasicAsset: React.FC<IBasicAsset> = (props) => {
  const { assetInfo } = props;

  return (
    <ProCard
      boxShadow
      extra={<ExtraDom assetInfo={assetInfo} />}
      bodyStyle={{ paddingBlockStart: 0 }}
    >
      {isEmpty(assetInfo?.resourceDetailConfigMap?.['BASE_INFO']) &&
      isEmpty(assetInfo?.resourceDetailConfigMap?.['NETWORK']) ? (
        <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
      ) : (
        <>
          <Text style={{ marginBottom: 6, display: 'block', color: '#377df7' }}>
            基本信息:
          </Text>
          {assetInfo?.resourceDetailConfigMap?.['BASE_INFO']?.map(
            (item: Record<string, any>, index: number) => {
              return (
                <Form.Item key={index} label={item?.name}>
                  {item.value}
                </Form.Item>
              );
            },
          )}
          <Text style={{ marginBottom: 6, display: 'block', color: '#377df7' }}>
            网络信息:
          </Text>
          {assetInfo?.resourceDetailConfigMap?.['NETWORK']?.map(
            (item: Record<string, any>, index: number) => {
              return (
                <Form.Item key={index} label={item?.name}>
                  {item.value}
                </Form.Item>
              );
            },
          )}
        </>
      )}
    </ProCard>
  );
};

export default BasicAsset;
