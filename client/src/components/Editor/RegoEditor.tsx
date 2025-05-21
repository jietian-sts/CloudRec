import * as monaco from 'monaco-editor';
import { useEffect, useRef } from 'react';

interface IRegoEditor {
  value?: string;
  onChange?: (value: string) => void;
  editorStyle?: Record<any, any>;
  editorKey: string;
  readOnly?: boolean;
}

const RegoEditor = (props: IRegoEditor) => {
  // Register for Rego language support
  monaco.languages.register({ id: 'rego' });

  const {
    value,
    onChange,
    editorStyle = {},
    editorKey,
    readOnly = false,
  } = props;

  const editorRef = useRef<any>();
  const editorInstance = useRef<any>();

  useEffect((): any => {
    if (!editorRef.current) return;
    if (!editorInstance.current) {
      editorInstance.current = monaco.editor.create(editorRef.current, {
        value: value,
        language: 'rego',
        theme: 'vs', // OR 'vs', 'hc-black' and so on
        readOnly: readOnly,
        automaticLayout: true, // Automatically adjust layout based on container size
      });
      editorInstance.current.onDidChangeModelContent((): void => {
        onChange?.(editorInstance.current.getValue());
      });
    }

    // Clean up function
    return (): void => {
      if (editorInstance.current) {
        editorInstance.current?.dispose();
        editorInstance.current = null;
      }
    };
  }, []);

  return (
    <div
      key={editorKey}
      ref={editorRef}
      style={{
        width: '100%',
        height: 760,
        borderRadius: 4,
        overflow: 'hidden',
        ...editorStyle,
      }}
    />
  );
};

export default RegoEditor;
