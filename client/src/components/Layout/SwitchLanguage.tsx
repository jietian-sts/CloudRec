import { TranslationOutlined } from '@ant-design/icons';
import { useIntl } from '@umijs/max';
import { Button } from 'antd';
import React from 'react';
import { setLocale } from 'umi';
import styles from './index.less';

export const LanguageList = [
  {
    label: '简体中文',
    value: 'zh-CN',
  },
  {
    label: 'English',
    value: 'en-US',
  },
];

// Switch Language
const SwitchLanguage: React.FC = () => {
  // Intl API
  const intl = useIntl();

  const onClickChangeLanguage = (): void => {
    // Expected language switching
    const expectLanguage =
      intl.locale === LanguageList[0].value
        ? LanguageList[1].value
        : LanguageList[0].value;
    setLocale(expectLanguage, true);
  };

  return (
    <Button
      className={styles['currentLanguage']}
      type={'link'}
      icon={<TranslationOutlined />}
      onClick={onClickChangeLanguage}
    >
      {intl.locale === LanguageList[0].value
        ? LanguageList[1].label
        : LanguageList[0].label}
    </Button>
  );
};
export default SwitchLanguage;
