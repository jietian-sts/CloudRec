interface IMenuFooter {
  collapsed: boolean | undefined;
}

const MenuFooter = (props: IMenuFooter) => {
  const { collapsed } = props;
  if (collapsed) return <></>;
  return (
    <div
      style={{
        textAlign: 'center',
        paddingBlockStart: 20,
        color: 'rgba(51, 51, 51, 0.6)',
      }}
    >
      © {new Date().getFullYear()} Made with CloudRec
    </div>
  );
};
export default MenuFooter;
