import { BASE_URL } from '@/services';
import { request } from '@umijs/max';

/** Save global variable configuration: POST /api/globalVariableConfig/saveGlobalVariableConfig */
export async function saveGlobalVariableConfig(
  body?: API.GlobalVariableConfigInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(
    `${BASE_URL}/api/globalVariableConfig/saveGlobalVariableConfig`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      data: {
        ...body,
      },
      ...(options || {}),
    },
  );
}

/** Get the global variable configuration list: POST /api/globalVariableConfig/listGlobalVariableConfig */
export async function listGlobalVariableConfig(
  body?: API.GlobalVariableConfigInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(
    `${BASE_URL}/api/globalVariableConfig/listGlobalVariableConfig`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      data: {
        ...body,
      },
      ...(options || {}),
    },
  );
}

/** Get a one-time token: POST /api/globalVariableConfig/deleteGlobalVariableConfig */
export async function deleteGlobalVariableConfig(
  params: {
    /** id */
    id: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.Result_String_>(
    `${BASE_URL}/api/globalVariableConfig/deleteGlobalVariableConfig`,
    {
      method: 'DELETE',
      params: { ...params },
      ...(options || {}),
    },
  );
}
