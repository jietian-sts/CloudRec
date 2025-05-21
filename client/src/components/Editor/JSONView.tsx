import ReactJsonView from 'react-json-view';

interface IJSONView {
  name?: string;
  value: Record<string, any>;
  viewerStyle?: Record<any, any>;
  displayDataTypes?: boolean;
}

const JSONView = (props: IJSONView) => {
  const {
    value,
    viewerStyle = {},
    displayDataTypes = false,
    name = 'output json',
  } = props;
  return (
    <ReactJsonView
      src={value}
      name={name}
      collapsed={false} // Whether to fold or not
      displayDataTypes={displayDataTypes} // Hide data types
      style={{
        height: 360,
        padding: 12,
        overflow: 'scroll',
        borderRadius: 4,
        background: '#FFF',
        ...viewerStyle,
      }}
    />
  );
};

export default JSONView;
