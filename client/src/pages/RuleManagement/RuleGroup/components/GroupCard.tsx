import RULE_GROUP_DELETE_SVG from '@/assets/images/RULE_GROUP_DELETE_SVG.svg';
import RULE_GROUP_EDIT_SVG from '@/assets/images/RULE_GROUP_EDIT_SVG.svg';
import RULE_GROUP_EXECUTE_SVG from '@/assets/images/RULE_GROUP_EXECUTE_SVG.svg';
import RULE_GROUP_SITE from '@/assets/images/RULE_GROUP_SITE.png';
import Disposition from '@/components/Disposition';
import EditButton from '@/pages/RuleManagement/RuleGroup/components/EditButton';
import EditModalForm from '@/pages/RuleManagement/RuleGroup/components/EditModalForm';
import { delRuleGroup, scanByGroup } from '@/services/rule/RuleController';
import { useIntl } from '@umijs/max';
import { Button, ConfigProvider, Flex, Image, Popconfirm, message } from 'antd';
import { MessageType } from 'antd/es/message/interface';
import { useRef, useState } from 'react';
import styles from '../index.less';

interface IAccountCard {
  ruleGroup: API.RuleGroupInfo;
  requestInitData: () => Promise<void>;
  requestCurrentData: () => Promise<void>;
}

const GroupCard = (props: IAccountCard) => {
  // Component Props
  const { ruleGroup, requestInitData, requestCurrentData } = props;
  // RecordInfo
  const { id, groupName, username, ruleCount } = ruleGroup;
  // message API
  const [messageApi, contextHolder] = message.useMessage();
  // Intl API
  const intl = useIntl();
  // New | Edit Modal Form Visible
  const [editFormVisible, setEditFormVisible] = useState<boolean>(false);
  // Cloud account information
  const ruleGroupInfoRef = useRef<any>({});
  // ScanLoading
  const [scanLoading, setScanLoading] = useState<boolean>(false);

  // Delete selected rule group
  const onClickDelRuleGroup = async (id: number): Promise<void> => {
    const hide: MessageType = messageApi.loading(
      intl.formatMessage({ id: 'common.message.text.delete.loading' }),
    );
    const result: API.Result_String_ = await delRuleGroup({ id });
    hide();
    if (result.code === 200 || result.msg === 'success') {
      messageApi.success(
        intl.formatMessage({ id: 'common.message.text.delete.success' }),
      );
      await requestInitData();
    }
  };

  // ScanByRuleGroup
  const onClickScanByGroup = async (id: number): Promise<void> => {
    const hide: MessageType = messageApi.loading(
      intl.formatMessage({ id: 'common.message.text.execute.loading' }),
    );
    setScanLoading(true);
    const result: API.Result_String_ = await scanByGroup({ id });
    hide();
    setScanLoading(false);
    if (result.code === 200 || result.msg === 'success') {
      messageApi.success(
        intl.formatMessage({ id: 'common.message.text.execute.success' }),
      );
      await requestCurrentData();
    }
  };

  return (
    <>
      {contextHolder}
      <div className={styles['ruleGroupCard']}>
        <div className={styles['ruleGroupHead']}>
          <Flex align={'center'} style={{ paddingTop: 6 }}>
            <Image
              src={RULE_GROUP_SITE}
              width={22}
              height={22}
              preview={false}
            />
            <Disposition
              text={groupName || '-'}
              maxWidth={180}
              rows={1}
              style={{
                color: '#333',
                fontSize: 17,
                fontWeight: 500,
                marginLeft: 6,
              }}
              placement={'topLeft'}
            />
          </Flex>
          <Disposition
            text={`${intl.formatMessage({
              id: 'rule.input.text.rule.group.creator',
            })}: ${username}`}
            maxWidth={100}
            rows={1}
            style={{
              color: '#5585fe',
              fontSize: 13,
            }}
            placement={'topLeft'}
          />
        </div>
        <div className={styles['ruleGroupMain']}>
          <div className={styles['ruleGroupContent']}>
            <Flex align={'center'} style={{ height: '100%' }}>
              <span className={styles['ruleGroupContentLeft']}>
                <div className={styles['ruleGroupContentTitle']}>
                  {intl.formatMessage({
                    id: 'rule.input.text.rule.number',
                  })}
                </div>
                <Button
                  type={'link'}
                  className={styles['ruleGroupContentCount']}
                  href={`/ruleManagement/ruleProject?groupId=${id}`}
                >
                  {ruleCount}
                </Button>
              </span>
              <span className={styles['ruleGroupContentRight']}>
                <div className={styles['ruleGroupContentOperate']}>
                  <Popconfirm
                    title={intl.formatMessage({
                      id: 'common.button.text.delete.confirm',
                    })}
                    onConfirm={() => onClickDelRuleGroup(id!)}
                    okText={intl.formatMessage({
                      id: 'common.button.text.ok',
                    })}
                    cancelText={intl.formatMessage({
                      id: 'common.button.text.cancel',
                    })}
                  >
                    <EditButton icon={RULE_GROUP_DELETE_SVG} />
                    <Button
                      style={{ fontSize: 13, paddingBottom: 0 }}
                      type={'link'}
                      danger
                      size={'small'}
                      className={styles['ruleGroupOperateButton']}
                    >
                      {intl.formatMessage({
                        id: 'common.button.text.delete',
                      })}
                    </Button>
                  </Popconfirm>
                </div>

                <ConfigProvider
                  theme={{
                    token: {
                      colorLink: '#333',
                    },
                  }}
                >
                  <div className={styles['ruleGroupContentOperate']}>
                    <EditButton
                      icon={RULE_GROUP_EDIT_SVG}
                      callbackFunc={(): void => {
                        setEditFormVisible(true);
                        ruleGroupInfoRef.current = {
                          ...ruleGroup,
                        };
                      }}
                      isEdit={true}
                    />
                    <Button
                      onClick={(): void => {
                        setEditFormVisible(true);
                        ruleGroupInfoRef.current = {
                          ...ruleGroup,
                        };
                      }}
                      style={{ fontSize: 13, paddingBottom: 0 }}
                      type={'link'}
                      size={'small'}
                    >
                      {intl.formatMessage({
                        id: 'common.button.text.edit',
                      })}
                    </Button>
                  </div>

                  <div className={styles['ruleGroupContentOperate']}>
                    <EditButton
                      icon={RULE_GROUP_EXECUTE_SVG}
                      callbackFunc={() => onClickScanByGroup(id!)}
                    />
                    <Button
                      onClick={() => onClickScanByGroup(id!)}
                      loading={scanLoading}
                      style={{ fontSize: 13, paddingBottom: 0 }}
                      type={'link'}
                      size={'small'}
                    >
                      {intl.formatMessage({
                        id: 'common.button.text.test',
                      })}
                    </Button>
                  </div>
                </ConfigProvider>
              </span>
            </Flex>
          </div>
        </div>
      </div>

      <EditModalForm // Add | Edit Cloud Account
        editFormVisible={editFormVisible}
        setEditFormVisible={setEditFormVisible}
        groupInfo={ruleGroupInfoRef.current}
        requestCurrentData={requestCurrentData}
      />
    </>
  );
};
export default GroupCard;
