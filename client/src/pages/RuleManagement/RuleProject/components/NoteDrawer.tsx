import { RegoEditor } from '@/components/Editor';
import { queryRegoList, saveRego } from '@/services/rego/RegoController';
import { ActionType, ProCard, ProTable } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Button, Drawer, message } from 'antd';
import { isEmpty } from 'lodash';
import React, { Dispatch, SetStateAction, useRef } from 'react';

interface INoteDrawer {
  noteDrawerVisible: boolean;
  setNoteDrawerVisible: Dispatch<SetStateAction<boolean>>;
  noteDrawerInfo: Record<string, any>;
  requestRuleDetailById: (id: number) => Promise<any>;
}

// Historical information
const NoteDrawer: React.FC<INoteDrawer> = (props) => {
  // Component Props
  const {
    noteDrawerVisible,
    setNoteDrawerVisible,
    noteDrawerInfo,
    requestRuleDetailById,
  } = props;
  // Message API
  const [messageApi, contextHolder] = message.useMessage();
  // Intl API
  const intl = useIntl();

  const initDrawer = (): void => {
    setNoteDrawerVisible(false);
  };

  // Close Drawer
  const onClickCloseDrawerForm = (): void => {
    initDrawer();
  };

  // Current Table Instance
  const tableActionRef = useRef<ActionType>();

  // Rollback
  const onClickRollBack = async (record: Record<string, any>): Promise<any> => {
    const postBody = {
      ruleId: noteDrawerInfo?.ruleId,
      isDraft: 0, // 1-draft, 0-formal
      ruleRego: record?.ruleRego,
    };
    const res: API.Result_T_ = await saveRego(postBody);
    if (res.code === 200 || res.msg === 'success') {
      messageApi.success(
        intl.formatMessage({
          id: 'rule.message.text.version.rollback.success',
        }),
      );
      await requestRuleDetailById(Number(noteDrawerInfo?.ruleId));
      onClickCloseDrawerForm();
    }
  };

  const columns: Array<any> = [
    {
      title: intl.formatMessage({
        id: 'rule.module.text.version',
      }),
      dataIndex: 'version',
      align: 'center',
      render: (_: string): string => 'v' + _,
    },
    {
      title: intl.formatMessage({
        id: 'rule.module.text.user.name',
      }),
      dataIndex: 'userName',
      align: 'center',
    },
    {
      title: intl.formatMessage({
        id: 'common.table.columns.rule.updateTime',
      }),
      dataIndex: 'gmtModified',
      align: 'center',
    },
    {
      title: intl.formatMessage({
        id: 'cloudAccount.extend.title.cloud.operate',
      }),
      dataIndex: 'operate',
      align: 'center',
      valueType: 'option',
      render: (_: any, record: { [key: string]: any }) => (
        <Button type={'link'} onClick={() => onClickRollBack(record)}>
          {intl.formatMessage({
            id: 'common.button.text.rollback',
          })}
        </Button>
      ),
    },
  ];

  const requestTableList = async (
    params: Record<string, any>,
  ): Promise<any> => {
    // Currently, the parameters support pagination
    const normalizeParam: Record<string, any> = {
      page: params.current,
      size: params.pageSize,
      ruleId: noteDrawerInfo?.ruleId,
    };
    const msg = await queryRegoList({ ...normalizeParam });
    return {
      data: msg?.content?.data || [],
      // Please return true for success, otherwise the table will stop parsing data, even if there is data present
      success: msg?.code === 200 || false,
      // Not transmitting will use the length of the data. If it is pagination, it must be transmitted
      total: msg?.content?.total || 0,
    };
  };

  return (
    <Drawer
      title={intl.formatMessage({
        id: 'rule.extend.text.historical.records',
      })}
      width={'52%'}
      destroyOnClose
      open={noteDrawerVisible}
      onClose={onClickCloseDrawerForm}
    >
      {contextHolder}
      <ProTable
        search={false}
        scroll={{ x: true }}
        options={false}
        rowKey={'id'}
        columns={columns}
        actionRef={tableActionRef}
        request={requestTableList}
        pagination={{
          showQuickJumper: false,
          showSizeChanger: true,
          defaultPageSize: 10,
          defaultCurrent: 1,
        }}
        expandable={{
          expandRowByClick: true,
          expandedRowRender: (record: { [key: string]: any }) => (
            <ProCard direction="column" gutter={[0, 16]}>
              {!isEmpty(record.ruleRego) && (
                <ProCard ghost>
                  <p>
                    {intl.formatMessage({
                      id: 'rule.module.text.info',
                    })}
                  </p>
                  <RegoEditor
                    editorKey="regoMsgEditor"
                    value={record.ruleRego}
                    editorStyle={{ height: '280px', maxHeight: '280px' }}
                  />
                </ProCard>
              )}
            </ProCard>
          ),
          rowExpandable: (record: { [key: string]: any }) =>
            !isEmpty(record.ruleRego),
        }}
      />
    </Drawer>
  );
};

export default NoteDrawer;
