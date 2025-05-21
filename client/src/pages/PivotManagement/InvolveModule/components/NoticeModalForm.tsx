import { CHECK_BOX_OPTIONS_LIST } from '@/pages/PivotManagement/InvolveModule/utils/const';
import { ModalForm } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Checkbox, Col, Form, FormInstance, Row } from 'antd';
import { isEmpty } from 'lodash';
import React, { Dispatch, SetStateAction } from 'react';

interface INoticeFormProps {
  noticeFormVisible: boolean;
  setNoticeFormVisible: Dispatch<SetStateAction<boolean>>;
  formRef?: any;
}

// Notify to configure Modal Form pop-up window
const NoticeModalForm: React.FC<INoticeFormProps> = (props) => {
  // Form Instance
  const [form] = Form.useForm<FormInstance>();
  // Component Props
  const { noticeFormVisible, setNoticeFormVisible, formRef } = props;
  // Intl API
  const intl = useIntl();

  const onClickFinishEditForm = async (
    formData: Record<string, any>,
  ): Promise<void> => {
    const actionList = formRef.current.getFieldValue('actionList');
    // Note that the selected option values are related to the order in which the options are selected
    const checkBoxList = formData?.actionList;
    if (isEmpty(checkBoxList)) {
      return setNoticeFormVisible(false);
    }
    // Attention: Update idx value when adding and deleting
    const actionTableList = checkBoxList?.map(
      (item: string, index: number) => ({
        idx: index + 1,
        actionType: item,
      }),
    );
    if (isEmpty(actionList)) {
      formRef.current.setFieldValue('actionList', actionTableList);
    } else {
      // Attention: Update idx value when adding and deleting
      const array = [...actionList, ...actionTableList]?.map(
        (item: Record<string, any>, index: number) => ({
          ...item,
          idx: index + 1,
        }),
      );
      formRef.current.setFieldValue('actionList', array);
    }
    setNoticeFormVisible(false);
  };

  const initForm = (): void => {
    form.resetFields();
  };

  const onCancel = (): void => {
    setNoticeFormVisible(false);
    initForm();
  };

  return (
    <ModalForm
      title={intl.formatMessage({
        id: 'involve.extend.title.notification.configuration',
      })}
      width={540}
      labelCol={{ span: 6 }}
      wrapperCol={{ span: 18 }}
      form={form}
      modalProps={{
        destroyOnClose: true,
        onCancel: () => onCancel(),
        styles: {
          body: {
            paddingTop: 12,
          },
        },
      }}
      layout={'horizontal'}
      open={noticeFormVisible}
      onFinish={onClickFinishEditForm}
    >
      <Form.Item name="actionList">
        <Checkbox.Group style={{ width: '100%' }}>
          <Row>
            {CHECK_BOX_OPTIONS_LIST.map((i) => {
              return (
                <Col key={i.value} span={24}>
                  <Checkbox value={i.value}>{i.label}</Checkbox>
                  <p style={{ fontSize: 12, marginLeft: 20, color: '#C0C0C0' }}>
                    ({i.desc})
                  </p>
                </Col>
              );
            })}
          </Row>
        </Checkbox.Group>
      </Form.Item>
    </ModalForm>
  );
};

export default NoticeModalForm;
