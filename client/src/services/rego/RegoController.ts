import { BASE_URL } from '@/services';
import { request } from '@umijs/max';

/** Execute POST /api/rego/testRego */
export async function evaluateRego(
  body?: API.Result_RegoInfo_,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(`${BASE_URL}/api/rego/testRego`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** Save POST /api/rego/saveRego */
export async function saveRego(
  body?: API.Result_RegoInfo_,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(`${BASE_URL}/api/rego/saveRego`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** Search for the latest version of Rego information GET /api/rego/queryLatest */
export async function queryLatestById(
  params?: API.Result_RegoInfo_,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(`${BASE_URL}/api/rego/queryLatest`, {
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

/** Rule syntax detection interface: POST /api/rego/lintRego */
export async function lintRego(
  body?: API.Result_RegoInfo_,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(`${BASE_URL}/api/rego/lintRego`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** Query historical version rego information POST /api/rego/queryLatest */
export async function queryRegoList(
  body?: API.Result_RegoInfo_,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(`${BASE_URL}/api/rego/queryRegoList`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
