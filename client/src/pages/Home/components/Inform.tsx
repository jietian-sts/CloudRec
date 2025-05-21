import BasicProCard from '@/components/Common/BasicProCard';
import Disposition from '@/components/Disposition';
import { queryTopRiskList } from '@/services/home/homeController';
import { RiskLevelList } from '@/utils/const';
import { obtainPlatformEasyIcon, obtainRiskLevel } from '@/utils/shared';
import { FullscreenExitOutlined, FullscreenOutlined } from '@ant-design/icons';
import { history, useIntl, useModel, useRequest } from '@umijs/max';
import { Breakpoint, Button, Col, Grid, Row, Table, TableProps } from 'antd';
import React, { useEffect } from 'react';
import { FullScreen, useFullScreenHandle } from 'react-full-screen';
import styles from '../index.less';
const { useBreakpoint } = Grid;

interface ITableCard {
  handle?: any;
}

const TableCard: React.FC<ITableCard> = (props) => {
  // Platform Rule Group List
  const { platformList } = useModel('rule');
  // Ant Design Provide monitoring of screen width changes
  const breakpoints: Partial<Record<Breakpoint, boolean>> = useBreakpoint();
  // Component Props
  const { handle } = props;
  // Intl API
  const intl = useIntl();

  // Click to switch to full screen mode
  const onClickFullScreen = (): void => {
    // In full screen mode
    if (handle?.active) {
      handle?.exit();
    } else {
      handle?.enter();
    }
  };

  // Obtain high-risk risk data
  const {
    data: TopRiskList,
    run: requestTopRiskList,
    loading: topRiskListLoading,
  } = useRequest(
    () => {
      return queryTopRiskList({});
    },
    {
      manual: true,
      formatResult: (r: API.Result_RiskRecordInfo) => {
        return r.content;
      },
    },
  );

  useEffect((): void => {
    requestTopRiskList();
  }, []);

  const onClickRuleToLink = (ruleCode: string, platform: string) => {
    history.push(
      `/ruleManagement/ruleProject?platform=${platform}&ruleCode=${ruleCode}`,
    );
  };

  const columns: TableProps<API.BaseRiskRecordInfo>['columns'] = [
    {
      title: intl.formatMessage({
        id: 'home.module.inform.columns.ruleName',
      }),
      dataIndex: 'ruleName',
      align: 'left',
      width: 300,
      render: (_: any, record: API.BaseRiskRecordInfo) => {
        return (
          <Disposition
            onClickCallBackFunc={(): void =>
              onClickRuleToLink(record.ruleCode!, record?.platform)
            }
            link={true}
            color={'rgba(69, 122, 255, 1)'}
            placement={'topLeft'}
            text={record.ruleName || '-'}
            maxWidth={breakpoints?.xxl ? 300 : 280}
            rows={1}
          />
        );
      },
    },
    {
      title: intl.formatMessage({
        id: 'home.module.inform.columns.ruleTypeName',
      }),
      dataIndex: 'ruleTypeNameList',
      align: 'left',
      width: 165,
      render: (_: any, record: API.BaseRiskRecordInfo) => {
        return (
          <Disposition
            placement={'topLeft'}
            color={'#333'}
            text={record.ruleTypeNameList.toString() || '-'}
            maxWidth={breakpoints?.xxl ? 165 : 120}
            rows={1}
          />
        );
      },
    },
    {
      title: intl.formatMessage({
        id: 'home.module.inform.columns.platform',
      }),
      dataIndex: 'platform',
      align: 'center',
      width: 80,
      render: (_, record: API.BaseRiskRecordInfo) => {
        return obtainPlatformEasyIcon(record.platform!, platformList);
      },
    },
    {
      title: intl.formatMessage({
        id: 'home.module.inform.columns.riskLevel',
      }),
      dataIndex: 'riskLevel',
      align: 'center',
      width: 155,
      render: (_: any, record: API.BaseRiskRecordInfo) => {
        return obtainRiskLevel(RiskLevelList, record.riskLevel);
      },
    },
    {
      title: intl.formatMessage({
        id: 'home.module.inform.columns.riskCount',
      }),
      dataIndex: 'riskCount',
      align: 'center',
      width: 176,
      render: (_: any, record: API.BaseRiskRecordInfo) => {
        return (
          <Button
            style={{ padding: 0, color: 'rgba(69,122,255,1)' }}
            size={'small'}
            type={'link'}
            href={`/riskManagement/riskList?platform=${record?.platform}&ruleCode=${record?.ruleCode}`}
            rel={'prefetch'}
          >
            {record?.count}
          </Button>
        );
      },
    },
  ];

  return (
    <BasicProCard
      bodyStyle={{ padding: '16px 16px 6px 16px', minHeight: 475 }}
      title={`${intl.formatMessage({
        id: 'home.module.inform.todo.Top10',
      })}`}
      extra={
        breakpoints?.md && (
          <>
            {handle?.active ? (
              <FullscreenExitOutlined onClick={() => onClickFullScreen()} />
            ) : (
              <FullscreenOutlined onClick={() => onClickFullScreen()} />
            )}
          </>
        )
      }
    >
      <Table
        scroll={{ x: true }}
        size={'small'}
        rowKey={'ruleId'}
        columns={columns}
        className={styles['informTable']}
        loading={topRiskListLoading}
        dataSource={TopRiskList}
        pagination={false}
      />
    </BasicProCard>
  );
};

// Data and chart related content
const Inform: React.FC = () => {
  // Ant Design Provide monitoring of screen width changes
  const breakpoints: Partial<Record<Breakpoint, boolean>> = useBreakpoint();
  const handle = useFullScreenHandle();

  return (
    <Row gutter={[16, 16]} className={styles['inform']}>
      {breakpoints.xs ? (
        <TableCard /> // Mobile terminal
      ) : (
        <Col span={24} className={styles['informCol']}>
          <FullScreen handle={handle} className={styles['fullScreenWrap']}>
            <TableCard handle={handle} />
          </FullScreen>
        </Col>
      )}
    </Row>
  );
};

export default Inform;
