package ob_tencent_sg_3600002_262

import rego.v1

default risk := false

risk if {
    count(sg_with_all_open_rule1) != 0
}

security_id := input.SecurityGroup.SecurityGroupId
security_name := input.SecurityGroup.SecurityGroupName

sg_with_all_open_rule1 contains p if {
    some p in input.SecurityGroupPolicySet.Ingress
    p.Port == "ALL"
    p.Protocol != "icmp"
    p.CidrBlock == "0.0.0.0/0"
}
