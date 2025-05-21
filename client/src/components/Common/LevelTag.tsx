import { Tag } from 'antd';
import React from 'react';
import styles from './index.less';

export default (props: {
  style?: React.CSSProperties;
  text?: string;
  level: 'HIGH' | 'MEDIUM' | 'LOW';
}) => {
  const { text, style, level } = props;
  return (
    <Tag
      className={
        styles[
          level === 'HIGH'
            ? 'riskHigh'
            : ['MEDIUM', 'MIDDLE']?.includes(level)
            ? 'riskMedium'
            : level === 'LOW'
            ? 'riskLow'
            : ''
        ]
      }
      style={{ ...style }}
    >
      {text}
    </Tag>
  );
};
