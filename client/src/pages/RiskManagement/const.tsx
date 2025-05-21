import IGNORED from '@/assets/images/IGNORED.png';
import REPAIRED from '@/assets/images/REPAIRED.png';
import UNREPAIRED from '@/assets/images/UNREPAIRED.png';
import WHITED from '@/assets/images/WHITED.png';
import { IValueType } from '@/utils/const';
import { FormattedMessage } from '@umijs/max';

// Ignore type
export const IgnoreReasonTypeList: Array<IValueType> = [
  {
    label: <FormattedMessage id={'risk.module.text.false.alarm'} />,
    value: 'MISREPORT',
  },
  {
    label: <FormattedMessage id={'risk.module.text.exception'} />,
    value: 'EXCEPTION',
  },
  {
    label: <FormattedMessage id={'risk.module.text.ignore'} />,
    value: 'IGNORE',
  },
];

// Risk status
export const RiskStatusList: Array<IValueType> = [
  {
    label: <FormattedMessage id={'risk.module.text.not.fixed'} />,
    value: 'UNREPAIRED',
    color: 'rgb(252, 176, 73)',
    background: '#FFEDEA',
    icon: UNREPAIRED,
  },
  {
    label: <FormattedMessage id={'risk.module.text.fixed'} />,
    value: 'REPAIRED',
    color: 'rgb(17, 133, 86)',
    background: '#DFF5EF',
    icon: REPAIRED,
  },
  {
    label: <FormattedMessage id={'risk.module.text.ignored'} />,
    value: 'IGNORED',
    color: '#45AEFF',
    background: 'rgba(230,243,254,1)',
    icon: IGNORED,
  },
  {
    label: <FormattedMessage id={'risk.module.text.whited'} />,
    value: 'WHITED',
    color: '#6A1B9A',
    background: '#F3E5F5',
    icon: WHITED,
  },
];

export const AssetStatusList: Array<IValueType> = [
  {
    label: <FormattedMessage id={'common.tag.text.exist'} />,
    value: 'exist',
  },
  {
    label: <FormattedMessage id={'common.tag.text.noExist'} />,
    value: 'not_exist',
  },
];
