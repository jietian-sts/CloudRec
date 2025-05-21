import I_KNOW from '@/assets/images/I_KNOW.png';
import ConditionTag from '@/components/Common/ConditionTag';
import Disposition from '@/components/Disposition';
import { JSON_EDITOR_LIST } from '@/pages/CloudAccount/AccountList/components/EditModalForm';
import { cloudAccountDetailById } from '@/services/account/AccountController';
import { queryGroupTypeList } from '@/services/resource/ResourceController';
import { IValueType } from '@/utils/const';
import {
  obtainPlatformIcon,
  obtainResourceTypeTextFromValue,
} from '@/utils/shared';
import { useIntl, useModel, useRequest } from '@umijs/max';
import {
  Button,
  Col,
  ConfigProvider,
  Divider,
  Flex,
  Form,
  Modal,
  Row,
  Typography,
} from 'antd';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import styles from '../../index.less';
const { Text } = Typography;

interface IAccountModalProps {
  accountModalVisible: boolean;
  setAccountModalVisible: Dispatch<SetStateAction<boolean>>;
  accountModalInfo: Record<string, any>;
}

// Cloud account details
const AccountModal: React.FC<IAccountModalProps> = (props) => {
  // Global Props
  const { platformList } = useModel('rule');
  const { tenantListAll } = useModel('tenant');
  // Intl API
  const intl = useIntl();
  // Component List
  const { accountModalVisible, accountModalInfo, setAccountModalVisible } =
    props;
  // ResourceTypeList
  const [resourceTypeList, setResourceTypeList] = useState([]);

  // According to the cloud platform, obtain a list of resource types
  const { run: requestResourceTypeList, loading: resourceTypeListLoading } =
    useRequest(
      (value: string) => {
        return queryGroupTypeList({ platformList: [value] });
      },
      {
        manual: true,
        formatResult: (result) => {
          const { content } = result;
          setResourceTypeList((content as any) || []);
          return content;
        },
      },
    );

  // Cloud Account Detail
  const {
    data: accountInfo,
    run: requestCloudAccountDetailById,
    loading: cloudAccountDetailLoading,
  }: any = useRequest(
    (id) => {
      return cloudAccountDetailById({ id });
    },
    {
      manual: true,
      formatResult: (r: any) => {
        return r.content || {};
      },
    },
  );

  const onClickCloseDrawerForm = (): void => {
    setAccountModalVisible(false);
  };

  useEffect((): void => {
    if (accountModalVisible) {
      if (accountModalInfo?.platform) {
        requestResourceTypeList(accountModalInfo?.platform);
      }
      if (accountModalInfo?.id) {
        requestCloudAccountDetailById(accountModalInfo.id);
      }
    }
  }, [accountModalVisible, accountModalInfo]);

  return (
    <Modal
      title={<img src={I_KNOW} alt="I_KNOW" className={styles['iKnow']} />}
      width={600}
      destroyOnClose
      open={accountModalVisible}
      closable={true}
      onOk={onClickCloseDrawerForm}
      onCancel={onClickCloseDrawerForm}
      loading={resourceTypeListLoading || cloudAccountDetailLoading}
      styles={{
        body: {
          minHeight: JSON_EDITOR_LIST?.includes(accountInfo?.platform)
            ? 384
            : 416,
          paddingLeft: 36,
          paddingRight: 36,
        },
      }}
      footer={
        <Button
          type={'primary'}
          onClick={onClickCloseDrawerForm}
          style={{ padding: '6px 52px' }}
        >
          {intl.formatMessage({
            id: 'cloudAccount.extend.title.iKnow',
          })}
        </Button>
      }
      wrapClassName={styles['accountModal']}
    >
      <Divider
        style={{
          margin: '18px 0 14px 0',
          borderColor: '#457aff',
          opacity: 0.25,
        }}
      />
      <ConfigProvider
        theme={{
          components: {
            Form: {
              itemMarginBottom: 8,
              labelColor: 'rgba(131, 131, 131, 1)',
              labelColonMarginInlineEnd: 24,
            },
          },
        }}
      >
        <Form labelCol={{ span: 6 }} wrapperCol={{ span: 18 }}>
          <Form.Item
            label={intl.formatMessage({
              id: 'cloudAccount.extend.title.account.id',
            })}
          >
            <Text style={{ color: '#457aff', fontWeight: 'bold' }}>
              {accountInfo?.cloudAccountId || '-'}
            </Text>
          </Form.Item>

          <Form.Item
            label={intl.formatMessage({
              id: 'cloudAccount.extend.title.account.alias',
            })}
          >
            <Text copyable style={{ color: 'rgba(74, 74, 74, 1)' }}>
              {accountInfo?.alias || '-'}
            </Text>
          </Form.Item>

          {!JSON_EDITOR_LIST?.includes(accountInfo?.platform) && (
            <Form.Item label={'AK'}>
              <Text style={{ color: 'rgba(74, 74, 74, 1)' }}>
                {accountInfo?.ak || '-'}
              </Text>
            </Form.Item>
          )}

          <Form.Item
            label={intl.formatMessage({
              id: 'cloudAccount.extend.title.cloud.platform',
            })}
          >
            <Flex>
              <span style={{ marginRight: 12 }}>
                {obtainPlatformIcon(accountInfo?.platform, platformList) || '-'}
              </span>
            </Flex>
          </Form.Item>

          <Row className={styles['basicInfoRow']}>
            <Col span={12} className={styles['basicLeftRow']}>
              <Row style={{ height: 44 }}>
                <span className={styles['leftInfoLabel']}>
                  {intl.formatMessage({
                    id: 'cloudAccount.extend.title.asset.type',
                  })}
                  &nbsp;:
                </span>
                <Disposition
                  style={{ paddingTop: 11, color: '#457aff' }}
                  text={
                    accountInfo?.resourceTypeListForWeb
                      ?.map((item: any[]) => {
                        return obtainResourceTypeTextFromValue(
                          resourceTypeList,
                          item,
                        );
                      })
                      ?.toString() || '-'
                  }
                  rows={1}
                  maxWidth={100}
                />
              </Row>
              <Row style={{ height: 44 }}>
                <span className={styles['leftInfoLabel']}>
                  {intl.formatMessage({
                    id: 'common.select.label.tenant',
                  })}
                  &nbsp;:
                </span>
                <Disposition
                  style={{ paddingTop: 11, color: '#457aff' }}
                  text={
                    tenantListAll?.find(
                      (item: IValueType): boolean =>
                        item.value === accountInfo?.tenantId,
                    )?.label || '-'
                  }
                  rows={1}
                  maxWidth={100}
                />
              </Row>
            </Col>
            <Col span={12}>
              <Row style={{ height: 44 }}>
                <span className={styles['rightInfoLabel']}>
                  {intl.formatMessage({
                    id: 'cloudAccount.extend.title.asset.number',
                  })}
                  &nbsp;:
                </span>
                <span
                  className={styles['basicInfoValue']}
                  style={{ color: '#457aff' }}
                >
                  {accountInfo?.resourceCount || 0}
                </span>
              </Row>
              <Row>
                <span
                  className={styles['rightInfoLabel']}
                  style={{ height: 44 }}
                >
                  {JSON_EDITOR_LIST?.includes(accountInfo?.platform)
                    ? `GCP KEY ${intl.formatMessage({
                        id: 'common.link.text.status',
                      })} : `
                    : `AK/SK ${intl.formatMessage({
                        id: 'common.link.text.status',
                      })} : `}
                </span>
                <Flex align={'center'}>
                  <ConditionTag state={accountInfo?.status} />
                </Flex>
              </Row>
            </Col>
          </Row>

          <Form.Item
            label={intl.formatMessage({
              id: 'cloudAccount.extend.title.lastScanTime',
            })}
          >
            <Text style={{ color: 'rgba(74, 74, 74, 1)' }}>
              {accountInfo?.lastScanTime || ' -'}
            </Text>
          </Form.Item>
          <Form.Item
            label={intl.formatMessage({
              id: 'cloudAccount.extend.title.createTime',
            })}
          >
            <Text style={{ color: 'rgba(74, 74, 74, 1)' }}>
              {accountInfo?.gmtCreate || '-'}
            </Text>
          </Form.Item>
          <Form.Item
            label={intl.formatMessage({
              id: 'cloudAccount.extend.title.updateTime',
            })}
          >
            <Text style={{ color: 'rgba(74, 74, 74, 1)' }}>
              {accountInfo?.gmtModified || '-'}
            </Text>
          </Form.Item>
        </Form>
      </ConfigProvider>
      <Divider
        style={{
          margin: '18px 0 0 0',
          borderColor: '#457aff',
          opacity: 0.25,
        }}
      />
    </Modal>
  );
};

export default AccountModal;
