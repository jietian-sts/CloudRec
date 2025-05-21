import * as GlobalVariableConfigController from '@/services/variable/GlobalVariableConfigCroller';
import * as InvolveController from './Involve/involveController';
import * as AccountController from './account/AccountController';
import * as AgentController from './agent/AgentController';
import * as AssetController from './asset/AssetController';
import * as HomeController from './home/homeController';
import * as PlatformController from './platform/PlatformController';
import * as RegoController from './rego/RegoController';
import * as ResourceController from './resource/ResourceController';
import * as RiskController from './risk/RiskController';
import * as RuleController from './rule/RuleController';
import * as TenantController from './tenant/TenantController';
import * as UserController from './user/UserController';

export const BASE_URL = process.env.NODE_ENV === 'production' ? '' : '/achieve';

export default {
  RuleController,
  PlatformController,
  ResourceController,
  UserController,
  TenantController,
  InvolveController,
  HomeController,
  AccountController,
  AgentController,
  AssetController,
  GlobalVariableConfigController,
  RegoController,
  RiskController,
};
