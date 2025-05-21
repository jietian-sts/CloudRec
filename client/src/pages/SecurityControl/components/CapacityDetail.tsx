import { JSONEditor } from '@/components/Editor';
import { useIntl } from '@umijs/max';
import { Drawer } from 'antd';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';

interface ICapacityDrawerProps {
  capacityDrawerInfo: API.BaseProductPosture;
  capacityDrawerVisible: boolean;
  setCapacityDrawerVisible: Dispatch<SetStateAction<boolean>>;
}

// Capacity Details
const CapacityDrawer: React.FC<ICapacityDrawerProps> = (props) => {
  // Component Props
  const {
    capacityDrawerInfo,
    capacityDrawerVisible,
    setCapacityDrawerVisible,
  } = props;
  // Intl API
  const intl = useIntl();
  // Strategy details
  const [capacityEditor, setCapacityEditor] = useState<string>(``);

  const onClickCloseDrawer = (): void => {
    setCapacityDrawerVisible(false);
  };

  useEffect((): void => {
    if (capacityDrawerVisible) {
      setCapacityEditor(
        JSON.stringify(JSON.parse(capacityDrawerInfo.policyDetail!), null, 4) ||
          '',
      );
    }
  }, [capacityDrawerVisible]);

  return (
    <Drawer
      title={intl.formatMessage({
        id: 'security.extend.title.security.detail',
      })}
      width={'40%'}
      destroyOnClose
      open={capacityDrawerVisible}
      onClose={onClickCloseDrawer}
    >
      <JSONEditor
        editorKey="CAPACITY_EDITOR"
        value={capacityEditor}
        readOnly={true}
        editorStyle={{ height: '100%' }}
      />
    </Drawer>
  );
};

export default CapacityDrawer;
