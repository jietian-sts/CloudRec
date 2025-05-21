import { querySubConfigList } from '@/services/Involve/involveController';
import { useRequest } from '@umijs/max';

export default () => {
  // Subscription Management - Get Configuration Conditions
  const { data: subConfigList } = useRequest(
    () => {
      return querySubConfigList({});
    },
    {
      formatResult: (result: API.Result_T_): Array<any> => {
        const { content } = result;
        return content;
      },
    },
  );

  return {
    subConfigList,
  };
};
