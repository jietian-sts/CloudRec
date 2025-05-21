import LoginAvatar from '@/components/Layout/LoginAvatar';
import LoginUser from '@/components/Layout/LoginUser';
import SwitchLanguage from '@/components/Layout/SwitchLanguage';
import SwitchTenant from '@/components/Layout/SwitchTenant';
import { FormattedMessage, history } from '@umijs/max';
import { Divider, Dropdown, Flex, MenuProps } from 'antd';

interface IUserAction {
  username: string | null;
  userId: number | null;
  tenantId?: number;
  tenantName: string;
  collapsed: boolean | undefined;
}

// Logout
export const onClickToLogout = (delay: number = 300): void => {
  localStorage.removeItem('JSESSIONID');
  const timer = setTimeout((): void => {
    window.location.reload();
    clearTimeout(timer);
  }, delay);
};

const UserAction = (props: IUserAction) => {
  const { username, userId, tenantName, tenantId } = props;

  const onClickToIndividual = (): void => {
    history.push('/individual');
  };

  const items: MenuProps['items'] = [
    {
      key: '1',
      label: (
        <a onClick={onClickToIndividual}>
          <FormattedMessage id={'layout.routes.title.personalCenter'} />
        </a>
      ),
    },
    {
      key: '2',
      label: (
        <a onClick={() => onClickToLogout()}>
          <FormattedMessage id={'user.extend.text.logout'} />
        </a>
      ),
    },
  ];

  return (
    <Flex align={'center'} justify={'center'} style={{ width: '100%' }}>
      <SwitchTenant tenantName={tenantName} tenantId={tenantId} />
      <Divider type={'vertical'} style={{ margin: '0 2px 0 2px' }} />
      <SwitchLanguage />
      <Divider type={'vertical'} style={{ margin: '0 14px 0 2px' }} />
      <Dropdown menu={{ items }}>
        <a onClick={(e): void => e.preventDefault()}>
          <LoginAvatar userId={userId} />
          <LoginUser username={username} />
        </a>
      </Dropdown>
    </Flex>
  );
};
export default UserAction;
