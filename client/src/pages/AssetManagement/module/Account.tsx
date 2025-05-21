import EnableTag from '@/components/Common/EnableTag';
import { queryIdentityDetailById } from '@/services/asset/AssetController';
import { obtainPlatformIcon } from '@/utils/shared';
import { ProCard } from '@ant-design/pro-components';
import { useIntl, useLocation, useModel, useRequest } from '@umijs/max';
import { Col, Flex, Form, Row } from 'antd';
import dayjs from 'dayjs';
import React, { useEffect } from 'react';

// Account
const Account: React.FC = () => {
  // Get query parameters
  const location = useLocation();
  const queryParameters: URLSearchParams = new URLSearchParams(location.search);
  const id = queryParameters.get('id');
  // Global Info
  const { platformList } = useModel('rule');
  // Intl API
  const intl = useIntl();

  // Query identity detail
  const {
    run: requestIdentityDetailById,
    data: identityDetailInfo,
    loading: identityDetailInfoLoading,
  } = useRequest(
    (id: number) =>
      queryIdentityDetailById({
        id: id,
      }),
    {
      manual: true,
      formatResult: (r) => r?.content,
    },
  );

  useEffect(() => {
    if (id) requestIdentityDetailById(Number(id));
  }, [id]);

  return (
    <ProCard
      loading={identityDetailInfoLoading}
      title={intl.formatMessage({ id: 'rule.module.text.basic.info' })}
    >
      <Form layout={'vertical'}>
        <Row>
          <Col span={12}>
            <Form.Item
              label={intl.formatMessage({
                id: 'asset.module.text.account.name',
              })}
            >
              {identityDetailInfo?.userInfo?.userName || '-'}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              label={intl.formatMessage({
                id: 'cloudAccount.extend.title.cloud.platform',
              })}
            >
              <Flex>
                {obtainPlatformIcon(
                  // @ts-ignore
                  identityDetailInfo?.userInfo?.platform,
                  platformList,
                )}
              </Flex>
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              label={intl.formatMessage({
                id: 'asset.module.text.email',
              })}
            >
              {identityDetailInfo?.userInfo?.email || '-'}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              label={intl.formatMessage({
                id: 'rule.table.columns.text.status',
              })}
            >
              {identityDetailInfo?.userInfo?.status || '-'}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              label={intl.formatMessage({
                id: 'cloudAccount.extend.title.createTime',
              })}
            >
              {identityDetailInfo?.userInfo?.createDate
                ? dayjs(identityDetailInfo?.userInfo?.createDate).format(
                    'YYYY-MM-DD HH:mm:ss',
                  )
                : '-'}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              label={intl.formatMessage({
                id: 'asset.module.text.last.login',
              })}
            >
              {identityDetailInfo?.userInfo?.lastLoginDate
                ? dayjs(identityDetailInfo?.userInfo?.lastLoginDate).format(
                    'YYYY-MM-DD HH:mm:ss',
                  )
                : '-'}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              label={intl.formatMessage({
                id: 'asset.module.text.mfa.status',
              })}
            >
              {/*@ts-ignore*/}
              <EnableTag enable={identityDetailInfo?.userInfo?.mfastatus} />
            </Form.Item>
          </Col>
        </Row>
      </Form>
    </ProCard>
  );
};

export default Account;
