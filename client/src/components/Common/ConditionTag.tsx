import FAIL from '@/assets/images/FAIL.svg';
import VALID from '@/assets/images/VALID.svg';
import WAITING from '@/assets/images/WAITING.svg';
import { Tag } from 'antd';
import styles from './index.less';

export default (props: {
  state?:
    | 'success'
    | 'valid'
    | 'invalid'
    | 'error'
    | 'failed'
    | 'waiting'
    | 'wait';
}) => {
  const { state } = props;

  let customTag = <Tag>{state || '-'}</Tag>;

  if (['success', 'valid'].includes(state!)) {
    customTag = (
      <Tag className={styles['validTag']}>
        <img src={VALID} alt="VALID_ICON" className={styles['imgResult']} />
        Valid
      </Tag>
    );
  } else if (['error', 'invalid', 'failed'].includes(state!)) {
    customTag = (
      <Tag className={styles['invalidTag']}>
        <img src={FAIL} alt="VALID_ICON" className={styles['imgResult']} />
        Invalid
      </Tag>
    );
  } else if (['waiting', 'wait'].includes(state!)) {
    customTag = (
      <Tag className={styles['waitingTag']}>
        <img
          src={WAITING}
          alt="WAITING_ICON"
          className={styles['imgProcess']}
        />
        Waiting
      </Tag>
    );
  }

  return customTag;
};
