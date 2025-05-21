import DEFAULT_PLATFORM from '@/assets/images/DEFAULT_PLATFORM.svg';
import { IValueType, platformURLMap } from '@/utils/const';
import { FormattedMessage } from '@umijs/max';
import { Flex, Image, Space, Tooltip } from 'antd';
import _ from 'lodash';
import { ReactNode } from 'react';
const { cloneDeep, isEmpty } = _;

// General ValueType convert ProTable Filter ValueEnum
export const valueListAsValueEnum = (valueList: Array<Record<string, any>>) => {
  const array = cloneDeep(valueList);
  const map: Map<any, any> = new Map();
  if (!isEmpty(array)) {
    for (const i in array) {
      if (Object.prototype.hasOwnProperty.call(array, i)) {
        if (!array[i]?.text && array[i]?.label) {
          array[i].text = array[i].label;
        }
        map.set(array[i].value, array[i]);
      }
    }
  }
  return map;
};

// Temporarily exclude global tenant options
export const valueListExcludeCover = (valueList: Array<API.TenantInfo>) => {
  return (
    valueList?.filter(
      (item: API.TenantInfo) =>
        item.tenantName !== '全局租户' && ![1].includes(item.tenantId!),
    ) || []
  );
};

export const valueListAddIcon = (valueList: Array<any>, position?: string) => {
  const justify = position || 'center';
  return valueList?.map((item: IValueType, index) => {
    return {
      label: (
        <Flex align={'center'} justify={justify} key={index}>
          <img
            key={index}
            style={{ height: '18px', marginRight: '6px' }}
            // @ts-ignore
            src={platformURLMap?.[item?.value + '_URL'] || DEFAULT_PLATFORM}
            alt={item?.value?.toString()}
          />
          {item?.label}
        </Flex>
      ),
      value: item.value,
    };
  });
};

export const valueListAddTag = (valueList: Array<any>) => {
  return valueList?.map((item: IValueType) => {
    return {
      label: (
        <Space size={6}>
          <Image
            style={{ marginBottom: 4 }}
            src={item.icon}
            alt="RISK_LEVEL"
            preview={false}
            width={20}
            height={13}
          />
          <span style={{ color: item.color }}>{item.text}</span>
        </Space>
      ),
      value: item.value,
    };
  });
};

export const obtainPlatformIcon = (
  platform: string,
  platformList: Array<any>,
) => {
  const elem = platformList?.find((item) => item.value === platform);
  if (!isEmpty(elem))
    return (
      <Flex align={'center'} justify={'center'} wrap={'nowrap'}>
        <img
          style={{ width: '18px', marginRight: '6px' }}
          // @ts-ignore
          src={platformURLMap?.[platform + '_URL'] || DEFAULT_PLATFORM}
          alt="PLATFORM_ICON"
        />
        <span color={'#333'} style={{ lineHeight: 1 }}>
          {elem?.label || '-'}
        </span>
      </Flex>
    );
  else return <></>;
};

export const obtainMultiplePlatformIcon = (platform: string) => {
  const platforms = platform.split(','); // Split the platform string into an array
  return (
    <Flex align={'center'} justify={'center'} wrap={'nowrap'}>
      {platforms.map((p) => {
        return (
          <Flex
            key={p}
            align={'center'}
            wrap={'nowrap'}
            style={{ marginRight: '10px' }}
          >
            <img
              style={{ width: '18px', marginRight: '6px' }}
              // @ts-ignore
              src={platformURLMap?.[p.trim() + '_URL'] || DEFAULT_PLATFORM}
              alt={'PLATFORM_ICON'}
            />
          </Flex>
        );
      })}
    </Flex>
  );
};

export const obtainPlatformEasyIcon = (
  platform: string,
  platformList: Array<any>,
) => {
  const elem = platformList?.find((item) => item.value === platform);
  return (
    <Tooltip title={elem?.label || '-'}>
      <Flex align={'center'} justify={'center'}>
        <img
          style={{ height: '18px', marginRight: '6px' }}
          // @ts-ignore
          src={platformURLMap?.[platform + '_URL']}
          alt="PLATFORM_ICON"
        />
      </Flex>
    </Tooltip>
  );
};

