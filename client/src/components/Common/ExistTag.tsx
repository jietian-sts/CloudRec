import { AssetStatusList } from '@/pages/RiskManagement/const';
import { FormattedMessage } from '@umijs/max';
import { Tag } from 'antd';
import styles from './index.less';

export default (props: { status?: 'exist' | 'not_exist' }) => {
  const { status } = props;

  let customTag = <Tag>{status || '-'}</Tag>;

  if ([AssetStatusList[1].value].includes(status!)) {
    // No Exist
    customTag = (
      <Tag className={styles['invalidTag']} style={{ marginLeft: 6 }}>
        <FormattedMessage id="common.tag.text.noExist" />
      </Tag>
    );
  } else {
    customTag = (
      <Tag className={styles['validTag']} style={{ marginLeft: 6 }}>
        <FormattedMessage id="common.tag.text.exist" />
      </Tag>
    );
  }

  return customTag;
};
