import styles from './index.less';

interface ILoginUser {
  username: string | null;
}
const LoginUser = (props: ILoginUser) => {
  const { username } = props;
  return <span className={styles['username']}>{username || '-'}</span>;
};
export default LoginUser;
