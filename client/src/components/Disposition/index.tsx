import { Tooltip, Typography } from 'antd';
import { TooltipPlacement } from 'antd/lib/tooltip';
import React from 'react';
const { Paragraph } = Typography;

interface IDisposition {
  rows?: number;
  text?: string | number;
  width?: number | string;
  maxWidth?: number;
  placement?: TooltipPlacement | undefined; // Tip prompt location
  color?: string;
  link?: boolean;
  onClickCallBackFunc?: (event: any) => void;
  style?: React.CSSProperties;
  copyable?: boolean;
}

export default ({
  text,
  width,
  maxWidth,
  rows = 2,
  placement = 'top',
  color = 'rgba(0, 0, 0, 0.88)',
  link = false,
  onClickCallBackFunc,
  style = {},
  copyable = false,
}: IDisposition) => {
  return (
    <div
      style={{
        maxWidth: maxWidth,
        width: width,
      }}
    >
      <Tooltip title={<div>{text}</div>} placement={placement}>
        <Paragraph
          ellipsis={{ rows }}
          style={{
            marginBottom: 0,
            color: color,
            cursor: link ? 'pointer' : '',
            ...style,
          }}
          onClick={onClickCallBackFunc}
          copyable={copyable}
        >
          {text}
        </Paragraph>
      </Tooltip>
    </div>
  );
};
