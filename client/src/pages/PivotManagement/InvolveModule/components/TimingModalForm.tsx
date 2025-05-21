import {
  ACTION_TYPE_LIST_TIMING,
  PERIOD_LIST_TIMING,
  TIME_LIST_TIMING,
} from '@/pages/PivotManagement/InvolveModule/utils/const';
import {
  ModalForm,
  ProFormSelect,
  ProFormTextArea,
} from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Button, Col, Form, FormInstance, Row } from 'antd';
import { isEmpty } from 'lodash';
import React, { Dispatch, SetStateAction, useEffect } from 'react';

interface ITimingFormProps {
  timingFormVisible: boolean;
  setTimingFormVisible: Dispatch<SetStateAction<boolean>>;
  formRef?: any;
  notificationInfo?: any;
}

// Timed notification ModalForm pop-up window
const TimingModalForm: React.FC<ITimingFormProps> = (props) => {
  // Form Instance
  const [form] = Form.useForm<FormInstance>();
  // Intl API
  const intl = useIntl();
  // Component Props
  const { timingFormVisible, setTimingFormVisible, formRef, notificationInfo } =
    props;

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
    setTimingFormVisible(false);
  };

  const initForm = (): void => {
    form.resetFields();
  };

  const onCancel = (): void => {
    setTimingFormVisible(false);
  };

  useEffect((): void => {
    if (timingFormVisible && !isEmpty(notificationInfo)) {
      form.setFieldsValue({
        ...notificationInfo,
      });
    } else if (!timingFormVisible) {
      initForm();
    }
  }, [timingFormVisible]);

  return (
    <ModalForm
      title={intl.formatMessage({
        id: 'involve.extend.title.scheduled.notification',
      })}
      width={560}
      form={form}
      modalProps={{
        destroyOnClose: true,
        onCancel: () => onCancel(),
      }}
      layout={'horizontal'}
      open={timingFormVisible}
      onFinish={onClickFinishEditForm}
    >
      <ProFormSelect
        label={intl.formatMessage({
          id: 'involve.extend.title.subscription.type',
        })}
        name={'action'}
        options={ACTION_TYPE_LIST_TIMING}
        rules={[{ required: true }]}
      />
      <ProFormTextArea
        label={intl.formatMessage({
          id: 'involve.extend.title.group.chat.name',
        })}
        name={'name'}
        rules={[{ required: true }]}
      />
      <ProFormTextArea
        label={
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
        }
        name={'url'}
        rules={[{ required: true }]}
      />
      <Row>
        <Col span={10}>
          <ProFormSelect
            label={intl.formatMessage({
              id: 'involve.extend.title.notification.time',
            })}
            name={'period'}
            options={PERIOD_LIST_TIMING}
            rules={[{ required: true }]}
          />
        </Col>
        <Col span={14}>
          <ProFormSelect
            label={''}
            name={'timeList'}
            options={TIME_LIST_TIMING}
            rules={[{ required: true }]}
            fieldProps={{
              mode: 'tags',
            }}
          />
        </Col>
      </Row>
    </ModalForm>
  );
};

export default TimingModalForm;
