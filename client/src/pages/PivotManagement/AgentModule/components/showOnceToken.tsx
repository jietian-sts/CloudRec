import Disposition from '@/components/Disposition';
import { InfoCircleOutlined } from '@ant-design/icons';
import { useIntl } from '@umijs/max';
import { Col, List, Modal, Row, Tooltip } from 'antd';
import Paragraph from 'antd/lib/typography/Paragraph';
import React from 'react';
import styles from '../index.less';

interface ITokenModalProps {
  visible: boolean;
  onClose: () => void;
  tokenInfo: {
    token: string;
    script: string;
    expireTime: string;
    username: string;
    tokenList: Array<any>;
  };
}

const ToolTipContent: React.FC = () => {
  // Intl API
  const intl = useIntl();

  return (
    <div>
      <div>
        {intl.formatMessage({
          id: 'collector.extend.text.token.tip1',
        })}
      </div>
      <div>
        {intl.formatMessage({
          id: 'collector.extend.text.token.tip2',
        })}
        <div>
          {intl.formatMessage({
            id: 'collector.extend.text.token.tip3',
          })}
        </div>
        <div>
          {intl.formatMessage({
            id: 'collector.extend.text.token.tip4',
          })}
        </div>
      </div>
    </div>
  );
};

const TokenModal: React.FC<ITokenModalProps> = ({
  visible,
  onClose,
  tokenInfo,
}) => {
  // Intl API
  const intl = useIntl();
  return (
    <Modal
      width={740}
      style={{ top: 120 }}
      title={intl.formatMessage({
        id: 'collector.extend.text.token.information',
      })}
      open={visible}
      onCancel={onClose}
      footer={null}
    >
      <p>
        Token
        <Tooltip
          overlayClassName={styles['toolTipWrap']}
          placement="top"
          title={<ToolTipContent />}
        >
          <InfoCircleOutlined className={styles['tooltipIcon']} />
        </Tooltip>
        :
        {tokenInfo?.token ? (
          <Paragraph copyable>{tokenInfo.token}</Paragraph>
        ) : (
          '-'
        )}
      </p>
      <p>
        <List
          bordered
          dataSource={tokenInfo?.tokenList || []}
          renderItem={(item) => (
            <List.Item>
              <Row gutter={[24, 0]} style={{ width: '100%' }}>
                <Col span={4}>
                  <Disposition
                    text={item?.platformName || '-'}
                    rows={1}
                    placement={'topLeft'}
                  />
                </Col>
                <Col span={20}>
                  <Disposition
                    text={item?.script || '-'}
                    rows={1}
                    placement={'topLeft'}
                    copyable={true}
                  />
                </Col>
              </Row>
            </List.Item>
          )}
        />
      </p>
      <p>
        {intl.formatMessage({
          id: 'collector.extend.text.expiration.date',
        })}
        &nbsp;:&nbsp;
        {tokenInfo?.expireTime || '-'}
      </p>
      <p>
        {intl.formatMessage({
          id: 'rule.input.text.rule.group.creator',
        })}
        &nbsp;:&nbsp;{tokenInfo?.username || '-'}
      </p>
    </Modal>
  );
};

export default TokenModal;
