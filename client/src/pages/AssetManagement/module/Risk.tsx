import { JSONEditor } from '@/components/Editor';
import { queryRiskInfo } from '@/services/asset/AssetController';
import { ProCard } from '@ant-design/pro-components';
import { FormattedMessage, useLocation, useRequest } from '@umijs/max';
import { Collapse } from 'antd';
import React, { useEffect, useState } from 'react';

// Risk
const Risk: React.FC = () => {
  // Get query parameters
  const location = useLocation();
  const queryParameters: URLSearchParams = new URLSearchParams(location.search);
  const id = queryParameters.get('id');
  // Active key map
  const [activeKeyMap, setActiveKeyMap] = useState<any>({});

  // Query risk card list
  const {
    run: requestIdentityRiskList,
    data: identityRiskList,
    loading: identityRiskListLoading,
  } = useRequest((id: number) => queryRiskInfo({ id: id }), {
    manual: true,
    formatResult: (r) => r?.content,
  });

  useEffect((): void => {
    if (id) requestIdentityRiskList(Number(id));
  }, [id]);

  return (
    <ProCard
      loading={identityRiskListLoading}
      title={<FormattedMessage id={'asset.module.text.risk.information'} />}
    >
      {identityRiskList?.map((item, index) => (
        <Collapse
          style={{ marginBottom: 12 }}
          key={index}
          expandIconPosition={'end'}
          collapsible="header"
          activeKey={activeKeyMap?.[index]}
          onChange={(keyList: string[]): void => {
            setActiveKeyMap({
              ...activeKeyMap,
              index: keyList,
            });
          }}
          items={[
            {
              key: '1',
              label: (
                <div>
                  <div>{item?.ruleName || '-'}</div>
                  <div>{item?.ruleDesc || '-'}</div>
                </div>
              ),
              children: (
                <>
                  {item.context && (
                    <JSONEditor
                      editorKey="IDENTITY_CONFIG_INSTANCE"
                      value={
                        JSON.stringify(JSON.parse(item.context), null, 4) || ''
                      }
                      readOnly={true}
                      editorStyle={{ height: 240 }}
                    />
                  )}
                </>
              ),
            },
          ]}
        />
      ))}
    </ProCard>
  );
};

export default Risk;
