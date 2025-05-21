import Develop from '@/assets/images/DEVELOP.svg';
import { ProCard } from '@ant-design/pro-components';
import { Result } from 'antd';

export default () => {
  return (
    <ProCard>
      <Result
        icon={<img src={Develop} width={240} alt="ICON" />}
        extra="代码的种子已播种，新功能正在技术的土壤里静静发芽。"
      />
    </ProCard>
  );
};
