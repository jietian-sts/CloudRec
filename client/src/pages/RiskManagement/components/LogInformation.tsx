import styleType from '@/components/Common/index.less';
import {
  queryOperationLog,
  requestCommentInformation,
} from '@/services/risk/RiskController';
import { ProCard } from '@ant-design/pro-components';
import { useIntl, useRequest } from '@umijs/max';
import {
  Breakpoint,
  Button,
  Col,
  ConfigProvider,
  Empty,
  Flex,
  Form,
  Grid,
  Input,
  Row,
  Timeline,
  message,
} from 'antd';
import { isEmpty } from 'lodash';
import React, { useEffect, useState } from 'react';
import styles from '../index.less';
const { useBreakpoint } = Grid;

interface ILogInformation {
  riskDrawerInfo: Record<string, any>;
}

// Risk changes
const LogInformation: React.FC<ILogInformation> = (props) => {
  // Ant Design Provide monitoring of screen width changes
  const breakpoints: Partial<Record<Breakpoint, boolean>> = useBreakpoint();
  // Risk Info
  const { riskDrawerInfo } = props;
  // Message Instance
  const [messageApi, contextHolder] = message.useMessage();
  // Operation Log List
  const [operationLogList, setOperationLogList] = useState<Array<any>>([]);
  // Intl API
  const intl = useIntl();

  // Log information
  const { run: requestOperationLog, loading: operationLogLoading } = useRequest(
    (id) => {
      return queryOperationLog({ id });
    },
    {
      manual: true,
      formatResult: (r: API.Result_RiskLogInfo): void => {
        const { content } = r;
        setOperationLogList(content);
      },
    },
  );

  // Add comment information
  const onClickAddLog = async (
    formData: Record<string, any>,
  ): Promise<void> => {
    const postBody = {
      id: riskDrawerInfo?.id,
      notes: formData?.notes,
    };
    const r = await requestCommentInformation(postBody);
    if (r.msg === 'success') {
      messageApi.success(
        intl.formatMessage({ id: 'common.message.text.add.success' }),
      );
      requestOperationLog(riskDrawerInfo.id);
    }
  };

  useEffect((): void => {
    if (riskDrawerInfo?.id) requestOperationLog(riskDrawerInfo.id);
  }, [riskDrawerInfo]);

  return (
    <ProCard
      style={{ backgroundColor: 'transparent', marginTop: 4 }}
      className={styles['LogInformation']}
      loading={operationLogLoading}
      title={
        <div
          className={styleType['customTitle']}
          style={{ color: 'rgb(36, 36, 36)', fontWeight: 'normal' }}
        >
          {intl.formatMessage({
            id: 'risk.module.text.log.information',
          })}
        </div>
      }
      headStyle={{
        paddingInline: 0,
        paddingBlockStart: 0,
      }}
      bodyStyle={{
        paddingInline: 0,
      }}
    >
      {contextHolder}
      <ConfigProvider
        theme={{
          components: {
            Input: {
              /* This is your component token */
              colorTextPlaceholder: 'rgb(142, 142, 142)',
              colorBgContainer: '#f9f9f9',
              activeBg: '#FFF',
            },
          },
        }}
      >
        <Form onFinish={onClickAddLog}>
          <Row gutter={[24, 0]}>
            <Col span={breakpoints?.xxl ? 12 : 20}>
              <Form.Item
                name="notes"
                rules={[
                  {
                    required: true,
                    message: intl.formatMessage({
                      id: 'risk.module.text.comment.not.empty',
                    }),
                  },
                ]}
              >
                <Input
                  placeholder={intl.formatMessage({
                    id: 'risk.module.text.add.comment',
                  })}
                  allowClear
                />
              </Form.Item>
            </Col>
            <Col span={4}>
              <Form.Item>
                <Button
                  type="primary"
                  htmlType="submit"
                  style={{ borderRadius: 4 }}
                >
                  {intl.formatMessage({
                    id: 'common.button.text.add',
                  })}
                </Button>
              </Form.Item>
            </Col>
          </Row>
        </Form>
      </ConfigProvider>
      {isEmpty(operationLogList) ? (
        <Row gutter={[24, 0]}>
          <Col span={breakpoints?.xxl ? 12 : 20}>
            <Flex justify={'center'}>
              <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
            </Flex>
          </Col>
        </Row>
      ) : (
        <Timeline
          items={operationLogList?.map((item: API.BaseRiskLogInfo) => {
            return {
              children: (
                <div key={item?.id}>
                  <div style={{ marginBottom: 4 }}>
                    <strong>{item?.action}</strong>
                  </div>
                  <div className={styles['actionItem']}>
                    <span className={styles['actionItemTitle']}>
                      {intl.formatMessage({
                        id: 'risk.module.text.operator',
                      })}
                      &nbsp;: &nbsp;
                    </span>
                    <span>{item?.username || '-'}</span>
                  </div>
                  <div className={styles['actionItem']}>
                    <span className={styles['actionItemTitle']}>
                      {intl.formatMessage({
                        id: 'risk.module.text.operating.time',
                      })}
                      &nbsp;: &nbsp;
                    </span>
                    <span>{item?.gmtCreate || '-'}</span>
                  </div>
                  <div className={styles['actionItem']}>
                    <span className={styles['actionItemTitle']}>
                      {intl.formatMessage({
                        id: 'risk.module.text.notes',
                      })}
                      &nbsp;: &nbsp;
                    </span>
                    <span>{item?.notes || '-'}</span>
                  </div>
                </div>
              ),
            };
          })}
        />
      )}
    </ProCard>
  );
};

export default LogInformation;
