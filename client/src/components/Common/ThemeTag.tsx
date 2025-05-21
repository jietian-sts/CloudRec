import { Tag } from 'antd';
import React from 'react';

export default (props: { style?: React.CSSProperties; text?: string }) => {
  const { text, style } = props;
  return (
    <Tag
      style={{
        background: '#ebf0ff',
        border: '1px solid #a6bfff',
        borderRadius: 2,
        color: '#457aff',
        ...style,
      }}
    >
      {text}
    </Tag>
  );
};
