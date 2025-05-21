import { JSONEditor } from '@/components/Editor';
import React from 'react';

interface IPermissionExpandRow {
  record: API.IPolicy;
}

// Permission expand row
const PermissionExpandRow: React.FC<IPermissionExpandRow> = (props) => {
  // Component Props
  const { record } = props;

  return (
    <div>
      {record.policyDocument && (
        <JSONEditor
          editorKey="POLICY_DOCUMENT_CONFIG_INSTANCE"
          value={
            JSON.stringify(JSON.parse(record.policyDocument), null, 4) || ''
          }
          readOnly={true}
          editorStyle={{ height: 240 }}
        />
      )}
    </div>
  );
};

export default PermissionExpandRow;
