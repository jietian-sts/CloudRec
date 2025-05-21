import {
  listAddedTenants,
  queryAllTenantList,
} from '@/services/tenant/TenantController';
import { useRequest } from '@umijs/max';

export default () => {
  // Tenants who have applied to join
  const { data: tenantListAdded } = useRequest(
    () => {
      return listAddedTenants({});
    },
    {
      formatResult: (r: any) => {
        const { content } = r;
        return content || [];
      },
    },
  );

  // List of all tenants
  const { data: tenantListAll } = useRequest(
    () => {
      return queryAllTenantList({});
    },
    {
      formatResult: (r: any) => {
        const { content } = r;
        const { data } = content;

        return (
          data?.map((item: API.TenantInfo) => ({
            label: item.tenantName || '-',
            value: item.id,
          })) || []
        );
      },
    },
  );

  return {
    tenantListAdded,
    tenantListAll,
  };
};
