import { UserOutlined } from '@ant-design/icons';
import { Avatar } from 'antd';

interface ILoginAvatar {
  userId: number | null;
}

const LoginAvatar = (props: ILoginAvatar) => {
  const { userId } = props;
  return (
    <>
      {userId ? (
        <Avatar src={``} />
      ) : (
        <Avatar size={32} icon={<UserOutlined />} />
      )}
    </>
  );
};
export default LoginAvatar;
