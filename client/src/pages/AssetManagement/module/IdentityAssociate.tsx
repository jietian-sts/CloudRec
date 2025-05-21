import { IDENTITY_ASSOCIATE_LIST } from '@/pages/AssetManagement/const';
import { ArrowLeftOutlined } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Button, Tabs } from 'antd';
import React, { useState } from 'react';

// Associate Identity
const IdentityAssociate: React.FC = () => {
  // Intl API
  const intl = useIntl();
  // Active Key
  const [activeKey, setActiveKey] = useState(IDENTITY_ASSOCIATE_LIST[0].key);

  return (
    <PageContainer
      ghost={true}
      breadcrumbRender={false}
      header={{
        ghost: true,
        style: {
          paddingBlockEnd: 0,
        },
      }}
      title={
        <Button
          style={{ padding: 0 }}
          type={'link'}
          size={'small'}
          onClick={() => history?.back()}
        >
          <ArrowLeftOutlined />
          {intl.formatMessage({
            id: 'common.button.text.return',
          })}
        </Button>
      }
    >
      <Tabs
        destroyInactiveTabPane
        onChange={setActiveKey}
        activeKey={activeKey}
        items={IDENTITY_ASSOCIATE_LIST.map((item) => {
          return {
            key: item.key,
            label: item.label,
            children: item.children,
            icon: item.icon,
          };
        })}
      />
    </PageContainer>
  );
};

export default IdentityAssociate;
