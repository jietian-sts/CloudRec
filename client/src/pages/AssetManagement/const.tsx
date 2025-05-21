import Account from '@/pages/AssetManagement/module/Account';
import Log from '@/pages/AssetManagement/module/Log';
import { default as Permission } from '@/pages/AssetManagement/module/Pemission';
import Risk from '@/pages/AssetManagement/module/Risk';
import {
  ClockCircleOutlined,
  SettingOutlined,
  UserOutlined,
  WarningOutlined,
} from '@ant-design/icons';
import { FormattedMessage } from '@umijs/max';
import { cloneDeep, isEmpty } from 'lodash';

export const serializeStatusMap = {
  false: 'invalid',
  true: 'valid',
};

export const AssetSortMethodMap = {
  ascend: 'ASC', // 1: Positive sequence (Ascending order)
  descend: 'DESC', // 2: Reverse order (Descending order)
};

export const serializeFormData = (
  formData: Record<string, any>,
  assetInfo: API.AssetInfo,
) => {
  const data = cloneDeep(formData);
  for (const key in data) {
    if (data.hasOwnProperty(key)) {
      const array = data[key];
      if (!isEmpty(array) && Array.isArray(array)) {
        array.forEach((item): void => {
          // @ts-ignore
          item['status'] = serializeStatusMap[item.status];
          item['platform'] = assetInfo.platform;
          item['resourceType'] = assetInfo.resourceType;
        });
      } else if (array === undefined) {
        data[key] = [];
      }
    }
  }
  return data;
};

export const unSerializeStatusMap = {
  invalid: false,
  valid: true,
};

export const unSerializeFormData = (formData: Record<string, any>) => {
  const data = cloneDeep(formData);
  for (const key in data) {
    if (data.hasOwnProperty(key)) {
      const array = data[key];
      if (!isEmpty(array) && Array.isArray(array)) {
        array.forEach((item): void => {
          // @ts-ignore
          item['status'] = unSerializeStatusMap[item.status];
        });
      }
    }
  }
  return data;
};

export const imageURLMap = {
  linkIcon:
    'https://mdn.alipayobjects.com/huamei_f8hv0x/afts/img/A*a8O4QrNtEvwAAAAAAAAAAAAADiiJAQ/original',
};

export const customTheme = {
  components: {
    Input: {
      colorBgContainerDisabled: '#FFF',
      colorTextDisabled: 'rgba(0, 0, 0, 0.88)',
      algorithm: true, // 启用算法
    },
  },
};

export const ACCESS_KEY_LIST = ['Access Key1', 'Access Key2'];

export const IDENTITY_ASSOCIATE_LIST = [
  {
    key: 'ACCOUNT',
    label: <FormattedMessage id={'asset.module.text.account.info'} />,
    icon: <UserOutlined />,
    children: <Account />,
  },
  {
    key: 'PERMISSION',
    label: <FormattedMessage id={'asset.module.text.permission'} />,
    icon: <SettingOutlined />,
    children: <Permission />,
  },
  {
    key: 'LOG',
    label: <FormattedMessage id={'asset.module.text.activity.log'} />,
    icon: <ClockCircleOutlined />,
    children: <Log />,
  },
  {
    key: 'RISK',
    label: <FormattedMessage id={'asset.module.text.risks'} />,
    icon: <WarningOutlined />,
    children: <Risk />,
  },
];
