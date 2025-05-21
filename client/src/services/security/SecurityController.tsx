import { BASE_URL } from '@/services';
import { request } from '@umijs/max';

/** Query risk situation: GET /api/cloudAccount/securityProduct/getOverallPosture */
export async function queryOverallPosture(
  params?: API.BaseSecurityInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(
    `${BASE_URL}/api/cloudAccount/securityProduct/getOverallPosture`,
    {
      method: 'GET',
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

/** Get a list of cloud platforms that support security control: GET /api/cloudAccount/securityProduct/getPlatformList */
export async function querySecurityPlatformList(
  params?: API.BaseSecurityInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(
    `${BASE_URL}/api/cloudAccount/securityProduct/getPlatformList`,
    {
      method: 'GET',
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

/** Query the list of cloud product coverage for cloud accounts interface: POST /api/cloudAccount/securityProduct/getCloudAccountSecurityProductPostureList */
export async function querySecurityProductPostureList(
  body?: API.BaseSecurityInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_SecurityInfo>(
    `${BASE_URL}/api/cloudAccount/securityProduct/getCloudAccountSecurityProductPostureList`,
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
