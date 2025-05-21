import { JSONEditor } from '@/components/Editor';
import { useIntl } from '@umijs/max';
import { Drawer } from 'antd';
import { isEmpty } from 'lodash';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';

interface IWhiteListInstanceProps {
  whiteListInstanceVisible: boolean;
  setWhiteListInstanceVisible: Dispatch<SetStateAction<boolean>>;
  whiteListInstanceInfo: Record<string, any>;
}

// White List Details JSON
const WhiteListInstance: React.FC<IWhiteListInstanceProps> = (props) => {
  const {
    whiteListInstanceVisible,
    whiteListInstanceInfo,
    setWhiteListInstanceVisible,
  } = props;
  // White List Details
  const [outputEditor, setOutputEditor] = useState(``);
  // Intl API
  const intl = useIntl();

  const initDrawer = (): void => {
    setWhiteListInstanceVisible(false);
    setOutputEditor(``);
  };

  const onClickCloseDrawerForm = (): void => {
    initDrawer();
  };

  useEffect((): void => {
    if (whiteListInstanceVisible && !isEmpty(whiteListInstanceInfo)) {
      const instanceJSON: string = JSON.stringify(
        whiteListInstanceInfo?.outputEditor,
        null,
        4,
      );
      if (!isEmpty(instanceJSON)) {
        setOutputEditor(instanceJSON);
      }
    }
  }, [whiteListInstanceVisible]);

  return (
    <Drawer
      title={intl.formatMessage({
        id: 'common.message.text.execute.detail',
      })}
      width={'42%'}
      destroyOnClose
      open={whiteListInstanceVisible}
      onClose={onClickCloseDrawerForm}
    >
      {!isEmpty(outputEditor) && (
        <JSONEditor
          editorKey="assetInstance"
          value={outputEditor}
          readOnly={true}
          editorStyle={{ height: '100%' }}
        />
      )}
    </Drawer>
  );
};

export default WhiteListInstance;
