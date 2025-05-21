package ob_hws_ecs_3400002_241
import rego.v1

default risk := false

risk if {
	count(sg_with_ssh_rule1) != 0
}

risk if {
	count(sg_with_ssh_rule2) != 0
}


instance_id := input.SecurityGroup.id

sg_with_ssh_rule1 contains p if {
    some p in input.SecurityGroup.security_group_rules
    p.port_range_max >= 22
    p.port_range_min <= 22
    p.remote_ip_prefix == "0.0.0.0/0"
    p.protocol != "icmp"
    p.direction == "ingress"
}

sg_with_ssh_rule2 contains p if {
    some p in input.SecurityGroup.security_group_rules
    p.port_range_max >= 22
    p.port_range_min <= 22
    p.remote_ip_prefix == ""
    p.protocol != "icmp"
    p.direction == "ingress"
}
