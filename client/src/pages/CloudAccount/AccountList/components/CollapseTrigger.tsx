import { LeftCircleFilled, RightCircleFilled } from '@ant-design/icons';
import React from 'react';

interface ICollapseTrigger {
  collapsed: boolean;
}

const iconType: React.CSSProperties = {
  fontSize: 22,
  fontWeight: 'bold',
  color: '#377df7',
};

// Cloud Account - Cloud Account List
const CollapseTrigger: React.FC<ICollapseTrigger> = (props) => {
  const { collapsed } = props;

  return (
    <>
      {collapsed ? (
        <RightCircleFilled style={iconType} />
      ) : (
        <LeftCircleFilled style={iconType} />
      )}
    </>
  );
};

export default CollapseTrigger;
