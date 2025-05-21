import { JSONEditor } from '@/components/Editor';
import { queryResourceDetailById } from '@/services/asset/AssetController';
import { useIntl, useRequest } from '@umijs/max';
import { Drawer } from 'antd';
import { isEmpty } from 'lodash';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';

interface IAssetInform {
  assetInformVisible: boolean;
  setAssetInformVisible: Dispatch<SetStateAction<boolean>>;
  assetInfo: Record<string, any>;
}

// Asset Details
const AssetInform: React.FC<IAssetInform> = (props) => {
  // Component Props
  const { assetInformVisible, assetInfo, setAssetInformVisible } = props;
  // Resource Details
  const [assetConfigEditor, setAssetConfigEditor] = useState(``);
  // Intl API
  const intl = useIntl();

  const onClickCloseModal = (): void => {
    setAssetInformVisible(false);
  };

  // Asset detail data
  const { run: requestResourceDetailById, loading: assetDetailLoading }: any =
    useRequest(
      (id: string) => {
        return queryResourceDetailById({ id });
      },
      {
        manual: true,
        formatResult: (r: API.Result_AssetDetailInfo): void => {
          const { content } = r;
          const instanceJSON: string = JSON.stringify(
            content?.instance,
            null,
            4,
          );
          if (!isEmpty(instanceJSON)) {
            setAssetConfigEditor(instanceJSON);
          }
        },
      },
    );

  useEffect((): void => {
    if (assetInformVisible && assetInfo?.id) {
      requestResourceDetailById(Number(assetInfo.id));
    }
  }, [assetInformVisible, assetInfo]);

  return (
    <Drawer
      title={intl.formatMessage({
        id: 'asset.extend.text.detail',
      })}
      width={'46%'}
      open={assetInformVisible}
      onClose={onClickCloseModal}
      destroyOnClose
      loading={assetDetailLoading}
    >
      {assetConfigEditor && (
        <JSONEditor
          editorKey="ASSET_CONFIG_INSTANCE"
          value={assetConfigEditor}
          readOnly={true}
          editorStyle={{ height: '100%' }}
        />
      )}
    </Drawer>
  );
};

export default AssetInform;
