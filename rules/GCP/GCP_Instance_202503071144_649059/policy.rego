package vpc_firewall_open_any_port_to_pub_6900002_143
import rego.v1

default risk := false
risk if {
	count(have_unrestricted_range) > 0
	count(public_ips) > 0
}

# Case 1: Allow has a higher priority than deny.
have_unrestricted_range contains port if {
	port_range := numbers.range(1,65535)
	
	# 1. get allowd/denied ranges
    some port in port_range
	port_str := format_int(port, 10)
    allowd_ranges := get_allowd_ranges(input.EffectiveFirewalls[_].firewalls, port_str)
    denied_ranges := get_denied_ranges(input.EffectiveFirewalls[_].firewalls, port_str)

	# 2. check if denied_ranges had the lowest priority
	ranges := array.concat(allowd_ranges, denied_ranges)
	min_priority := min(ranges)
	not min_priority in denied_ranges
}

public_ips contains ip if {
	some interface in input.Instance.networkInterfaces
	some access_config in interface.accessConfigs
	upper(access_config.type) == "ONE_TO_ONE_NAT"
	count(access_config.natIP) > 0
	ip := access_config.natIP
}

unrestricted_ranges := {"0.0.0.0/0", "::/0"}

get_allowd_ranges(rules, port) := allowd_ranges if {
	allowd_ranges := [priority |
		some rule in rules
		upper(rule.direction) == "INGRESS"
		rule.allowed
        some protocol_and_port in rule.allowed
        protocol_and_port.IPProtocol != "icmp"
        port in protocol_and_port.ports
		some range in rule.sourceRanges
		range in unrestricted_ranges
		priority := rule.priority
	]
}

get_denied_ranges(rules, port) := denied_ranges if {
	denied_ranges := [priority |
		some rule in rules
		upper(rule.direction) == "INGRESS"
		rule.denied
        some protocol_and_port in rule.denied
        protocol_and_port.IPProtocol != "icmp"
        port in protocol_and_port.ports
		some range in rule.sourceRanges
		range in unrestricted_ranges
		priority := rule.priority
	]
}
