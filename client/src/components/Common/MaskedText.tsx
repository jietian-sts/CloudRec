import Paragraph from 'antd/lib/typography/Paragraph';
import React from 'react';

interface IMaskedText {
  text?: string;
  color?: string;
  link?: boolean;
  style?: React.CSSProperties;
  rows?: number;
}

const MaskedText: React.FC<IMaskedText> = (props) => {
  const { text, color, link, rows = 1, style } = props;

  return (
    <Paragraph
      ellipsis={{ rows }}
      style={{
        marginBottom: 0,
        color: color,
        cursor: link ? 'pointer' : '',
        ...style,
      }}
      copyable={{ text }}
    >
      *** ***
    </Paragraph>
  );
};

export default MaskedText;
