package ec2_sg_rule_open_to_all_public_1800001_288
import rego.v1

default risk := false

risk if {
    sg_rule_open_to_all
    public_ip != null
}

## ec2 基础信息
instance_id := input.InstanceId
## 公网ip
public_ip := input.PublicIpAddress

## 安全组入向规则
sg_rules contains ip_permission if {
    some ip_permission in input.SecurityGroupDetail[_].IpPermissions
}

sg_rule_open_to_all if {
    some ip_ranges in sg_rules[_].IpRanges
    cidr_ip := ip_ranges.CidrIp
    cidr_ip == "0.0.0.0/0"
}