import { ACTION_TYPE_LIST_REALTIME } from '@/pages/PivotManagement/InvolveModule/utils/const';
import {
  ModalForm,
  ProFormDependency,
  ProFormSelect,
  ProFormTextArea,
} from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Button, Form, FormInstance } from 'antd';
import { isEmpty } from 'lodash';
import React, { Dispatch, SetStateAction, useEffect } from 'react';

interface IRealTimeFormProps {
  realtimeFormVisible: boolean;
  setRealtimeFormVisible: Dispatch<SetStateAction<boolean>>;
  formRef?: any;
  notificationInfo?: any;
}

// Real time notification of Modal Form pop-up window
const RealTimeModalForm: React.FC<IRealTimeFormProps> = (props) => {
  // Form Instance
  const [form] = Form.useForm<FormInstance>();
  // Intl API
  const intl = useIntl();
  // Component Props
  const {
    realtimeFormVisible,
    setRealtimeFormVisible,
    formRef,
    notificationInfo,
  } = props;

  const onClickFinishEditForm = async (
    formData: Record<string, any>,
  ): Promise<void> => {
    const actionList = formRef.current.getFieldValue('actionList');
    // Complete configuration of current row
    const recordRow = {
      ...notificationInfo,
      ...formData,
    };
    // Update the complete configuration of the current row in the table
    const actionTableList = actionList?.map((item: any) => {
      if (item.idx === notificationInfo.idx) {
        return recordRow;
      } else {
        return item;
      }
    });
    formRef.current.setFieldValue('actionList', actionTableList);
    setRealtimeFormVisible(false);
  };

  const initForm = (): void => {
    form.resetFields();
  };

  const onCancel = (): void => {
    setRealtimeFormVisible(false);
  };

  useEffect((): void => {
    if (realtimeFormVisible && !isEmpty(notificationInfo)) {
      form.setFieldsValue({
        ...notificationInfo,
      });
    } else if (!realtimeFormVisible) {
      initForm();
    }
  }, [realtimeFormVisible]);

  return (
    <ModalForm
      title={intl.formatMessage({
        id: 'involve.extend.title.real.time.notification',
      })}
      width={560}
      form={form}
      modalProps={{
        destroyOnClose: true,
        onCancel: () => onCancel(),
      }}
      layout={'horizontal'}
      open={realtimeFormVisible}
      onFinish={onClickFinishEditForm}
    >
      <ProFormSelect
        label={intl.formatMessage({
          id: 'involve.extend.title.subscription.type',
        })}
        name={'action'}
        options={ACTION_TYPE_LIST_REALTIME}
        rules={[{ required: true }]}
      />

      <ProFormDependency name={['action']}>
        {({ action }) => {
          // The callback type of the interface does not display the group chat name
          if (action === ACTION_TYPE_LIST_REALTIME[1]?.value) return <></>;
          return (
            <ProFormTextArea
              label={intl.formatMessage({
                id: 'involve.extend.title.group.chat.name',
              })}
              name={'name'}
              rules={[{ required: true }]}
            />
          );
        }}
      </ProFormDependency>

      <ProFormDependency name={['action']}>
        {({ action }) => {
          return (
            <ProFormTextArea
              label={
                action === ACTION_TYPE_LIST_REALTIME[1]?.value ? (
                  <div style={{ position: 'relative' }}>
                    {intl.formatMessage({
                      id: 'involve.extend.title.callback.url',
                    })}
                    <Button
                      type={'link'}
                      size={'small'}
                      style={{
                        padding: 0,
                        position: 'absolute',
                        left: 0,
                        top: 20,
                        fontSize: 12,
                      }}
                      href={
                        'https://cloudrec.yuque.com/org-wiki-cloudrec-iew3sz/hocvhx/rqvy5gapmz43g29p'
                      }
                      target={'_blank'}
                    >
                      {intl.formatMessage({
                        id: 'involve.extend.title.documentation',
                      })}
                    </Button>
                  </div>
                ) : (
                  <div style={{ position: 'relative' }}>
                    {intl.formatMessage({
                      id: 'involve.extend.title.to.be.notified.group',
                    })}
                    <Button
                      type={'link'}
                      size={'small'}
                      style={{
                        padding: 0,
                        position: 'absolute',
                        left: 0,
                        top: 20,
                        fontSize: 12,
                      }}
                      href={
                        'https://cloudrec.yuque.com/org-wiki-cloudrec-iew3sz/hocvhx/rqvy5gapmz43g29p'
                      }
                      target={'_blank'}
                    >
                      {intl.formatMessage({
                        id: 'involve.extend.title.documentation',
                      })}
                    </Button>
                  </div>
                )
              }
              name={'url'}
              rules={[
                {
                  required: true,
                  message:
                    action === ACTION_TYPE_LIST_REALTIME[1]?.value
                      ? intl.formatMessage({
                          id: 'involve.input.text.callback.url.tip',
                        })
                      : intl.formatMessage({
                          id: 'involve.input.text.notified.group.tip',
                        }),
                },
              ]}
            />
          );
        }}
      </ProFormDependency>
    </ModalForm>
  );
};

export default RealTimeModalForm;
