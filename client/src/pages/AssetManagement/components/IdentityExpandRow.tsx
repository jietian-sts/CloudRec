import { Empty, Flex, Row, Space, Typography } from 'antd';
import { isEmpty } from 'lodash';
import React from 'react';
const { Text, Paragraph } = Typography;

interface IIdentityExpandRow {
  record: API.BaseIdentity;
}

// Identity expand row
const IdentityExpandRow: React.FC<IIdentityExpandRow> = (props) => {
  // Component Props
  const { record } = props;

  return (
    <div style={{ padding: '0 12px' }}>
      <Row style={{ marginBottom: 8 }}>
        <Text strong>Access Key List</Text>
      </Row>
      <Row>
        {!isEmpty(record?.accessInfos) ? (
          <Space wrap size={16}>
            {record?.accessInfos?.map((item, index) => (
              <Paragraph copyable key={index} style={{ marginBottom: 0 }}>
                {item?.accessKeyId}
              </Paragraph>
            ))}
          </Space>
        ) : (
          <Flex style={{ width: '100%', display: 'block' }}>
            <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
          </Flex>
        )}
      </Row>
    </div>
  );
};

export default IdentityExpandRow;
