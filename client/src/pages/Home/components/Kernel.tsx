import AkSk from '@/pages/Home/components/AkSk';
import Carousel from '@/pages/Home/components/Banner';
import Risk from '@/pages/Home/components/Risk';
import { Col, Row } from 'antd';
import React from 'react';
import styles from '../index.less';

// Main content of the page
const Kernel: React.FC = () => {
  return (
    <Row gutter={[16, 16]} className={styles['kernel']}>
      <Col span={24} className={styles['kernelCol']}>
        <Risk />
      </Col>
      <Col span={24} className={styles['kernelCol']}>
        <Carousel />
      </Col>
      <Col span={24} className={styles['kernelCol']}>
        <AkSk />
      </Col>
    </Row>
  );
};

export default Kernel;