export const obtainPlatformEasyName = (
  platform: string,
  platformList: Array<any>,
) => {
  const elem = platformList?.find((item) => item.value === platform);
  return elem.label;
};

// Obtain the first attribute and corresponding value of the target object
export const obtainFirstProperty = (
  object: Record<string, any>,
): { key: string; value: string } => {
  const cloneObject = cloneDeep(object);
  const keys: string[] = Object.keys(cloneObject);
  const key: string = keys[0];
  const value = cloneObject[key];
  return { key, value };
};

// Retrieve the last attribute and corresponding value of the target object
export const obtainLastProperty = (
  object: Record<string, any>,
): { key: string; value: string } => {
  const cloneObject = cloneDeep(object);
  let allKeys = Object.keys(cloneObject);
  // Get the last attribute
  const key = allKeys[allKeys.length - 1];
  // Get the value corresponding to the last attribute
  const value = cloneObject[key];
  return { key, value };
};

// Obtain risk status
export const obtainRiskStatus = (valueList: Array<any>, status: string) => {
  const elem = valueList?.find((item) => item.value === status);
  return (
    <Space size={6}>
      <Image
        style={{ marginBottom: 4 }}
        src={elem?.icon}
        alt="RISK_STATUS"
        preview={false}
        width={14}
        height={14}
      />
      <span style={{ color: elem?.color }}>{elem?.label}</span>
    </Space>
  );
};

// Obtain risk level
export const obtainRiskLevel = (valueList: Array<any>, status: string) => {
  const elem = valueList?.find((item) => item?.value === status);
  return (
    <Tooltip title={elem?.text}>
      <img
        src={elem?.icon}
        alt="RISK_LEVEL"
        style={{ width: 20, height: 14 }}
      />
    </Tooltip>
  );
};

// Obtain risk level
export const obtainIntactRiskLevel = (
  valueList: Array<any>,
  status: string,
) => {
  const elem = valueList?.find((item: any) => item.value === status);
  return (
    <Flex align={'center'}>
      <img
        src={elem?.icon}
        alt="RISK_LEVEL"
        style={{ width: 20, height: 14 }}
      />
      <span style={{ color: elem?.color, marginLeft: 6 }}>{elem?.text}</span>
    </Flex>
  );
};

// Recursive traversal to obtain rule type concatenation cascade component Label
export const obtainRuleTypeTextFromValue = (
  ruleTypeArray: Array<any>,
  valueArray: Array<any>,
): string | null => {
  try {
    for (const option of ruleTypeArray) {
      if (option?.id === valueArray?.[0]) {
        if (valueArray?.length === 1) {
          return option?.typeName; // If there is only one layer, return the current label
        }
        if (option?.childList) {
          // Recursive search for sub options
          const childLabel = obtainRuleTypeTextFromValue(
            option?.childList,
            valueArray?.slice(1),
          );
          if (childLabel && option) {
            return `${option?.typeName || '-'}/${childLabel || '-'}`; // Splicing parent and child labels
          }
        }
      }
    }
    // If no corresponding label is found
    return null;
  } catch (e) {
    return null;
  }
};

// Get a set of rule type copyrighting
export const obtainRuleTypeTextList = (
  ruleTypeList: Array<any>,
  valueList: Array<any>,
) => {
  const array: Array<string | null> = valueList?.map((item) => {
    return obtainRuleTypeTextFromValue(ruleTypeList, item);
  });
  return array?.toString();
};

// Recursive traversal to obtain resource type concatenation cascade components Label
export const obtainResourceTypeTextFromValue = (
  resourceTypeArray: Array<any>,
  valueArray: Array<any>,
): string | null => {
  try {
    for (const option of resourceTypeArray) {
      if (option.value === valueArray?.[0]) {
        if (valueArray?.length === 1) {
          return option?.label; // If there is only one layer, return the current label
        }
        if (option?.children) {
          const childLabel = obtainResourceTypeTextFromValue(
            option?.children,
            valueArray?.slice(1),
          ); // Recursive search for sub options
          if (childLabel && option) {
            return `${option?.label || '-'}/${childLabel || '-'}`; // Splicing parent and child levels label
          }
        }
      }
    }
    // If no corresponding one is found label
    return null;
  } catch (e) {
    return null;
  }
};

