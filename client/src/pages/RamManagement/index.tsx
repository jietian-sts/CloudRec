import { Link } from '@umijs/max';
import { Button, Result } from 'antd';

const ComingSoonPage = () => {
  return (
    <Result
      status="404"
      title="敬请期待!"
      subTitle="⛏️ing..."
      extra={
        <Link to="/">
          {/* Use Link component to package jump button */}
          <Button type="primary">返回首页</Button>
          {/* Click the button to jump to the homepage */}
        </Link>
      }
    />
  );
};

export default ComingSoonPage;
