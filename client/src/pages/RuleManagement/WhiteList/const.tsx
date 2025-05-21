import { IValueType } from '@/utils/const';
import { FormattedMessage } from '@umijs/max';
import { cloneDeep, isEmpty } from 'lodash';

export const WhiteListRuleTypeList: Array<IValueType> = [
  {
    label: <FormattedMessage id={'rule.module.text.rule.engine'} />,
    value: 'RULE_ENGINE',
  },
  {
    label: <FormattedMessage id={'rule.module.text.rule.rego'} />,
    value: 'REGO',
  },
];

// Format FormData parameters
export const serializeData = (formData: Record<string, any>) => {
  const data = cloneDeep(formData);
  const { ruleConfigList } = data;
  // Format the value of the ruleConfig List field
  if (Array.isArray(ruleConfigList) && !isEmpty(ruleConfigList)) {
    data.ruleConfigList =
      ruleConfigList.map((item, i) => {
        // eslint-disable-next-line
        const { idx, ...reset } = item;
        return {
          ...reset,
          id: i + 1,
        };
      }) || [];
  }
  return data;
};

// Reformat FormData parameter (assignment)
export const deserializeData = (formData: Record<string, any>) => {
  const data: Record<string, any> = cloneDeep(formData);
  const ruleConfig = JSON.parse(cloneDeep(data?.ruleConfig)) || [];
  const editableKeyList: Array<number> = [];
  // Format the value of the ruleConfig List field
  if (Array.isArray(ruleConfig) && !isEmpty(ruleConfig)) {
    data.ruleConfigList =
      ruleConfig?.map((item) => {
        // eslint-disable-next-line
        const { id, ...reset } = item;
        editableKeyList.push(id);
        return {
          ...reset,
          idx: id,
        };
      }) || [];
  }
  data.editableKeyList = editableKeyList;
  return data;
};

// Reformat FormData parameter (assignment)
export const deserializeUniqueData = (formData: Record<string, any>) => {
  const data: Record<string, any> = cloneDeep(formData);
  const ruleConfig = cloneDeep(data?.ruleConfigList) || [];
  const editableKeyList: Array<number> = [];
  // Format the value of the ruleConfig List field
  if (Array.isArray(ruleConfig) && !isEmpty(ruleConfig)) {
    data.ruleConfigList =
      ruleConfig?.map((item) => {
        // eslint-disable-next-line
        const { id, ...reset } = item;
        editableKeyList.push(id);
        return {
          ...reset,
          idx: id,
        };
      }) || [];
  }
  data.editableKeyList = editableKeyList;
  return data;
};

export const WHITELIST_DEFAULT_CODE_EDITOR = `package cloudrec_white_list

import rego.v1
  
default whited = false
  
whited if {
   input.method == "GET"
   input.path == "/public/resource"
}
`;
