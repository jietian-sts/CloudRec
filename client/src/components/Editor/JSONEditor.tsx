import * as monaco from 'monaco-editor';
import React, { useEffect, useRef } from 'react';

interface IJSONEditor {
  value?: string;
  editorKey: string;
  onChange?: (value: string) => void;
  editorStyle?: React.CSSProperties;
  readOnly?: boolean;
}

const JSONEditor = (props: IJSONEditor) => {
  // Register JSON support
  monaco.languages.register({ id: 'json' });

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
        language: 'json',
        theme: 'vs', // OR 'vs', 'hc-black' and so on
        readOnly: readOnly,
        folding: true, // Enable folding function
        automaticLayout: true, // Automatically adjust layout based on container size
      });
      // When the editor value is modified, it will be displayed back to the parent component state
      editorInstance.current.onDidChangeModelContent((): void => {
        onChange?.(editorInstance.current.getValue());
      });
    } else {
      editorInstance.current.setValue(value);
    }

    // Clean up function
    return (): void => {
      if (editorInstance?.current) {
        editorInstance?.current?.dispose();
        editorInstance.current = null;
      }
    };
  }, []);

  // Dealing with external value changes
  useEffect((): void => {
    if (editorInstance.current && value !== editorInstance.current.getValue()) {
      editorInstance.current.setValue(value);
    }
  }, [value]);

  return (
    <div
      key={editorKey}
      ref={editorRef}
      style={{
        height: 360,
        borderRadius: 4,
        overflow: 'hidden',
        ...editorStyle,
      }}
    />
  );
};

export default JSONEditor;
