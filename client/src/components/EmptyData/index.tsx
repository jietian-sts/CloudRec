import EMPTY_DATA from '@/assets/images/EMPTY_DATA.svg';
import { FormattedMessage } from '@umijs/max';
import { Flex } from 'antd';
import React from 'react';

interface IEmptyData {
  color?: string;
}

const EmptyData: React.FC<IEmptyData> = (props) => {
  const { color = '#FFF' } = props;
  return (
    <Flex
      style={{ width: '100%', height: '100%' }}
      vertical={true}
      align={'center'}
      justify={'center'}
    >
      <img
        src={EMPTY_DATA}
        alt="EMPTY_DATA"
        style={{ width: 64, height: 40 }}
      />
      <span style={{ color, fontSize: 13 }}>
        <FormattedMessage id={'common.tag.text.empty.data'} />
      </span>
    </Flex>
  );
};

export default EmptyData;
