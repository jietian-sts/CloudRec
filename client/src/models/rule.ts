import { queryPlatformList } from '@/services/platform/PlatformController';
import { queryTypeList } from '@/services/resource/ResourceController';
import {
  queryAllRuleList,
  queryRuleGroupList,
  queryRuleTypeList,
  queryWhitedConfigList,
} from '@/services/rule/RuleController';
import { useRequest } from '@umijs/max';

export default () => {
  // Cloud Platform List
  const { data: platformList }: any = useRequest(
    () => {
      return queryPlatformList({});
    },
    {
      formatResult: (r: any) => {
        const { content } = r;
        return content?.map((item: Record<string, any>) => ({
          label: item.platformName,
          value: item.platform,
        }));
      },
    },
  );

  // Rule Group List
  const { data: ruleGroupList }: any = useRequest(
    () => {
      return queryRuleGroupList({});
    },
    {
      formatResult: (r: any) => {
        const { content } = r;
        const { data } = content || {};
        return data?.map((item: any) => ({
          label: item.groupName,
          value: item.id,
        }));
      },
    },
  );

  // List of Resource Types
  const { data: resourceList }: any = useRequest(
    () => {
      return queryTypeList({});
    },
    {
      formatResult: (r: any) => {
        const { content } = r;
        return content?.map((item: Record<string, any>) => ({
          label: item.resourceName,
          value: item.resourceType,
        }));
      },
    },
  );

  // Rule Type List
  const { data: ruleTypeList }: any = useRequest(
    () => {
      return queryRuleTypeList({});
    },
    {
      formatResult: (r: any) => {
        const { content } = r;
        return content;
      },
    },
  );

  const { data: allRuleList }: any = useRequest(
    () => {
      return queryAllRuleList({});
    },
    {
      formatResult: (r: any) =>
        r.content?.map((item: { [key: string]: any }) => ({
          ...item,
          key: item?.id,
          label: item?.ruleName,
          value: item?.ruleCode,
        })) || [],
    },
  );

  // White List Management - Get Configuration Conditions
  const { data: whiteListConfigList } = useRequest(
    () => {
      return queryWhitedConfigList({});
    },
    {
      formatResult: (result: API.Result_T_): Array<any> => {
        const { content } = result;
        return content;
      },
    },
  );

  return {
    whiteListConfigList,
    allRuleList,
    ruleTypeList,
    platformList,
    ruleGroupList,
    resourceList,
  };
};
