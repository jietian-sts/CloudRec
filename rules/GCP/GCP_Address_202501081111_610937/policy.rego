package address_exposed_by_lb_4300001_147
import rego.v1

default risk := false
risk if {
	count(chains) > 0
}

unrestrictedRanges := ["*", "0.0.0.0/0"]

get_resource_id(url) := id if {
	parts := split(url, "/")
	id := parts[count(parts) - 1]
}

get_resource_type(url) := type if {
	parts := split(url, "/")
	type := parts[count(parts) - 2]
}

get_allowd_ranges(rules) := allowd_ranges if {
	allowd_ranges := [priority |
		some rule in rules
		rule.action == "allow"
		some srcIpRange in rule.match.config.srcIpRanges
		srcIpRange in unrestrictedRanges
		priority := rule.priority
	]
}

get_denied_ranges(rules) := denied_ranges if {
	denied_ranges := [priority |
		some rule in rules
		contains(lower(rule.action), "deny")
		some srcIpRange in rule.match.config.srcIpRanges
		srcIpRange in unrestrictedRanges
		priority := rule.priority
	]
}

#########################################################################################################
# 1. Application Load Balancers
# [Traffic] --> [Forwarding Rule] --> [Target HTTP/HTTPS proxy] --> [URL Map] --> [Backend Service]

#########################################################################################################
# 2. Proxy Network Load Balancers
# [Traffic] --> [Forwarding Rule] --> [Target TCP/SSL proxy] --> [Backend Service]

# Case 2.1: There are no SecurityPolicies here.
chains contains point if {
	some user in input.Address.users
	user_id := get_resource_id(user)

	some ForwardingRule in input.ForwardingRules
	ForwardingRule.name == user_id
	target_id := get_resource_id(ForwardingRule.target)

	some TargetTcpProxy in input.TargetTcpProxies
	TargetTcpProxy.name == target_id
	service_id := get_resource_id(TargetTcpProxy.service)

	some BackendService in input.BackendServices
	BackendService.name == service_id
	BackendService.securityPolicy == null

	point := sprintf(
		"IPAddress: %v, portRange: %v, IPProtocol: %v, Chain: %v --> %v --> %v",
		[
			ForwardingRule.IPAddress,
			ForwardingRule.portRange,
			ForwardingRule.IPProtocol,
			user_id,
			target_id,
			service_id,
		],
	)
}

# Case 2.2: Allow has a higher priority than deny.
chains contains point if {
	some user in input.Address.users
	user_id := get_resource_id(user)

	some ForwardingRule in input.ForwardingRules
	ForwardingRule.name == user_id
	target_id := get_resource_id(ForwardingRule.target)

	some TargetTcpProxy in input.TargetTcpProxies
	TargetTcpProxy.name == target_id
	service_id := get_resource_id(TargetTcpProxy.service)

	some BackendService in input.BackendServices
	BackendService.name == service_id
	security_policy_id := get_resource_id(BackendService.securityPolicy)

	some SecurityPolicy in input.SecurityPolicies
	SecurityPolicy.name == security_policy_id

	allowd_ranges := get_allowd_ranges(SecurityPolicy.rules)
	denied_ranges := get_denied_ranges(SecurityPolicy.rules)
	min(allowd_ranges) < min(denied_ranges)

	point := sprintf(
		"IPAddress: %v, portRange: %v, IPProtocol: %v, Chain: %v --> %v --> %v --> %v",
		[
			ForwardingRule.IPAddress,
			ForwardingRule.portRange,
			ForwardingRule.IPProtocol,
			security_policy_id,
			user_id,
			target_id,
			service_id,
		],
	)
}

# Case 2.3: Only allow rules.
chains contains point if {
	some user in input.Address.users
	user_id := get_resource_id(user)

	some ForwardingRule in input.ForwardingRules
	ForwardingRule.name == user_id
	target_id := get_resource_id(ForwardingRule.target)

	some TargetTcpProxy in input.TargetTcpProxies
	TargetTcpProxy.name == target_id
	service_id := get_resource_id(TargetTcpProxy.service)

	some BackendService in input.BackendServices
	BackendService.name == service_id
	security_policy_id := get_resource_id(BackendService.securityPolicy)

	some SecurityPolicy in input.SecurityPolicies
	SecurityPolicy.name == security_policy_id

	allowd_ranges := get_allowd_ranges(SecurityPolicy.rules)
	denied_ranges := get_denied_ranges(SecurityPolicy.rules)

	count(allowd_ranges) > 0
	count(denied_ranges) == 0

	point := sprintf(
		"IPAddress: %v, portRange: %v, IPProtocol: %v, Chain: %v --> %v --> %v --> %v",
		[
			ForwardingRule.IPAddress,
			ForwardingRule.portRange,
			ForwardingRule.IPProtocol,
			security_policy_id,
			user_id,
			target_id,
			service_id,
		],
	)
}

#########################################################################################################
# 3. Passthrough Network Load Balancers
# [Traffic] --> [Forwarding Rule] --> [Backend Service]
