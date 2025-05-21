package elb_sg_rule_open_to_all_public_1900001_286
import rego.v1

default risk := false

risk if {
    sg_rule_open_to_all
    pub_lb
}

## 基础信息
load_balancer_name := input.LoadBalancerName
dns_name := input.DNSName

## elb 网络类型
net_scheme := input.Scheme

## 安全组入向规则
sg_rules contains ip_permission if {
    some ip_permission in input.SecurityGroupDetail[_].IpPermissions
}

sg_rule_open_to_all if {
    some ip_ranges in sg_rules[_].IpRanges
    cidr_ip := ip_ranges.CidrIp
    cidr_ip == "0.0.0.0/0"
}
sg_rule_open_to_all if {
    ## 不存在安全组
    not input.SecurityGroups
}

## 公网lb
pub_lb if {
    net_scheme in ["internet-facing"]
}