// Get the current tenant name
export const obtainTenantName = (valueList: Array<any>, tenantId: number) => {
  const elem = valueList?.find((item) => item?.value === tenantId);
  return elem?.label;
};

// Traverse to obtain primary classification of resource types Label
export const obtainGroupTypeTextFromValue = (
  groupTypeArray: Array<any>,
  key: string,
): string | null => {
  if (isEmpty(groupTypeArray)) return '-';
  const item = groupTypeArray.find((item) => item.value === key);
  // If no corresponding one is found label
  return item?.label || '-';
};

// Get the current time point [morning, noon, afternoon, evening]
export const obtainTimeOfDay = (): string | ReactNode => {
  const now: Date = new Date();
  const hours: number = now.getHours();
  if (hours >= 5 && hours < 12) {
    return <FormattedMessage id={'individual.module.text.good.morning'} />; // 5:00 - 11:59
  } else if (hours >= 12 && hours < 14) {
    return <FormattedMessage id={'individual.module.text.good.afternoon'} />; // 12:00 - 13:59
  } else if (hours >= 14 && hours < 18) {
    // 14:00 - 17:59
    return (
      <FormattedMessage id={'individual.module.text.good.afternoon.still'} />
    );
  } else {
    return <FormattedMessage id={'individual.module.text.good.evening'} />; // 18:00 - 4:59
  }
};

// Obtain the color corresponding to the risk level
export const obtainRiskLevelColor = (valueList: Array<any>, key: string) => {
  const item = valueList.find((item) => item.value === key);
  return item?.color;
};

// Return the last element of the array
export function obtainLastElement<T>(arr: T[]): T | undefined {
  if (arr.length === 0) {
    return undefined;
  }
  return arr[arr.length - 1];
}

/*
 * Retain n decimal places after the decimal point
 * Note: It can only be used for display purposes and not for monetary purposes
 * */
export const roundToN = (value: number, accuracy: number): string => {
  let n: number = accuracy;
  const strValue = value.toString();

  // Check if there is an index component
  const exponentIndex: number = strValue.indexOf('e');
  if (exponentIndex > -1) {
    const exponent: number = parseInt(strValue.substr(exponentIndex + 1), 10);
    n -= exponent;
  }

  // Check if there is a decimal point
  const decimalIndex: number = strValue.indexOf('.');
  if (decimalIndex > -1 && strValue.length - decimalIndex - 1 > n) {
    // Numbers need to be rounded to a specific number of decimal places
    const fixedValue = (value + Math.pow(10, -n - 1)).toString();

    // Find the index of the exponential part in the rounded string representation (if any)
    const newExponentIndex: number = fixedValue.indexOf('e');
    if (newExponentIndex > -1) {
      return parseFloat(fixedValue).toFixed(n);
    }

    // Without the index part, directly extract the string
    const end: number = fixedValue.indexOf('.') + n + 1;
    return fixedValue.substring(0, end);
  }

  // No rounding required, convert directly to a string
  return value.toFixed(n);
};

export const BlobExportXLSXFn = (values: any, name: string) => {
  const a = document.createElement('a'); // Create a label
  const blob = new Blob([values], {
    type: 'application/vnd.ms-excel;charset=UTF-8',
  }); // Retrieve the streaming data of the interface
  a.style.display = 'none';
  a.href = URL.createObjectURL(blob); // Create a new URL to represent the specified blob object
  a.download = name + '.xlsx'; // Specify the download file name
  a.click(); // Trigger download
  URL.revokeObjectURL(a.href); // Release URL Object
};

export const BlobExportZIPFn = (values: any, name: string) => {
  const blob = new Blob([values], {
    type: 'text/plain;charset=UTF-8',
  }); // Retrieve the streaming data of the interface
  const url = URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = name + '.zip';
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
};

// format intl Pagination total
export const showTotalIntlFunc = (
  total: number,
  range: Array<number>,
  locale?: string,
) =>
  locale === 'en-US'
    ? `${range[0]}-${range[1]} of ${total} items`
    : `第 ${range[0]}-${range[1]} 条/总共 ${total} 条`;
