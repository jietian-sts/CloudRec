package cloudrec_8500003_269
import rego.v1

default risk := false
risk if {
	has_public_ip
	count(sg_with_all_open_rule) > 0
}

has_public_ip if {
	some addr in input.ServerDetail.addresses[_]
	# https://www.huaweicloud.com/zhishi/eip9.html
	addr["OS-EXT-IPS:type"] == "floating"
}

risk_remote_ip_prefix := ["0.0.0.0/0", "::/0"]

exclued_protocol := ["icmp"]

sg_with_all_open_rule contains rule if {
	some rule in input.SecurityGroup[_].security_group_rules
	rule.port_range_max == 0
	rule.port_range_min == 0
	rule.remote_ip_prefix in risk_remote_ip_prefix
	not rule.protocol in exclued_protocol
	rule.direction == "ingress"
}

sg_with_all_open_rule contains rule if {
	some rule in input.SecurityGroup[_].security_group_rules
	rule.port_range_max == 1
	rule.port_range_min == 65535
	rule.remote_ip_prefix in risk_remote_ip_prefix
	not rule.protocol in exclued_protocol
	rule.direction == "ingress"
}
