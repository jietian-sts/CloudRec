import { BASE_URL } from '@/services';
import { request } from '@umijs/max';

/** Obtain aggregated data POST api/home/getAggregatedData */
export async function queryAggregatedData(
  body?: { [key: string]: any },
  options?: { [key: string]: any },
) {
  return request<API.Result_AggregatedInfo>(
    `${BASE_URL}/api/home/getAggregatedData`,
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

/** Obtain data on currently supported platforms and displayed resources POST api/home/getPlatformResourceData */
export async function queryPlatformResourceData(
  body?: { [key: string]: any },
  options?: { [key: string]: any },
) {
  return request<API.Result_ResourceInfo>(
    `${BASE_URL}/api/home/getPlatformResourceData`,
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

/** Obtain high-risk, medium risk, and low-risk data to be processed POST api/home/getRiskLevelDataList */
export async function queryRiskLevelData(
  body?: { [key: string]: any },
  options?: { [key: string]: any },
) {
  return request<API.Result_RiskLevelInfo>(
    `${BASE_URL}/api/home/getRiskLevelDataList`,
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

/** Obtain data on the number of AKSK and the number of ACL and ACL without them POST api/home/getAccessKeyAndAclSituation */
export async function queryAccessKeyAndAclSituation(
  body?: { [key: string]: any },
  options?: { [key: string]: any },
) {
  return request<API.Result_AccessKeyInfo>(
    `${BASE_URL}/api/home/getAccessKeyAndAclSituation`,
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

/** Obtain the top 10 high-risk risk data POST api/home/getTopRiskList */
export async function queryTopRiskList(
  body?: { [key: string]: any },
  options?: { [key: string]: any },
) {
  return request<API.Result_RiskRecordInfo>(
    `${BASE_URL}/api/home/getTopRiskList`,
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

/** Obtain the risk trend of the past 7 days POST api/home/getRiskTrend */
export async function queryRiskTrend(
  body?: { [key: string]: any },
  options?: { [key: string]: any },
) {
  return request<API.Result_RiskTrendInfo>(
    `${BASE_URL}/api/home/getRiskTrend`,
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
