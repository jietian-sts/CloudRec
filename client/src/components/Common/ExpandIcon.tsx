import { DownCircleOutlined, RightCircleOutlined } from '@ant-design/icons';

// @ts-ignore
export default ({ expanded, onExpand, record }) =>
  expanded ? (
    <DownCircleOutlined
      style={{ color: '#457aff', fontSize: 14 }}
      onClick={(e) => onExpand(record, e)}
    />
  ) : (
    <RightCircleOutlined
      style={{ color: '#457aff', fontSize: 14 }}
      onClick={(e) => onExpand(record, e)}
    />
  );
