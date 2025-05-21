package cloudrec_6600005_272

import rego.v1
import data

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

sg_with_all_open_rule contains {port.port: rule.id} if {
	some rule in input.SecurityGroup[_].security_group_rules
	some port in data.servicePorts
	port.port <= rule.port_range_max
	port.port >= rule.port_range_min
	rule.remote_ip_prefix in risk_remote_ip_prefix
	not rule.protocol in exclued_protocol
	rule.direction == "ingress"
}

msg contains formated_msg if {
	some k, v in sg_with_all_open_rule[_]
	some port in data.servicePorts
	k == port.port
	formated_msg := sprintf("安全组规则 %v 将高危端口 %v (%v) 开放到互联网", [v, k, port.service])
}

msg_to_user := concat("\n", msg)
