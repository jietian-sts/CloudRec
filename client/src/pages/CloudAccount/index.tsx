import { PageContainer } from '@ant-design/pro-components';
import React from 'react';
import AccountList from './AccountList';

// Cloud account
const CloudAccount: React.FC = () => {
  return (
    <PageContainer title={false}>
      <AccountList />
    </PageContainer>
  );
};

export default CloudAccount;
