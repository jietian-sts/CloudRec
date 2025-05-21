import Disposition from '@/components/Disposition';
import CapacityDetail from '@/pages/SecurityControl/components/CapacityDetail';
import {
  obtainOverallPostureName,
  SECURITY_ABILITY_STATUS_LIST,
} from '@/pages/SecurityControl/const';
import { ActionType } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Button, Card, Col, Flex, Switch } from 'antd';
import { MutableRefObject, useRef, useState } from 'react';
import styles from '../index.less';

interface ICapacityCard {
  tableActionRef: MutableRefObject<ActionType | undefined>;
  record: API.BaseProductPosture;
}

const CapacityCard = (props: ICapacityCard) => {
  // Component Props
  const { record } = props;
  // Intl API
  const intl = useIntl();
  // Strategy details visible
  const [capacityDrawerVisible, setCapacityDrawerVisible] =
    useState<boolean>(false);
  // Capacity details
  const capacityDrawerInfo = useRef<API.BaseProductPosture>();

  // Strategy details
  const onClickViewCapacityDetail = () => {
    setCapacityDrawerVisible(true);
    capacityDrawerInfo.current = {
      policyDetail: record?.policyDetail,
    };
  };

  return (
    <Col span={12}>
      <Card
        className={styles['capacityCard']}
        styles={{
          body: {
            padding: '12px 20px',
          },
        }}
      >
        <Flex style={{ width: '100%' }}>
          <div className={styles['capacityCardLeft']}>
            <div className={styles['capacityCardLeftTitle']}>
              {intl.formatMessage({
                id: 'security.module.text.safety.ability',
              })}
            </div>
            <div className={styles['capacityCardLeftType']}>
              {obtainOverallPostureName(record.productType!) || '-'}
            </div>
            <Switch
              disabled={true}
              checkedChildren={intl.formatMessage({
                id: 'common.button.text.work',
              })}
              unCheckedChildren={intl.formatMessage({
                id: 'common.button.text.stop',
              })}
              checked={record?.status === SECURITY_ABILITY_STATUS_LIST[0].value}
            />
          </div>
          <div className={styles['capacityCardRight']}>
            <div className={styles['capacityCardRightContent']}>
              <Flex>
                <span className={styles['capacityCardRightLabel']}>
                  {intl.formatMessage({
                    id: 'security.module.text.safety.version',
                  })}
                  &nbsp;:&nbsp;&nbsp;
                </span>
                <Disposition
                  text={record.version || '-'}
                  maxWidth={260}
                  rows={1}
                  color={'#3D3D3D'}
                />
              </Flex>
              <Flex style={{ margin: '2px 0' }}>
                <span className={styles['capacityCardRightLabel']}>
                  {intl.formatMessage({
                    id: 'security.module.text.safety.strategy',
                  })}
                  &nbsp;:&nbsp;&nbsp;
                </span>
                <Disposition
                  text={record?.policy || '-'}
                  maxWidth={260}
                  rows={1}
                  color={'#3D3D3D'}
                />
              </Flex>
              <div>
                <span className={styles['capacityCardRightLabel']}>
                  {intl.formatMessage({
                    id: 'security.module.text.safety.resource',
                  })}
                  &nbsp;:&nbsp;&nbsp;
                </span>
                <span className={styles['capacityCardRightValue']}>
                  <span className={styles['range']}>
                    {record?.protectedCount}
                  </span>
                  <span className={styles['separator']}>/</span>
                  <span className={styles['altogether']}>{record?.total}</span>
                </span>
              </div>
            </div>
            <Button
              type={'link'}
              style={{ padding: 0 }}
              onClick={(): void => onClickViewCapacityDetail()}
            >
              {intl.formatMessage({
                id: 'security.extend.title.security.detail',
              })}
            </Button>
          </div>
        </Flex>
      </Card>
      <CapacityDetail
        capacityDrawerInfo={capacityDrawerInfo.current!}
        capacityDrawerVisible={capacityDrawerVisible}
        setCapacityDrawerVisible={setCapacityDrawerVisible}
      />
    </Col>
  );
};
export default CapacityCard;
