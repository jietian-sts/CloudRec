import {
  queryIdentityPlatformList,
  queryResourceTypeList,
} from '@/services/asset/AssetController';
import { queryGroupTypeList } from '@/services/resource/ResourceController';
import { useRequest } from '@umijs/max';

export default () => {
  // Cloud Platform - Get Resource Type List
  const { data: assetList } = useRequest(
    () => {
      return queryResourceTypeList({});
    },
    {
      formatResult: (result): Array<any> => {
        const { content } = result;
        return (
          content?.map((item: any) => ({
            label: item.resourceName,
            value: item.resourceType,
          })) || []
        );
      },
    },
  );

  // According to the cloud platform, obtain a list of resource types
  const { data: groupTypeList } = useRequest(
    () => {
      return queryGroupTypeList({} as any);
    },
    {
      formatResult: (result): Array<any> => {
        const { content } = result;
        return content || [];
      },
    },
  );

  // List of cloud platforms that support identity control
  const { data: identityPlatformList } = useRequest(
    () => queryIdentityPlatformList({}),
    {
      formatResult: (r: any) =>
        r?.content?.map(
          (item: { platformName?: string; platform?: string }) => ({
            label: item?.platformName,
            value: item?.platform,
          }),
        ) || [],
    },
  );

  return {
    assetList,
    groupTypeList,
    identityPlatformList,
  };
};
