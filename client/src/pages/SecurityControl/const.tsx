import { PLATFORM_THEME_SUCCESS, PLATFORM_THEME_WARN } from '@/constants';
import styles from '@/pages/SecurityControl/index.less';
import { FormattedMessage } from '@umijs/max';
import { Divider, Tooltip } from 'antd';

export enum STATISTIC_TYPE {
  TOTAL = 'CLOUD_ACCOUNT',
}

export const SECURITY_ABILITY_STATUS_LIST = [
  {
    label: <FormattedMessage id={'common.button.text.work'} />,
    value: 'open',
  },
  {
    label: <FormattedMessage id={'common.button.text.close'} />,
    value: 'close',
  },
];

export const DEFAULT_OVERALL_POSTURE_LIST = [
  {
    total: 0,
    open: 0,
    close: 0,
    type: 'CLOUD_ACCOUNT',
    name: <FormattedMessage id={'security.module.text.cloud.account.name'} />,
    title: <FormattedMessage id={'security.module.text.cloud.account.count'} />,
  },
  {
    total: null,
    open: 0,
    close: 0,
    type: 'DDoS',
    name: <FormattedMessage id={'security.module.text.ddos.name'} />,
    title: (
      <FormattedMessage id={'security.module.text.ddos.opening.situation'} />
    ),
  },
  {
    total: null,
    open: 0,
    close: 0,
    type: 'WAF',
    name: <FormattedMessage id={'security.module.text.waf.name'} />,
    title: (
      <FormattedMessage id={'security.module.text.waf.opening.situation'} />
    ),
  },
  {
    total: null,
    open: 0,
    close: 0,
    type: 'FIRE WALL',
    name: <FormattedMessage id={'security.module.text.firewall.name'} />,
    title: (
      <FormattedMessage
        id={'security.module.text.firewall.opening.situation'}
      />
    ),
  },
  {
    total: null,
    open: 0,
    close: 0,
    type: 'SAS',
    name: <FormattedMessage id={'security.module.text.sas.name'} />,
    title: (
      <FormattedMessage id={'security.module.text.sas.opening.situation'} />
    ),
  },
];

export const statisticFormatter = (value: any) => {
  if (value.type === STATISTIC_TYPE.TOTAL) {
    return <div style={{ fontWeight: '500' }}>{value.total}</div>;
  } else {
    return (
      <div>
        <Tooltip title={<FormattedMessage id={'common.tag.text.opened'} />}>
          <span style={{ color: PLATFORM_THEME_SUCCESS }}>{value.open}</span>
        </Tooltip>
        <Divider type={'vertical'} className={styles['divider']} />
        <Tooltip title={<FormattedMessage id={'common.tag.text.unopened'} />}>
          <span style={{ color: PLATFORM_THEME_WARN }}>{value.close}</span>
        </Tooltip>
      </div>
    );
  }
};

export const obtainOverallPostureName = (productType: string) => {
  return (
    DEFAULT_OVERALL_POSTURE_LIST.find((item) => item.type === productType)
      ?.name || '-'
  );
};

export const obtainFilterVisible = (
  valueList: Array<any>,
  productType: string,
) => {
  return !valueList.find((item) => item.type === productType);
};
