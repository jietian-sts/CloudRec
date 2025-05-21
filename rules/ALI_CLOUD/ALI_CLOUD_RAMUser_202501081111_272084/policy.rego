package ram_user_with_risk_access_3700001_195

import rego.v1
import data

default risk := false

risk if {
	count(using_high_risk_access) > 0
    not tmp_white
}

## 临时加白
tmp_white if {
    input.UserDetail.UserName in data.control_user_name_list.ram_user_name
}

high_risk_access_list := ["AliyunRAMFullAccess","AdministratorAccess"]
user_name := input.UserDetail.UserName
access_id := input.ActiveAccessKeys

using_high_risk_access contains p if {
    some policy in input.Policies
    p := policy.Policy.PolicyName
    p in high_risk_access_list
} 


