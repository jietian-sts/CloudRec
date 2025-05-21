import { querySecurityPlatformList } from '@/services/security/SecurityController';
import { useRequest } from '@umijs/max';

export default () => {
  // List of cloud platforms that support security control
  const { data: securityPlatformList } = useRequest(
    () => {
      return querySecurityPlatformList({});
    },
    {
      formatResult: (r: any) => {
        return (
          r?.content?.map(
            (item: { platformName?: string; platform?: string }) => ({
              label: item?.platformName,
              value: item?.platform,
            }),
          ) || []
        );
      },
    },
  );

  return {
    securityPlatformList,
  };
};
