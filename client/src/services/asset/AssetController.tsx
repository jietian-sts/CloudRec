import { BASE_URL } from '@/services';
import { request } from '@umijs/max';

/** According to the cloud platform - interface for obtaining a list of resource types GET /api/resource/typeList */
export async function queryResourceTypeList(
  params?: API.AssetInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_AssetTypeInfo>(
    `${BASE_URL}/api/resource/typeList`,
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

/** Multi tenant division query asset list POST /api/resource/queryResourceList */
export async function queryResourceList(
  body?: API.AssetInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_AssetInfo>(
    `${BASE_URL}/api/resource/queryResourceList`,
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

/** Get a list of fields that support searching for resources POST /api/resource/resourceFieldList */
export async function queryResourceFieldList(
  body?: API.AssetInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_AssetFieldInfo>(
    `${BASE_URL}/api/resource/resourceFieldList`,
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

/** Query asset details POST /api/resource/queryResourceDetail */
export async function queryResourceDetailById(
  body?: API.AssetInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_AssetDetailInfo>(
    `${BASE_URL}/api/resource/queryResourceDetail`,
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

/** Save detailed configuration POST /api/resourceDetailConfig/saveDetailConfig */
export async function saveDetailConfig(
  body?: API.AssetConfig,
  options?: { [key: string]: any },
) {
  return request<API.Result_String_>(
    `${BASE_URL}/api/resourceDetailConfig/saveDetailConfig`,
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

/** Delete detailed configuration POST /api/resourceDetailConfig/delDetailConfig */
export async function delDetailConfig(
  body?: API.AssetInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_String_>(
    `${BASE_URL}/api/resourceDetailConfig/delDetailConfig`,
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

/** Modify details status POST  /api/resourceDetailConfig/modifyResourceDetailConfigStatus */
export async function modifyResourceDetailConfigStatus(
  body?: API.AssetInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_String_>(
    `${BASE_URL}/api/resourceDetailConfig/modifyResourceDetailConfigStatus`,
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

/**  Query asset details configuration list (no need for pagination) POST /api/resourceDetailConfig/queryDetailConfigList */
export async function queryDetailConfigList(
  body?: API.AssetInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_String_>(
    `${BASE_URL}/api/resourceDetailConfig/queryDetailConfigList`,
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

/** Asset aggregation POST  /api/resource/queryAggregateAssets */
export async function queryAggregateAssets(
  body?: API.AssetInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_AggregateAssetInfo>(
    `${BASE_URL}/api/resource/queryAggregateAssets`,
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

/** Resource Risk Quantity POST  /api/resource/queryResourceRiskQuantity */
export async function queryResourceRiskQuantity(
  body?: API.AssetInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_AssetRiskQuantity>(
    `${BASE_URL}/api/resource/queryResourceRiskQuantity`,
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

/** Obtain risk cards POST  /api/rule/queryRuleList */
export async function queryRiskRuleList(
  body?: API.BaseRiskCard,
  options?: { [key: string]: any },
) {
  return request<API.Result_RiskCard>(`${BASE_URL}/api/rule/queryRuleList`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** Identity List POST  /api/identity/queryIdentityList */
export async function queryIdentityList(
  body?: API.BaseIdentity,
  options?: { [key: string]: any },
) {
  return request<API.Result_Identity>(
    `${BASE_URL}/api/identity/queryIdentityList`,
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

/** Get a list of cloud platforms that support identity control: GET /api/identity/getPlatformList */
export async function queryIdentityPlatformList(
  params?: API.BaseIdentity,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(`${BASE_URL}/api/identity/getPlatformList`, {
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

/** Obtain identity details based on ID POST  /api/identity/queryIdentityList */
export async function queryIdentityDetailById(
  body: API.BaseIdentity,
  options?: { [key: string]: any },
) {
  return request<API.Result_Identity_Detail>(
    `${BASE_URL}/api/identity/queryIdentity/${body.id}`,
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

/** Query risk info POST  /api/rule/queryRiskInfo */
export async function queryRiskInfo(
  body: API.BaseIdentityRisk,
  options?: { [key: string]: any },
) {
  return request<API.Result_IdentityRisk>(
    `${BASE_URL}/api/identity/queryRiskInfo`,
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

/** Query rule card info POST  /api/rule/queryRiskInfo */
export async function queryIdentityCardList(
  body: API.BaseIdentityCard,
  options?: { [key: string]: any },
) {
  return request<API.Result_IdentityCard>(
    `${BASE_URL}/api/identity/queryIdentityCardList`,
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

/** Get tag collection POST  /api/identity/groupTagList */
export async function queryGroupTagList(
  body: { [key: string]: any },
  options?: { [key: string]: any },
) {
  return request<API.Result_GroupTag>(`${BASE_URL}/api/identity/groupTagList`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
