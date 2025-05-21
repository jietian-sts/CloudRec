import { JSONEditor } from '@/components/Editor';
import { saveGlobalVariableConfig } from '@/services/variable/GlobalVariableConfigCroller';
import { InfoCircleOutlined } from '@ant-design/icons';
import { ActionType, ModalForm, ProFormText } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Form, FormInstance, Tooltip, message } from 'antd';
import { isEmpty } from 'lodash';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';

interface IEditFormProps {
  editFormVisible: boolean;
  setEditFormVisible: Dispatch<SetStateAction<boolean>>;
  globalVariableConfigInfo: Record<string, any>;
  tableActionRef: React.RefObject<ActionType | undefined>;
}

// New | Edit Variable
const EditModalForm: React.FC<IEditFormProps> = (props) => {
  // Message API
  const [messageApi, contextHolder] = message.useMessage();
  // Form Instance
  const [form] = Form.useForm<FormInstance>();
  // Intl API
  const intl = useIntl();
  // Component Props
  const {
    editFormVisible,
    globalVariableConfigInfo,
    setEditFormVisible,
    tableActionRef,
  } = props;

  const [jsonValue, setJsonValue] = useState('{}');

  const handleJsonChange = (value: React.SetStateAction<string>) => {
    setJsonValue(value);
  };
  const onClickFishEditForm = async (formData: any) => {
    const d = { ...formData, data: jsonValue };
    const postBody = {
      ...d,
    };

    if (globalVariableConfigInfo.id) postBody.id = globalVariableConfigInfo.id;
    const res: API.Result_String_ = await saveGlobalVariableConfig(postBody);
    if (res.msg === 'success') {
      if (globalVariableConfigInfo.id) {
        messageApi.success(
          intl.formatMessage({ id: 'common.message.text.edit.success' }),
        );
      } else {
        messageApi.success(
          intl.formatMessage({ id: 'common.message.text.create.success' }),
        );
      }
      setEditFormVisible(false);
      tableActionRef.current?.reloadAndRest?.();
    }
  };

  useEffect((): void => {
    if (
      editFormVisible &&
      !isEmpty(globalVariableConfigInfo) &&
      globalVariableConfigInfo.id
    ) {
      const { ...reset } = globalVariableConfigInfo;
      form.setFieldsValue({
        ...reset,
      });

      setJsonValue(globalVariableConfigInfo.data);
    }
  }, [editFormVisible, globalVariableConfigInfo]);

  const initForm = (): void => {
    form.resetFields();
  };

  const onCancel = (): void => {
    setEditFormVisible(false);
    initForm();
  };

  return (
    <>
      {contextHolder}
      <ModalForm
        labelCol={{
          span: intl.locale === 'en-US' ? 5 : 4,
        }}
        wrapperCol={{
          span: intl.locale === 'en-US' ? 19 : 20,
        }}
        title={
          <>
            <span style={{ marginRight: 4 }}>
              {globalVariableConfigInfo.id
                ? intl.formatMessage({
                    id: 'variable.extend.title.edit',
                  })
                : intl.formatMessage({
                    id: 'variable.extend.title.add',
                  })}
            </span>
          </>
        }
        width={640}
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
        open={editFormVisible}
        onFinish={onClickFishEditForm}
        layout={'horizontal'}
      >
        <ProFormText
          name="name"
          label={intl.formatMessage({
            id: 'rule.module.text.variable.name',
          })}
          rules={[{ required: true }]}
          placeholder={intl.formatMessage({
            id: 'variable.input.name.text.placeholder',
          })}
        />
        <ProFormText
          disabled={!!globalVariableConfigInfo.id}
          name="path"
          label={intl.formatMessage({
            id: 'rule.module.text.variable.path',
          })}
          rules={[{ required: true }]}
          placeholder={intl.formatMessage({
            id: 'variable.input.path.text.placeholder',
          })}
          fieldProps={{
            suffix: !!globalVariableConfigInfo.id && ( // Disabled status prompt message
              <Tooltip
                title={intl.formatMessage({
                  id: 'variable.input.path.suffix.tip',
                })}
              >
                <InfoCircleOutlined style={{ color: 'rgba(0, 0, 0, .45)' }} />
              </Tooltip>
            ),
          }}
        />
        <ProFormText
          name="data"
          label={intl.formatMessage({
            id: 'rule.module.text.variable.value',
          })}
          valuePropName="data"
        >
          <JSONEditor
            value={jsonValue}
            onChange={handleJsonChange}
            editorStyle={{ height: '420px' }}
            editorKey="inputEditor"
          />
        </ProFormText>
      </ModalForm>
    </>
  );
};

export default EditModalForm;
