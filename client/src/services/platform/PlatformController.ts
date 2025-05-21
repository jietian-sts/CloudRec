import { BASE_URL } from '@/services';
import { request } from '@umijs/max';

/** Platform query interface: GET /api/platform/platformList */
export async function queryPlatformList(
  params?: { [key: string]: any },
  options?: { [key: string]: any },
) {
  return request<API.Result_PlatformInfo_>(
    `${BASE_URL}/api/platform/platformList`,
    {
      method: 'get',
      headers: {
        'Content-Type': 'application/json',
      },
      params: {
        ...params,
      },
      ...(options || {}),
    },
  );
}
