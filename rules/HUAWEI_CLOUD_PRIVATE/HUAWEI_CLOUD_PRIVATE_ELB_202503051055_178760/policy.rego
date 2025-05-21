package hws_elb_high_risk_port_public_open_rule_6700001_279

import rego.v1

default risk := false

risk if {
	count(public_ip) > 0
	count(unsafe_listeners) > 0
}

instance_id := input.LoadBalancer.id

description := input.LoadBalancer.description

public_ip contains input.LoadBalancer.publicips[_].publicip_address

unsafe_listeners contains {ip_groups.name: port} if {
	# 1. Check if the port opened by the listener is a high-risk port
	some ip_groups in input.IpGroupsDetails[_].IpGroups
	some port in data.servicePorts
	port.port == port_ip_list_map[ip_groups.id]

	# 2. Check high-risk port is open to internet
	some ip_list in ip_groups.ip_list
	ip_list.ip == "0.0.0.0/0"
}

port_ip_list_map[ip_list] := port if {
	some ListenerDetail in input.ListenerDetails
	port := ListenerDetail.protocol_port
	ip_list := ListenerDetail.ipgroup.ipgroup_id
}

msg contains info if {
	some k, v in unsafe_listeners[_]
	info := sprintf("监听器 %v 开放了高危端口: %v (%v) ", [k, v.port, v.service])
}

msg_to_user := concat("\n", msg)
