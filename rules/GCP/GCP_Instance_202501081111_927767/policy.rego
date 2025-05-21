package vpc_firewall_open_to_pub_4600002_139

import rego.v1

default risk := false

# firewall policy hava higher priority than policy rule
# ref: https://cloud.google.com/firewall/docs/firewall-policies-overview

# Case 1: allow public access in firewall policy
risk if {
	not startswith(input.Instance.name, "gke-")
	have_unrestricted_range_in_firewall_policys
	count(public_ips) > 0
}

# Case 2: allow public access in firewall rule
# should check there is no block public access rule in firewall policy
risk if {
	not startswith(input.Instance.name, "gke-")
	not block_puclic_access_in_firewall_policys
	have_unrestricted_range
	count(public_ips) > 0
}

# ==================================================
# check firewall policy
# ==================================================

# Case 1: Allow has a higher priority than deny.
have_unrestricted_range_in_firewall_policys if {
	some policy in input.EffectiveFirewalls[_].firewallPolicys

	# 1. get allowd/denied ranges
	allowd_ranges := get_allowd_ranges(policy.rules)
	denied_ranges := get_denied_ranges(policy.rules)

	# 2. check
	min(allowd_ranges) < min(denied_ranges)
}

# Case 2: Only allow rules.
have_unrestricted_range_in_firewall_policys if {
	some policy in input.EffectiveFirewalls[_].firewallPolicys

	# 1. get allowd/denied ranges
	allowd_ranges := get_allowd_ranges(policy.rules)
	denied_ranges := get_denied_ranges(policy.rules)

	# 2. check
	count(allowd_ranges) > 0
	count(denied_ranges) == 0
}

# ==================================================
# check firewall policy (prepare for rule check)
# ==================================================

# Case 1: Deny has a higher priority than allow.
block_puclic_access_in_firewall_policys if {
	some policy in input.EffectiveFirewalls[_].firewallPolicys

	# 1. get allowd/denied ranges
	allowd_ranges := get_allowd_ranges(policy.rules)
	denied_ranges := get_denied_ranges(policy.rules)

	# 2. check
	min(denied_ranges) < min(allowd_ranges)
}

# Case 2: Only deny rules.
block_puclic_access_in_firewall_policys if {
	some policy in input.EffectiveFirewalls[_].firewallPolicys

	# 1. get allowd/denied ranges
	allowd_ranges := get_allowd_ranges(policy.rules)
	denied_ranges := get_denied_ranges(policy.rules)

	# 2. check
	count(denied_ranges) > 0
	count(allowd_ranges) == 0
}

# ==================================================
# check firewall rule
# ==================================================

# Case 1: Allow has a higher priority than deny.
have_unrestricted_range if {
	# 1. get allowd/denied ranges
	allowd_ranges := get_allowd_ranges(input.EffectiveFirewalls[_].firewalls)
	denied_ranges := get_denied_ranges(input.EffectiveFirewalls[_].firewalls)

	# 2. check
	min(allowd_ranges) < min(denied_ranges)
}

# Case 2: Only allow rules.
have_unrestricted_range if {
	# 1. get allowd/denied ranges
	allowd_ranges := get_allowd_ranges(input.EffectiveFirewalls[_].firewalls)
	denied_ranges := get_denied_ranges(input.EffectiveFirewalls[_].firewalls)

	# 2. check
	count(allowd_ranges) > 0
	count(denied_ranges) == 0
}

# ==================================================

public_ips contains ip if {
	some interface in input.Instance.networkInterfaces
	some access_config in interface.accessConfigs
	upper(access_config.type) == "ONE_TO_ONE_NAT"
	count(access_config.natIP) > 0
	ip := access_config.natIP
}

unrestricted_ranges := {"0.0.0.0/0", "::/0"}
all_ports := {"0-65535", "all"}

# ==================================================
# helper functions
# ==================================================

# rule.kind == firewallPolicyRule/firewall
# ports is all OR ip_protocol is all

get_allowd_ranges(rules) := allowd_ranges if {
	allowd_ranges := [priority |
		some rule in rules
		rule.kind == "compute#firewallPolicyRule"
		upper(rule.direction) == "INGRESS"
		rule.action == "allow"
		some range in rule.match.srcIpRanges
		range in unrestricted_ranges
		some config in rule.match.layer4Configs
		config.ipProtocol == "all"
		priority := rule.priority
	]
} else := allowd_ranges if {
	allowd_ranges := [priority |
		some rule in rules
		rule.kind == "compute#firewallPolicyRule"
		upper(rule.direction) == "INGRESS"
		rule.action == "allow"
		some range in rule.match.srcIpRanges
		range in unrestricted_ranges
		some config in rule.match.layer4Configs
		all_ports[config.ports[_]]
		priority := rule.priority
	]
} else := allowd_ranges if {
	allowd_ranges := [priority |
		some rule in rules
		rule.kind == "compute#firewall"
		upper(rule.direction) == "INGRESS"
		rule.allowed
		some range in rule.sourceRanges
		range in unrestricted_ranges
		some allow in rule.allowed
		allow.IPProtocol == "all"
		priority := rule.priority
	]
} else := allowd_ranges if {
	allowd_ranges := [priority |
		some rule in rules
		rule.kind == "compute#firewall"
		upper(rule.direction) == "INGRESS"
		rule.allowed
		some range in rule.sourceRanges
		range in unrestricted_ranges
		some allow in rule.allowed
		all_ports[allow.ports[_]]
		priority := rule.priority
	]
}

get_denied_ranges(rules) := denied_ranges if {
	denied_ranges := [priority |
		some rule in rules
		rule.kind == "compute#firewallPolicyRule"
		upper(rule.direction) == "INGRESS"
		rule.action == "deny"
		some range in rule.match.srcIpRanges
		range in unrestricted_ranges
		some config in rule.match.layer4Configs
		config.ipProtocol == "all"
		priority := rule.priority
	]
} else := denied_ranges if {
	denied_ranges := [priority |
		some rule in rules
		rule.kind == "compute#firewallPolicyRule"
		upper(rule.direction) == "INGRESS"
		rule.action == "deny"
		some range in rule.match.srcIpRanges
		range in unrestricted_ranges
		some config in rule.match.layer4Configs
		all_ports[config.ports[_]]
		priority := rule.priority
	]
} else := denied_ranges if {
	denied_ranges := [priority |
		some rule in rules
		rule.kind == "compute#firewall"
		upper(rule.direction) == "INGRESS"
		rule.denied
		some range in rule.sourceRanges
		range in unrestricted_ranges
		some allow in rule.allowed
		allow.IPProtocol == "all"
		priority := rule.priority
	]
} else := denied_ranges if {
	denied_ranges := [priority |
		some rule in rules
		rule.kind == "compute#firewall"
		upper(rule.direction) == "INGRESS"
		rule.denied
		some range in rule.sourceRanges
		range in unrestricted_ranges
		some allow in rule.allowed
		all_ports[allow.ports[_]]
		priority := rule.priority
	]
}

# ==================================================

msg_to_user contains info if {
	some public_ip in public_ips
	info := sprintf("IP %v expose to Internet", [public_ip])
}
