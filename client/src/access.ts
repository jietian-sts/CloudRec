export default (initialState: API.UserInfo) => {
  // Here, define the permissions in the project according to the initialization data and manage them uniformly
  // Reference Documents https://umijs.org/docs/max/access
  const isAdmin: boolean = initialState?.roleName === 'admin';
  const {
    username,
    userId,
    tenantName,
    roleName,
    gmtCreate,
    gmtModified,
    lastLoginTime,
  } = initialState;

  return {
    lastLoginTime,
    username,
    userId,
    tenantName,
    roleName,
    gmtCreate,
    gmtModified,
    isAdmin,
  };
};
