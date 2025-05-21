import { JSONEditor } from '@/components/Editor';
import {
  customTheme,
  imageURLMap,
  serializeFormData,
  unSerializeFormData,
} from '@/pages/AssetManagement/const';
import {
  queryDetailConfigList,
  queryResourceDetailById,
  saveDetailConfig,
} from '@/services/asset/AssetController';
import {
  ArrowLeftOutlined,
  EditOutlined,
  MinusCircleOutlined,
  PlusOutlined,
} from '@ant-design/icons';
import { PageContainer, ProCard } from '@ant-design/pro-components';
import { history, useIntl, useRequest } from '@umijs/max';
import {
  Button,
  Col,
  ConfigProvider,
  Flex,
  Form,
  Input,
  Row,
  Space,
  Switch,
  Typography,
  message,
} from 'antd';
import { isEmpty } from 'lodash';
import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'umi';
import styles from '../index.less';
const { Text } = Typography;

// Asset allocation
const ConfigAsset: React.FC = () => {
  const [searchParams] = useSearchParams();
  const id = searchParams.get('id');
  const view = searchParams.get('view') || false;
  // Form Instance
  const [form] = Form.useForm();
  // Message Instance
  const [messageApi, contextHolder] = message.useMessage();
  // Intl API
  const intl = useIntl();

  // Edit Form
  const [disableForm, setDisableForm] = useState<boolean>(true);

  // Resource Details
  const [assetConfigEditor, setAssetConfigEditor] = useState(``);

  // Query asset details configuration list
  const {
    data: assetConfigList,
    run: requestDetailConfigList,
    loading: assetDetailConfigLoading,
  }: any = useRequest(
    (values: Record<string, any>) => {
      return queryDetailConfigList(values);
    },
    {
      manual: true,
      formatResult: (r) => {
        const { content } = r;
        const data = unSerializeFormData(content as any) || {};
        form.setFieldsValue(data);
        return r?.content;
      },
    },
  );

  // Asset details data
  const {
    data: assetInfo,
    run: requestResourceDetailById,
    loading: assetDetailLoading,
  }: any = useRequest(
    (id: string) => {
      return queryResourceDetailById({ id });
    },
    {
      manual: true,
      formatResult: (r: any) => {
        const { content } = r;
        const instanceJSON: string = JSON.stringify(content?.instance, null, 4);
        if (!isEmpty(instanceJSON)) {
          setAssetConfigEditor(instanceJSON);
        }
        const configInfoBody = {
          resourceId: content?.resourceId,
          platform: content?.platform,
          resourceType: content?.resourceType,
        };
        requestDetailConfigList(configInfoBody);
        return r.content || {};
      },
    },
  );

  useEffect((): void => {
    if (id) requestResourceDetailById(id);
  }, [id]);

  // Edit
  const onClickEditForm = (): void => {
    setDisableForm(false);
  };

  // Cancel Editing
  const onClickCancelEditForm = (): void => {
    setDisableForm(true);
    const data = unSerializeFormData(assetConfigList as any) || {};
    form.setFieldsValue(data);
  };

  // Save
  const onClickSaveForm = (): void => {
    form.validateFields().then(async (values): Promise<void> => {
      const configBody = serializeFormData(values, assetInfo);
      const result = await saveDetailConfig(configBody as any);
      if (result?.msg === 'success') {
        messageApi.success(
          intl.formatMessage({ id: 'common.message.text.save.success' }),
        );
        setDisableForm(true);
        const configInfoBody = {
          resourceId: assetInfo?.resourceId,
          platform: assetInfo?.platform,
          resourceType: assetInfo?.resourceType,
        };
        await requestDetailConfigList(configInfoBody);
      }
    });
  };

  return (
    <PageContainer
      loading={assetDetailLoading}
      className={styles['assetConfigPageContainer']}
      breadcrumbRender={false}
      title={
        <Button type={'link'} size={'small'} onClick={() => history?.back()}>
          <ArrowLeftOutlined />
          {intl.formatMessage({
            id: 'common.button.text.return',
          })}
        </Button>
      }
    >
      {contextHolder}
      <Row style={{ height: '100%' }} gutter={[32, 0]}>
        <Col span={view ? 24 : 10} style={{ height: '100%' }}>
          <JSONEditor
            editorKey="assetConfigInstance"
            value={assetConfigEditor}
            readOnly={true}
            editorStyle={{ height: '100%' }}
          />
        </Col>
        <Col span={view ? 0 : 14}>
          <ConfigProvider theme={customTheme}>
            <ProCard
              loading={assetDetailConfigLoading}
              style={{ minHeight: '100%' }}
            >
              <Row>
                <Flex style={{ width: '100%' }} justify={'flex-end'}>
                  <Space size={'small'}>
                    <Button
                      type={'link'}
                      style={{ color: 'rgba(54, 110, 255, 1)' }}
                      href={
                        'https://cloudrec.yuque.com/org-wiki-cloudrec-iew3sz/hocvhx/va0a4s9gggpbnnmn'
                      }
                      target={'_blank'}
                    >
                      <img
                        src={imageURLMap['linkIcon']}
                        style={{ height: 14 }}
                        alt="LINK_ICON"
                      />
                      {intl.formatMessage({
                        id: 'rule.extend.text.config',
                      })}
                    </Button>
                    {disableForm ? (
                      <Button
                        style={{ padding: '4px 12px' }}
                        type={'primary'}
                        onClick={() => onClickEditForm()}
                      >
                        <EditOutlined />
                        {intl.formatMessage({
                          id: 'common.button.text.edit',
                        })}
                      </Button>
                    ) : (
                      <Button
                        type={'primary'}
                        onClick={() => onClickCancelEditForm()}
                      >
                        {intl.formatMessage({
                          id: 'common.button.text.cancel',
                        })}
                      </Button>
                    )}

                    <Button
                      disabled={disableForm}
                      type={'primary'}
                      onClick={() => onClickSaveForm()}
                    >
                      {intl.formatMessage({
                        id: 'common.button.text.save',
                      })}
                    </Button>
                  </Space>
                </Flex>
              </Row>
              <Form
                name="dynamic_form_nest_item"
                autoComplete="off"
                form={form}
                disabled={disableForm}
              >
                <Text style={{ marginBottom: 8, display: 'block' }}>
                  {intl.formatMessage({
                    id: 'cloudAccount.extend.title.basic.information',
                  })}
                  &nbsp;:&nbsp;
                </Text>
                <Form.List name="BASE_INFO">
                  {(fields, { add, remove }) => (
                    <>
                      {fields.map(({ key, name, ...restField }) => (
                        <Row key={key} gutter={[12, 0]}>
                          <Col span={4}>
                            <Form.Item
                              {...restField}
                              name={[name, 'name']}
                              rules={[{ required: true }]}
                            >
                              <Input placeholder="name" />
                            </Form.Item>
                          </Col>
                          <Col span={8}>
                            <Form.Item
                              {...restField}
                              name={[name, 'path']}
                              rules={[{ required: true }]}
                            >
                              <Input placeholder="path" />
                            </Form.Item>
                          </Col>
                          <Col>
                            <Form.Item
                              {...restField}
                              name={[name, 'status']}
                              rules={[{ required: false }]}
                              initialValue={true}
                            >
                              <Switch />
                            </Form.Item>
                          </Col>

                          {disableForm && (
                            <Col span={6}>
                              <Form.Item
                                {...restField}
                                name={[name, 'value']}
                                rules={[{ required: false }]}
                              >
                                <Input placeholder="value" />
                              </Form.Item>
                            </Col>
                          )}
                          {!disableForm && (
                            <Col style={{ paddingTop: 5 }}>
                              <MinusCircleOutlined
                                onClick={() => remove(name)}
                              />
                            </Col>
                          )}
                        </Row>
                      ))}
                      {!disableForm && (
                        <Form.Item>
                          <Button
                            type="dashed"
                            onClick={() => add()}
                            block
                            icon={<PlusOutlined />}
                          >
                            Add field
                          </Button>
                        </Form.Item>
                      )}
                    </>
                  )}
                </Form.List>

                <Text style={{ marginBottom: 8, display: 'block' }}>
                  {intl.formatMessage({
                    id: 'asset.module.text.net.information',
                  })}
                  &nbsp;:&nbsp;
                </Text>
                <Form.List name="NETWORK">
                  {(fields, { add, remove }) => (
                    <>
                      {fields.map(({ key, name, ...restField }) => (
                        <Row key={key} gutter={[12, 0]}>
                          <Col span={4}>
                            <Form.Item
                              {...restField}
                              name={[name, 'name']}
                              rules={[{ required: true }]}
                            >
                              <Input placeholder="name" />
                            </Form.Item>
                          </Col>
                          <Col span={8}>
                            <Form.Item
                              {...restField}
                              name={[name, 'path']}
                              rules={[{ required: true }]}
                            >
                              <Input placeholder="path" />
                            </Form.Item>
                          </Col>
                          <Col>
                            <Form.Item
                              {...restField}
                              name={[name, 'status']}
                              rules={[{ required: false }]}
                              initialValue={true}
                            >
                              <Switch />
                            </Form.Item>
                          </Col>
                          {disableForm && (
                            <Col span={6}>
                              <Form.Item
                                {...restField}
                                name={[name, 'value']}
                                rules={[{ required: false }]}
                              >
                                <Input placeholder="value" />
                              </Form.Item>
                            </Col>
                          )}
                          {!disableForm && (
                            <Col style={{ paddingTop: 5 }}>
                              <MinusCircleOutlined
                                onClick={() => remove(name)}
                              />
                            </Col>
                          )}
                        </Row>
                      ))}
                      {!disableForm && (
                        <Form.Item>
                          <Button
                            type="dashed"
                            onClick={() => add()}
                            block
                            icon={<PlusOutlined />}
                          >
                            Add field
                          </Button>
                        </Form.Item>
                      )}
                    </>
                  )}
                </Form.List>
              </Form>
            </ProCard>
          </ConfigProvider>
        </Col>
      </Row>
    </PageContainer>
  );
};

export default ConfigAsset;
