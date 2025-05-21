import { BASE_URL } from '@/services';
import { request } from '@umijs/max';

/** Resource type query interface: GET /api/resource/typeList */
export async function queryTypeList(
  params?: { [key: string]: any },
  options?: { [key: string]: any },
) {
  return request<API.Result_PlatformInfo_>(
    `${BASE_URL}/api/resource/typeList`,
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

/** Resource type query interface: POST /api/resource/groupTypeList */
export async function queryGroupTypeList(
  body?: {
    platformList: string[];
  },
  options?: { [key: string]: any },
) {
  return request<API.Result_PlatformInfo_>(
    `${BASE_URL}/api/resource/groupTypeList`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      data: body,
      ...(options || {}),
    },
  );
}
