import { queryResourceDetailById } from '@/services/asset/AssetController';
import { IValueType } from '@/utils/const';
import { obtainPlatformIcon } from '@/utils/shared';
import { ProfileOutlined } from '@ant-design/icons';
import { ActionType, ProCard } from '@ant-design/pro-components';
import { useModel, useRequest } from '@umijs/max';
import { Drawer, Flex, Tabs, TabsProps, Tooltip, Typography } from 'antd';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import styles from '../index.less';
import AssetInstance from './AssetInstance';
import AssociateRisk from './AssociateRisk';
import BasicAsset from './BasicAsset';
const { Text } = Typography;

interface IAssetDrawerProps {
  assetDrawerVisible: boolean;
  setAssetDrawerVisible: Dispatch<SetStateAction<boolean>>;
  assetDrawerInfo: Record<string, any>;
  tableActionRef?: React.RefObject<ActionType | undefined>;
}

// Asset Details
const AssetDrawer: React.FC<IAssetDrawerProps> = (props) => {
  const { assetDrawerVisible, assetDrawerInfo, setAssetDrawerVisible } = props;
  const { platformList } = useModel('rule');
  const { assetList } = useModel('asset');
  // Asset Details JSON
  const [assetInstanceVisible, setAssetInstanceVisible] = useState(false);

  // Asset detail data
  const {
    data: assetInfo,
    run: requestResourceDetailById,
    loading: assetDetailLoading,
  }: any = useRequest(
    (id: string) => {
      return queryResourceDetailById({ id });
    },
    {
      manual: true,
      formatResult: (r: any) => {
        return r.content || {};
      },
    },
  );

  const [activeTabKey, setActiveTabKey] = useState('BasicAsset');

  const tabItems: TabsProps['items'] = [
    {
      key: 'BasicAsset',
      label: '资产信息',
      children: <BasicAsset assetInfo={assetInfo} />,
    },
    {
      key: 'AssociateRisk',
      label: '风险信息',
      children: <AssociateRisk assetInfo={assetInfo} />,
    },
  ];

  const onTabChange = (key: string): void => {
    setActiveTabKey(key);
  };

  const initDrawer = (): void => {
    setAssetDrawerVisible(false);
    setActiveTabKey('BasicAsset');
  };

  const onClickCloseDrawerForm = (): void => {
    initDrawer();
  };

  useEffect((): void => {
    if (assetDrawerVisible && assetDrawerInfo?.id) {
      requestResourceDetailById(assetDrawerInfo.id);
    }
  }, [assetDrawerVisible, assetDrawerInfo]);

  return (
    <Drawer
      title={'资产详情'}
      width={'50%'}
      open={assetDrawerVisible}
      onClose={onClickCloseDrawerForm}
      loading={assetDetailLoading}
    >
      <ProCard
        boxShadow
        style={{ marginBottom: 16 }}
        bodyStyle={{ paddingBlock: 6 }}
      >
        <Text className={styles['customText']}>
          资源名称: {assetInfo?.resourceName || '-'}
        </Text>
        <Text className={styles['customText']}>
          资源id: {assetInfo?.resourceId || '-'}
          <Tooltip title={'资源详情'}>
            <span
              className={styles['iconWrap']}
              onClick={() => setAssetInstanceVisible(true)}
            >
              <ProfileOutlined className={styles['resourceInstance']} />
            </span>
          </Tooltip>
        </Text>
        <Text className={styles['customText']}>
          <Flex>
            云服务:
            <span style={{ margin: '0 12px 0 6px' }}>
              {obtainPlatformIcon(assetInfo?.platform, platformList)}
            </span>
            <span>{assetInfo?.platform}</span>
          </Flex>
        </Text>
        <Text className={styles['customText']}>
          资源类型:
          <span style={{ color: '#0958d9', marginLeft: '4px' }}>
            {assetList?.find(
              (item: IValueType) => item.value === assetInfo?.resourceType,
            )?.label || '-'}
          </span>
        </Text>
      </ProCard>

      <Tabs // Asset information ｜ Risk information
        activeKey={activeTabKey}
        items={tabItems}
        onChange={onTabChange}
        destroyInactiveTabPane
      />

      <AssetInstance // Resource Details
        assetInstanceVisible={assetInstanceVisible}
        setAssetInstanceVisible={setAssetInstanceVisible}
        assetInfo={assetInfo}
      />
    </Drawer>
  );
};

export default AssetDrawer;
