import { JSONEditor } from '@/components/Editor';
import { useIntl } from '@umijs/max';
import { Drawer } from 'antd';
import { isEmpty } from 'lodash';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';

interface IAssetInstanceProps {
  assetInstanceVisible: boolean;
  setAssetInstanceVisible: Dispatch<SetStateAction<boolean>>;
  assetInfo: Record<string, any>;
}

// Asset Details JSON
const AssetInstance: React.FC<IAssetInstanceProps> = (props) => {
  const { assetInstanceVisible, assetInfo, setAssetInstanceVisible } = props;
  // Asset Details
  const [assetEditor, setAssetEditor] = useState(``);
  // Intl API
  const intl = useIntl();

  const initDrawer = (): void => {
    setAssetInstanceVisible(false);
    setAssetEditor(``);
  };

  const onClickCloseDrawerForm = (): void => {
    initDrawer();
  };

  useEffect((): void => {
    if (assetInstanceVisible && !isEmpty(assetInfo)) {
      const instanceJSON: string = JSON.stringify(assetInfo?.instance, null, 4);
      if (!isEmpty(instanceJSON)) {
        setAssetEditor(instanceJSON);
      }
    }
  }, [assetInstanceVisible]);

  return (
    <Drawer
      title={intl.formatMessage({
        id: 'asset.extend.text.detail',
      })}
      width={'42%'}
      destroyOnClose
      open={assetInstanceVisible}
      onClose={onClickCloseDrawerForm}
    >
      {!isEmpty(assetEditor) && (
        <JSONEditor
          editorKey="assetInstance"
          value={assetEditor}
          readOnly={true}
          editorStyle={{ height: '100%' }}
        />
      )}
    </Drawer>
  );
};

export default AssetInstance;
