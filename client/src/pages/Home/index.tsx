import EditModalForm from '@/pages/Allocation/Individual/components/EditModalForm';
import Inform from '@/pages/Home/components/Inform';
import Kernel from '@/pages/Home/components/Kernel';
import Overview from '@/pages/Home/components/Overview';
import TrendChart from '@/pages/Home/components/TrendChart';
import { PageContainer } from '@ant-design/pro-components';
import { useAccess } from '@umijs/max';
import { Breakpoint, Col, Grid, Row } from 'antd';
import React, { useEffect, useState } from 'react';
import styles from './index.less';
const { useBreakpoint } = Grid;

const HomePage: React.FC = () => {
  // Ant Design Provide monitoring of screen width changes
  const breakpoints: Partial<Record<Breakpoint, boolean>> = useBreakpoint();
  // Global Access
  const access = useAccess();
  // User changes password Modal Visible
  const [editFormVisible, setEditFormVisible] = useState<boolean>(false);

  useEffect((): void => {
    // There is currently no latest login time available
    if (!access?.lastLoginTime) setEditFormVisible(true);
  }, []);

  return (
    <PageContainer
      ghost
      title={false}
      className={styles['homePage']}
      childrenContentStyle={
        breakpoints.xs ? { paddingBlockEnd: 16, paddingInline: 20 } : {}
      }
    >
      <Overview />
      <Row gutter={[16, 16]}>
        <Col span={breakpoints.xs ? 24 : 15}>
          <TrendChart />
          <Inform />
        </Col>
        <Col span={breakpoints.xs ? 24 : 9}>
          <Kernel />
        </Col>
      </Row>

      <EditModalForm // User changes password
        editFormVisible={editFormVisible}
        setEditFormVisible={setEditFormVisible}
      />
    </PageContainer>
  );
};

export default HomePage;
