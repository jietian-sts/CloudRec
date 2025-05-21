import { IgnoreReasonTypeList } from '@/pages/RiskManagement/const';
import { ignoreRisk } from '@/services/risk/RiskController';
import {
  ActionType,
  ModalForm,
  ProFormSelect,
  ProFormTextArea,
} from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Form, FormInstance, message } from 'antd';
import { isEmpty } from 'lodash';
import React, { Dispatch, SetStateAction, useEffect } from 'react';

interface IIgnoreRiskFormProps {
  ignoreRiskFormVisible: boolean;
  setIgnoreRiskFormVisible: Dispatch<SetStateAction<boolean>>;
  riskInfo: Record<string, any>;
  tableActionRef: React.RefObject<ActionType | undefined>;
}

// Ignore risk
const IgnoreRiskForm: React.FC<IIgnoreRiskFormProps> = (props) => {
  // Message API
  const [messageApi, contextHolder] = message.useMessage();
  // Form Instance
  const [form] = Form.useForm<FormInstance>();
  // Intl API
  const intl = useIntl();
  // Component Props
  const {
    ignoreRiskFormVisible,
    riskInfo,
    setIgnoreRiskFormVisible,
    tableActionRef,
  } = props;

  const initForm = (): void => {
    form.resetFields();
  };

  const onCancel = (): void => {
    setIgnoreRiskFormVisible(false);
    initForm();
  };

  const onClickFishIgnoreRiskForm = async (formData: any) => {
    const postBody = {
      ...formData,
      riskId: riskInfo.id,
    };
    const r: API.Result_String_ = await ignoreRisk(postBody);
    if (r.code === 200 && r.msg === 'success') {
      messageApi.success(
        intl.formatMessage({ id: 'risk.message.api.ignore.risk.success' }),
      );
      tableActionRef?.current?.reload();
      onCancel();
    }
  };

  useEffect((): void => {
    if (ignoreRiskFormVisible && !isEmpty(riskInfo)) {
      form.setFieldsValue({
        // @ts-ignore
        ignoreReasonType: riskInfo?.ignoreReasonType,
        ignoreReason: riskInfo?.ignoreReason,
      });
    }
  }, [ignoreRiskFormVisible, riskInfo]);

  return (
    <>
      {contextHolder}
      <ModalForm
        labelCol={{
          span: intl.locale === 'en-US' ? 6 : 4,
        }}
        title={intl.formatMessage({
          id: 'risk.module.text.ignore.risk',
        })}
        width={520}
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
        open={ignoreRiskFormVisible}
        onFinish={onClickFishIgnoreRiskForm}
        layout={'horizontal'}
      >
        <ProFormSelect
          label={intl.formatMessage({
            id: 'risk.module.text.ignore.type',
          })}
          name={'ignoreReasonType'}
          rules={[{ required: true }]}
          options={IgnoreReasonTypeList as any}
        />

        <ProFormTextArea
          label={intl.formatMessage({
            id: 'risk.module.text.ignore.reason',
          })}
          name={'ignoreReason'}
          rules={[{ required: true }]}
          fieldProps={{
            rows: 6,
          }}
        />
      </ModalForm>
    </>
  );
};

export default IgnoreRiskForm;
