import { remarkAccessKey } from '@/services/user/UserController';
import {
  ActionType,
  ModalForm,
  ProFormTextArea,
} from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Form, FormInstance, message } from 'antd';
import React, { Dispatch, SetStateAction } from 'react';

interface IEditFormProps {
  editFormVisible: boolean;
  setEditFormVisible: Dispatch<SetStateAction<boolean>>;
  accessInfo: API.BaseAccessInfo;
  tableActionRef: React.RefObject<ActionType | undefined>;
}

// Add a note
const EditModalForm: React.FC<IEditFormProps> = (props) => {
  // Message Instance
  const [messageApi, contextHolder] = message.useMessage();
  // Form Instance
  const [form] = Form.useForm<FormInstance>();
  // Intl API
  const intl = useIntl();
  // Component Props
  const { editFormVisible, setEditFormVisible, accessInfo, tableActionRef } =
    props;

  const initForm = (): void => {
    setEditFormVisible(false);
    form.resetFields();
  };

  const onClickFishEditForm = async (formData: any): Promise<void> => {
    const { remark } = formData;
    const postBody = {
      id: accessInfo.id,
      remark: remark,
    };
    const r = await remarkAccessKey(postBody);
    if (r.msg === 'success') {
      messageApi.success(
        intl.formatMessage({ id: 'common.message.text.add.success' }),
      );
      initForm();
      tableActionRef?.current?.reloadAndRest?.();
    }
  };

  return (
    <>
      {contextHolder}
      <ModalForm
        title={intl.formatMessage({
          id: 'individual.table.columns.remark.information',
        })}
        width={460}
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
        labelCol={{ span: 4 }}
        wrapperCol={{ span: 20 }}
        layout={'horizontal'}
        open={editFormVisible}
        onFinish={onClickFishEditForm}
      >
        <ProFormTextArea
          name="remark"
          label={intl.formatMessage({
            id: 'individual.table.columns.text.remark',
          })}
          rules={[{ required: true }]}
          initialValue={accessInfo?.remark}
        />
      </ModalForm>
    </>
  );
};

export default EditModalForm;
