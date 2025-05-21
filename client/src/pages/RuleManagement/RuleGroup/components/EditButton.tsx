import React from 'react';
import styles from '../index.less';

interface IEditButton {
  icon: string;
  callbackFunc?: (params: any) => void;
  isEdit?: boolean;
}

const EditButton: React.FC<IEditButton> = (props) => {
  const { icon, callbackFunc, isEdit = false } = props;

  return (
    <div onClick={callbackFunc} className={styles['editButton']}>
      <div
        className={styles['editButtonMain']}
        style={isEdit ? { paddingLeft: 8 } : {}}
      >
        <img src={icon} alt="EDIT_ICON" className={styles['editIcon']} />
      </div>
    </div>
  );
};
export default EditButton;
