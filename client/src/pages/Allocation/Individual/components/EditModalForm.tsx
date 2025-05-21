import { onClickToLogout } from '@/components/Layout/UserAction';
import { changeUserPassword } from '@/services/user/UserController';
import {
  ModalForm,
  ProFormDependency,
  ProFormText,
} from '@ant-design/pro-components';
import { useAccess, useIntl } from '@umijs/max';
import { Form, FormInstance, message } from 'antd';
import { isEmpty } from 'lodash';
import React, { Dispatch, SetStateAction } from 'react';

interface IEditFormProps {
  editFormVisible: boolean;
  setEditFormVisible: Dispatch<SetStateAction<boolean>>;
}

// User changes password
const EditModalForm: React.FC<IEditFormProps> = (props) => {
  const access = useAccess();
  // Message Instance
  const [messageApi, contextHolder] = message.useMessage();
  // Form Instance
  const [form] = Form.useForm<FormInstance>();
  // Intl API
  const intl = useIntl();
  // ModalVisible
  const { editFormVisible, setEditFormVisible } = props;

  const initForm = (): void => {
    setEditFormVisible(false);
    form.resetFields();
  };

  const onClickFishEditForm = async (formData: any): Promise<void> => {
    const { passwordAgo, passwordNow } = formData;
    const postBody = {
      oldPassword: passwordAgo,
      newPassword: passwordNow,
      userId: access.userId!,
    };
    const r = await changeUserPassword(postBody);
    if (r.msg === 'success') {
      initForm();
      messageApi.success(
        intl.formatMessage({
          id: 'individual.module.text.password.changed.successfully',
        }),
      );
      onClickToLogout(600);
    }
  };

  return (
    <>
      {contextHolder}
      <ModalForm
        title={intl.formatMessage({
          id: 'individual.module.text.change.password',
        })}
        width={520}
        form={form}
        modalProps={{
          destroyOnClose: true,
          onCancel: () => initForm(),
          styles: {
            body: {
              paddingTop: 12,
            },
          },
        }}
        labelCol={{ span: intl.locale === 'en-US' ? 7 : 4 }}
        wrapperCol={{ span: intl.locale === 'en-US' ? 17 : 20 }}
        layout={'horizontal'}
        open={editFormVisible}
        onFinish={onClickFishEditForm}
      >
        <ProFormText.Password
          name="passwordAgo"
          label={intl.formatMessage({
            id: 'individual.module.text.old.password',
          })}
          rules={[{ required: true }]}
        />

        <ProFormText.Password
          name="passwordNow"
          label={intl.formatMessage({
            id: 'individual.module.text.new.password',
          })}
          rules={[{ required: true }]}
        />

        <ProFormDependency name={['passwordNow']}>
          {({ passwordNow }) => {
            return (
              <ProFormText.Password
                name="passwordAck"
                label={intl.formatMessage({
                  id: 'individual.module.text.confirm.password',
                })}
                rules={[
                  {
                    required: true,
                    validator: (_, value: string): Promise<any> => {
                      if (isEmpty(value)) {
                        return Promise.reject(
                          intl.formatMessage({
                            id: 'individual.module.text.confirm.password.empty.tip',
                          }),
                        );
                      }
                      if (value !== passwordNow) {
                        return Promise.reject(
                          intl.formatMessage({
                            id: 'individual.module.text.confirm.password.inconsistent.tip',
                          }),
                        );
                      } else {
                        return Promise.resolve();
                      }
                    },
                  },
                ]}
              />
            );
          }}
        </ProFormDependency>
      </ModalForm>
    </>
  );
};

export default EditModalForm;
