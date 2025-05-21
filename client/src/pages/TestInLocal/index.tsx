import G6Graph from '@/components/AntDV';
import { PageContainer } from '@ant-design/pro-components';
import React from 'react';

const HomePage: React.FC = () => {
  return (
    <PageContainer ghost title={false}>
      <G6Graph />
    </PageContainer>
  );
};

export default HomePage;
