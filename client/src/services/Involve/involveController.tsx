import { BASE_URL } from '@/services';
import { request } from '@umijs/max';

/** Get configuration condition interface GET /api/risk/subscription/getSubConfigList */
export async function querySubConfigList(
  params?: API.AssetInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(
    `${BASE_URL}/api/risk/subscription/getSubConfigList`,
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

/** Save | Edit Subscription POST /api/risk/subscription/changeStatus */
export async function saveSubscriptionConfig(
  body?: API.InvolveInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(
    `${BASE_URL}/api/risk/subscription/saveConfig`,
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

/** Get configuration condition interface GET /api/risk/subscription/getSubscriptionDetail */
export async function querySubscriptionDetailById(
  params?: {
    id: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(
    `${BASE_URL}/api/risk/subscription/getSubscriptionDetail`,
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

/** Execute query configuration Table list interface POST /api/risk/subscription/getSubscriptionList */
export async function querySubscriptionList(
  body?: API.InvolveInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(
    `${BASE_URL}/api/risk/subscription/getSubscriptionList`,
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

/** Enable and disable subscriptions POST /api/risk/subscription/changeStatus */
export async function queryChangeStatus(
  body?: API.InvolveInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(
    `${BASE_URL}/api/risk/subscription/changeStatus`,
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

/** Delete configuration interface: DELETE /api/risk/subscription/deleteSubscription */
export async function deleteInvolve(
  params: {
    /** id */
    id: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(
    `${BASE_URL}/api/risk/subscription/deleteSubscription`,
    {
      method: 'DELETE',
      params: { ...params },
      ...(options || {}),
    },
  );
}
