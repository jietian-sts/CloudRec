import { ProCard } from '@ant-design/pro-components';
import { Empty } from 'antd';
import React from 'react';

// Log
const Log: React.FC = () => {
  return (
    <ProCard>
      <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
    </ProCard>
  );
};

export default Log;
