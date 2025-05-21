import { JSONEditor, JSONView, RegoEditor } from '@/components/Editor';
import { imageURLMap } from '@/pages/AssetManagement/const';
import NoteDrawer from '@/pages/RuleManagement/RuleProject/components/NoteDrawer';
import {
  evaluateRego,
  queryLatestById,
  saveRego,
} from '@/services/rego/RegoController';
import { PageContainer } from '@ant-design/pro-components';
import { useLocation } from '@umijs/max';
import {
  Button,
  Col,
  Flex,
  FloatButton,
  Row,
  Space,
  Typography,
  message,
} from 'antd';
import React, { useEffect, useRef, useState } from 'react';
import styles from './index.less';
const { Title } = Typography;

/**
 * Edit rule details (not yet used)
 * Note: Not yet used
 */

const Detail: React.FC = () => {
  // Get routing parameters
  const location = useLocation();
  const queryParameters = new URLSearchParams(location.search);
  const [id] = useState(queryParameters.get('id'));
  // Message API
  const [messageApi, contextHolder] = message.useMessage();
  // Query Loading
  const [loading, setLoading] = useState<boolean>(false);
  // Code Editor(Rego)
  const [codeEditor, setCodeEditor] = useState(``);
  // Lint
  // const [lintEditor] = useState<Record<string, any>>({});
  // Input
  const [inputEditor, setInputEditor] = useState(``);
  // Output
  const [outputEditor, setOutputEditor] = useState<Record<string, any>>({});
  // Is it a draft
  const [isDraft, setIsDraft] = useState<number>(1);
  // History
  const [noteDrawerVisible, setNoteDrawerVisible] = useState<boolean>(false);
  // Rule Information
  const noteDrawerInfo = useRef<Record<string, any>>({});

  const onRegoEditorChange = (value: string): void => {
    setCodeEditor(value);
  };

  const onInputEditorChange = (value: string): void => {
    setInputEditor(value);
  };

  // Query the latest version of data
  const requestLatestById = async (id: number): Promise<void> => {
    setLoading(true);
    const res: API.Result_T_ = await queryLatestById({
      ruleId: id,
    });
    setLoading(false);
    if (res.code === 200 || res.msg === 'success') {
      const { content } = res;
      setCodeEditor(content?.ruleRego || '');
      setIsDraft(content?.isDraft);
      // Processing JSON data echo formatting
      const inputJSON = content?.input ? JSON.parse(content?.input) : {};
      setInputEditor(JSON.stringify(inputJSON, null, 4) || '');
    }
  };

  useEffect((): void => {
    if (id) requestLatestById(Number(id));
  }, [id]);

  // Execute
  const onClickEvaluate = async (): Promise<void> => {
    const res: API.Result_T_ = await evaluateRego({
      ruleRego: codeEditor,
      input: inputEditor,
    });
    if (res.code === 200 || res.msg === 'success') {
      messageApi.success('执行成功');
      setOutputEditor(res?.content || {});
    } else if (res.code !== 200 && res.msg !== 'success') {
      // Analysis failed, display the reason for the failure
      setOutputEditor(res || {});
    }
  };

  // Save
  const onClickSave = async (): Promise<void> => {
    const postBody = {
      ruleId: Number(id),
      isDraft: 1, // 1-Draft, 0-Formal
      ruleRego: codeEditor,
    };
    const res: API.Result_T_ = await saveRego(postBody);
    if (res.code === 200 || res.msg === 'success') {
      await requestLatestById(Number(id));
      messageApi.success('保存成功');
    }
  };

  // Submit
  const onClickSubmit = async (): Promise<void> => {
    const postBody = {
      ruleId: Number(id),
      isDraft: 0, // 1-Draft, 0-Formal
      ruleRego: codeEditor,
    };
    const res: API.Result_T_ = await saveRego(postBody);
    if (res.code === 200 || res.msg === 'success') {
      await requestLatestById(Number(id));
      messageApi.success('提交成功');
    }
  };

  // History
  const onClickHistoryMenu = (): void => {
    noteDrawerInfo.current = {
      ruleId: id,
    };
    setNoteDrawerVisible(true);
  };

  return (
    <PageContainer
      ghost
      title={false}
      loading={loading}
      style={{ paddingInline: 12 }}
    >
      {contextHolder}
      <Row>
        <Flex justify={'end'} style={{ width: '100%' }}>
          <Space>
            <Button
              type={'link'}
              style={{ color: 'rgba(54, 110, 255, 1)' }}
              href={
                'https://cloudrec.yuque.com/org-wiki-cloudrec-iew3sz/hocvhx/hiw98ff7fzdn3exc'
              }
              target={'_blank'}
            >
              <img
                src={imageURLMap['linkIcon']}
                style={{ height: 14 }}
                alt="LINK_ICON"
              />
              配置说明
            </Button>
            <Button type={'primary'} onClick={() => onClickEvaluate()}>
              执行
            </Button>
            {isDraft === 1 && ( // Can only be saved in draft state
              <Button type={'primary'} onClick={() => onClickSave()}>
                保存
              </Button>
            )}
            <Button type={'primary'} onClick={() => onClickSubmit()}>
              提交
            </Button>
          </Space>
        </Flex>
      </Row>
      <Row gutter={20}>
        <Col md={14} span={24}>
          <Title level={5}>The Rego PlayGround</Title>
          <RegoEditor
            editorKey="regoEditor"
            value={codeEditor}
            onChange={onRegoEditorChange}
            editorStyle={{ height: '740px' }}
          />
          {/*<Title style={{ marginTop: 8 }} level={5}>*/}
          {/*  LINT*/}
          {/*</Title>*/}
          {/*<JSONView*/}
          {/*  viewerStyle={{ height: '280px' }}*/}
          {/*  value={lintEditor}*/}
          {/*  name={'lint Json'}*/}
          {/*/>*/}
        </Col>

        <Col md={10} span={24}>
          <Title level={5}>INPUT</Title>
          <JSONEditor
            editorStyle={{ height: '420px' }}
            editorKey="inputEditor"
            value={inputEditor}
            onChange={onInputEditorChange}
          />
          <Title style={{ marginTop: 8 }} level={5}>
            OUTPUT
          </Title>
          <JSONView viewerStyle={{ height: '280px' }} value={outputEditor} />
        </Col>
      </Row>
      <FloatButton.Group
        shape="square"
        style={{ insetInlineEnd: 2, top: 168, bottom: 32 }}
      >
        <FloatButton
          onClick={() => onClickHistoryMenu()}
          className={styles['floatButton']}
          icon={<div style={{ fontSize: 14 }}>历史</div>}
        />
        <FloatButton
          className={styles['floatButton']}
          icon={<div style={{ fontSize: 14 }}>属性</div>}
        />
      </FloatButton.Group>

      <NoteDrawer
        noteDrawerVisible={noteDrawerVisible}
        setNoteDrawerVisible={setNoteDrawerVisible}
        noteDrawerInfo={noteDrawerInfo.current}
        requestRuleDetailById={requestLatestById}
      />
    </PageContainer>
  );
};

export default Detail;
