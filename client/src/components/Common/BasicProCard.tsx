import { ProCard } from '@ant-design/pro-components';
import React, { ReactNode } from 'react';
import styles from './index.less';

export default (props: {
  children?: any;
  title?: string | ReactNode;
  style?: any;
  extra?: any;
  className?: string;
  bodyStyle?: React.CSSProperties;
  headerBordered?: boolean;
  loading?: boolean;
  cardNodeRef?: any;
}) => {
  const {
    children,
    title,
    style,
    extra,
    className,
    bodyStyle,
    loading,
    cardNodeRef,
    headerBordered,
  } = props;
  return (
    <ProCard
      headStyle={{ paddingInline: 16 }}
      ref={cardNodeRef}
      loading={loading}
      bodyStyle={bodyStyle}
      title={<div className={styles['customTitle']}>{title}</div>}
      headerBordered={headerBordered}
      style={style}
      extra={extra}
      className={className}
    >
      {children}
    </ProCard>
  );
};
