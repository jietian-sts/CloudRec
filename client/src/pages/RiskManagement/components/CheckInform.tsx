import styleType from '@/components/Common/index.less';
import { JSONEditor } from '@/components/Editor';
import { ProCard } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { isEmpty } from 'lodash';
import React, { useEffect, useState } from 'react';
import styles from '../index.less';

interface ILogInformation {
  riskDrawerInfo: Record<string, any>;
}

// Testing situation
const CheckInform: React.FC<ILogInformation> = (props) => {
  // Component Props
  const { riskDrawerInfo } = props;
  // Intl API
  const intl = useIntl();

  const [evaluateEditor, setEvaluateEditor] = useState<string>(``);

  useEffect((): void => {
    const evaluateJSON = riskDrawerInfo?.result
      ? JSON.parse(riskDrawerInfo?.result)
      : {};
    setEvaluateEditor(JSON.stringify(evaluateJSON, null, 4) || '');
  }, [riskDrawerInfo]);

  return (
    <ProCard
      style={{ backgroundColor: 'transparent' }}
      className={styles['checkInform']}
      title={
        <div
          className={styleType['customTitle']}
          style={{ color: 'rgb(36, 36, 36)', fontWeight: 'normal' }}
        >
          {intl.formatMessage({
            id: 'risk.module.text.testing.situation',
          })}
        </div>
      }
      headStyle={{
        paddingInline: 0,
        paddingBlockStart: 0,
      }}
      bodyStyle={{
        paddingInline: 0,
      }}
    >
      {!isEmpty(evaluateEditor) && (
        <JSONEditor
          editorKey="evaluateEditor"
          value={evaluateEditor}
          readOnly={true}
          editorStyle={{ height: 280 }}
        />
      )}
    </ProCard>
  );
};

export default CheckInform;
