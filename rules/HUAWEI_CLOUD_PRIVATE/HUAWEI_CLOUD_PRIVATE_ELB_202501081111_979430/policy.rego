package cloudrec_2300001_284

import rego.v1

default risk := false

risk if {
	count(public_ip) > 0
	count(risk_ip_list_rules) > 0
}

instance_id := input.LoadBalancer.id

description := input.LoadBalancer.description

public_ip contains ip if {
	ip := input.LoadBalancer.publicips[_].publicip_address
}

risk_ip_list_rules contains ip_list_rule if {
	some ip_list_rule in input.IpGroupsDetails[_].IpGroups[_].ip_list
	ip_list_rule.ip == "0.0.0.0/0"
}
