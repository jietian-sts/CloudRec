import { PLATFORM_THEME_FAILED, PLATFORM_THEME_SUCCESS } from '@/constants';
import { CheckCircleOutlined, CloseCircleOutlined } from '@ant-design/icons';
import { FormattedMessage } from '@umijs/max';
import { Space, Typography } from 'antd';

export default (props: { enable: boolean }) => {
  const { enable } = props;
  return (
    <>
      {enable ? (
        <Space size={4}>
          <CheckCircleOutlined style={{ color: PLATFORM_THEME_SUCCESS }} />
          <Typography.Text>
            <FormattedMessage id={'common.tag.text.enabled'} />
          </Typography.Text>
        </Space>
      ) : (
        <Space size={4}>
          <CloseCircleOutlined style={{ color: PLATFORM_THEME_FAILED }} />
          <Typography.Text>
            <FormattedMessage id={'common.tag.text.disabled'} />
          </Typography.Text>
        </Space>
      )}
    </>
  );
};
