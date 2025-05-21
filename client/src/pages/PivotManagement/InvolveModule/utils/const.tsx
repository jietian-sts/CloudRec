import { FormattedMessage } from '@umijs/max';

export const CHECK_BOX_OPTIONS_LIST = [
  {
    label: <FormattedMessage id={'involve.extend.title.timing.notice'} />,
    value: 'timing',
    desc: <FormattedMessage id={'involve.extend.title.timing.notice.desc'} />,
  },
  {
    label: <FormattedMessage id={'involve.extend.title.realtime.notice'} />,
    value: 'realtime',
    desc: <FormattedMessage id={'involve.extend.title.realtime.notice.desc'} />,
  },
];

export const ACTION_TYPE_LIST_TIMING = [
  {
    label: <FormattedMessage id={'involve.extend.text.dingGroup'} />,
    value: 'dingGroup',
  },
  {
    label: <FormattedMessage id={'involve.extend.text.wechat'} />,
    value: 'wechat',
  },
];

export const ACTION_TYPE_LIST_REALTIME = [
  {
    label: <FormattedMessage id={'involve.extend.text.dingGroup'} />,
    value: 'dingGroup',
  },
  {
    label: <FormattedMessage id={'involve.extend.text.interfaceCallback'} />,
    value: 'interfaceCallback',
  },
  {
    label: <FormattedMessage id={'involve.extend.text.wechat'} />,
    value: 'wechat',
  },
];

export const PERIOD_LIST_TIMING = [
  {
    label: <FormattedMessage id={'involve.extend.text.day.monday'} />,
    value: '1',
  },
  {
    label: <FormattedMessage id={'involve.extend.text.day.tuesday'} />,
    value: '2',
  },
  {
    label: <FormattedMessage id={'involve.extend.text.day.wednesday'} />,
    value: '3',
  },
  {
    label: <FormattedMessage id={'involve.extend.text.day.thursday'} />,
    value: '4',
  },
  {
    label: <FormattedMessage id={'involve.extend.text.day.friday'} />,
    value: '5',
  },
  {
    label: <FormattedMessage id={'involve.extend.text.day.saturday'} />,
    value: '6',
  },
  {
    label: <FormattedMessage id={'involve.extend.text.day.sunday'} />,
    value: '7',
  },
  {
    label: <FormattedMessage id={'involve.extend.text.every.day'} />,
    value: 'all',
  },
];

export const TIME_LIST_TIMING = [
  { label: '10: 00', value: '10' },
  { label: '11: 00', value: '11' },
  { label: '12: 00', value: '12' },
  { label: '13: 00', value: '13' },
  { label: '14: 00', value: '14' },
  { label: '15: 00', value: '15' },
  { label: '16: 00', value: '16' },
  { label: '17: 00', value: '17' },
  { label: '18: 00', value: '18' },
  { label: '19: 00', value: '19' },
  { label: '20: 00', value: '20' },
  { label: '21: 00', value: '21' },
];
