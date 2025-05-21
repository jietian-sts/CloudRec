import { BASE_URL } from '@/services';
import { request } from '@umijs/max';

/** Current user information query interface: POST /api/tenant/queryTenantList */
export async function queryTenantList(
  body?: API.TenantInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result>(`${BASE_URL}/api/tenant/queryTenantList`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: {
      ...body,
    },
    ...(options || {}),
  });
}

/** Current all tenant information query interface: POST /api/tenant/queryAllTenantList */
export async function queryAllTenantList(
  params?: { [key: string]: any },
  options?: { [key: string]: any },
) {
  return request<API.Result>(`${BASE_URL}/api/tenant/queryAllTenantList`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** Save tenant information: POST /api/tenant/saveTenant */
export async function saveTenant(
  body?: API.TenantInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result>(`${BASE_URL}/api/tenant/saveTenant`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: {
      ...body,
    },
    ...(options || {}),
  });
}

/** View members within the tenant: GET /api/tenant/queryMember */
export async function queryMember(
  body?: API.TenantInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result>(`${BASE_URL}/api/tenant/queryMember`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: {
      ...body,
    },
    ...(options || {}),
  });
}

/** Add Members: POST /api/tenant/joinUser */
export async function joinUser(
  body?: API.TenantUser,
  options?: { [key: string]: any },
) {
  return request<API.Result_String_>(`${BASE_URL}/api/tenant/joinUser`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: {
      ...body,
    },
    ...(options || {}),
  });
}

/** Remove Members: POST /api/tenant/removeUser */
export async function removeUser(
  params?: API.TenantUser,
  options?: { [key: string]: any },
) {
  return request<API.Result_String_>(`${BASE_URL}/api/tenant/removeUser`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** Switch the current user's tenant: POST /api/tenant/changeTenant */
export async function changeTenant(
  body?: API.TenantInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_String_>(`${BASE_URL}/api/tenant/changeTenant`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: {
      ...body,
    },
    ...(options || {}),
  });
}

/** Currently added to the tenant list: POST /api/tenant/changeTenant */
export async function listAddedTenants(
  body?: API.TenantInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result>(`${BASE_URL}/api/tenant/listAddedTenants`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
    data: {
      ...body,
    },
    ...(options || {}),
  });
}
