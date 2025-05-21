import { useIntl } from '@umijs/max';
import type {
  GetProp,
  TableColumnsType,
  TableProps,
  TransferProps,
} from 'antd';
import { Table, Transfer } from 'antd';
import React from 'react';
type TransferItem = GetProp<TransferProps, 'dataSource'>[number];
type TableRowSelection<T extends object> = TableProps<T>['rowSelection'];

interface DataType {
  key: string;
  title?: string;
  platform?: string;
}

export interface TableTransferProps extends TransferProps<TransferItem> {
  dataSource: DataType[];
  leftColumns: TableColumnsType<DataType>;
  rightColumns: TableColumnsType<DataType>;
  loading: boolean;
}

// Customize Table Transfer
const TableTransfer: React.FC<TableTransferProps> = (props) => {
  // Component Props
  const { leftColumns, rightColumns, loading, ...restProps } = props;
  // Intl API
  const intl = useIntl();
  return (
    <Transfer
      style={{ width: '100%' }}
      {...restProps}
      titles={[
        intl.formatMessage({
          id: 'rule.module.text.to.be.selected',
        }),
        intl.formatMessage({
          id: 'rule.module.text.selected',
        }),
      ]}
    >
      {({
        direction,
        filteredItems,
        onItemSelect,
        onItemSelectAll,
        selectedKeys: listSelectedKeys,
        disabled: listDisabled,
      }) => {
        const columns = direction === 'left' ? leftColumns : rightColumns;
        const rowSelection: TableRowSelection<TransferItem> = {
          getCheckboxProps: () => ({ disabled: listDisabled }),
          onChange(selectedRowKeys) {
            onItemSelectAll(selectedRowKeys, 'replace');
          },
          selectedRowKeys: listSelectedKeys,
          selections: [
            Table.SELECTION_ALL,
            Table.SELECTION_INVERT,
            Table.SELECTION_NONE,
          ],
        };

        return (
          <Table
            key={'id'}
            loading={loading}
            rowSelection={rowSelection}
            columns={columns}
            dataSource={filteredItems}
            size="small"
            style={{ pointerEvents: listDisabled ? 'none' : undefined }}
            onRow={({ key, disabled: itemDisabled }) => ({
              onClick: (): void => {
                if (itemDisabled || listDisabled) {
                  return;
                }
                onItemSelect(key, !listSelectedKeys.includes(key));
              },
            })}
          />
        );
      }}
    </Transfer>
  );
};

export default TableTransfer;
