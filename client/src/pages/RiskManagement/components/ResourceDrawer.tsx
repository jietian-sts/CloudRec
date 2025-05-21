import { JSONEditor } from '@/components/Editor';
import { queryRiskDetailById } from '@/services/risk/RiskController';
import { useIntl, useRequest } from '@umijs/max';
import { Drawer } from 'antd';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';

interface IEvaluateDrawerProps {
  resourceDrawerVisible: boolean;
  setResourceDrawerVisible: Dispatch<SetStateAction<boolean>>;
  riskDrawerInfo: Record<string, any>;
}

// Asset Details
const ResourceDrawer: React.FC<IEvaluateDrawerProps> = (props) => {
  // Component Props
  const { resourceDrawerVisible, riskDrawerInfo, setResourceDrawerVisible } =
    props;
  // Intl API
  const intl = useIntl();

  const [resourceEditor, setResourceEditor] = useState(``);

  const initDrawer = (): void => {
    setResourceDrawerVisible(false);
    setResourceEditor(``);
  };

  // Asset detail data
  const { loading: riskInfoLoading, run: requestRiskDetailById }: any =
    useRequest(
      (id) => {
        return queryRiskDetailById({ riskId: id });
      },
      {
        manual: true,
        formatResult: (r: any) => {
          const { content } = r;
          const resourceJSON = content?.resourceInstance
            ? JSON.parse(content?.resourceInstance)
            : {};
          setResourceEditor(JSON.stringify(resourceJSON, null, 4) || '');
          return r.content || {};
        },
      },
    );

  const onClickCloseDrawerForm = (): void => {
    initDrawer();
  };

  useEffect((): void => {
    if (resourceDrawerVisible && riskDrawerInfo?.id) {
      requestRiskDetailById(riskDrawerInfo.id);
    }
  }, [resourceDrawerVisible, riskDrawerInfo]);

  return (
    <Drawer
      title={intl.formatMessage({
        id: 'asset.extend.text.detail',
      })}
      width={'40%'}
      destroyOnClose
      open={resourceDrawerVisible}
      onClose={onClickCloseDrawerForm}
      loading={riskInfoLoading}
    >
      <JSONEditor
        editorKey="resourceEditor"
        value={resourceEditor}
        readOnly={true}
        editorStyle={{ height: '100%' }}
      />
    </Drawer>
  );
};

export default ResourceDrawer;
