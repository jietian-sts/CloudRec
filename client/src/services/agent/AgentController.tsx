import { BASE_URL } from '@/services';
import { request } from '@umijs/max';

/** Retrieve the list of currently registered agents in the system: POST /api/agentApi/agentList */
export async function queryAgentList(
  body?: API.AgentInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(`${BASE_URL}/api/agentApi/agentList`, {
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

/** Get a one-time token: POST /api/agentApi/getOnceToken */
export async function getOnceToken(
  body?: API.AgentInfo,
  options?: { [key: string]: any },
) {
  return request<API.Result_T_>(`${BASE_URL}/api/agentApi/getOnceToken`, {
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

/** Rule group deletion interface: DELETE /api/agentApi/exitAgent */
export async function exitAgent(
  params: {
    /** id */
    onceToken: string;
  },
  options?: { [key: string]: any },
) {
  return request<API.Result_String_>(`${BASE_URL}/api/agentApi/exitAgent`, {
    method: 'POST',
    params: { ...params },
    ...(options || {}),
  });
}
