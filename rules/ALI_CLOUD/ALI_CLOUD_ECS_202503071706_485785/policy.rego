package ecs_open_any_ports_to_pub_6900003_160
import rego.v1

default risk := false
risk if {
	count(have_unrestricted_ports) > 0
    has_public_address
}

public_ip_address := input.Instance.PublicIpAddress.IpAddress
has_public_address if {
    count(public_ip_address) > 0
}

have_unrestricted_ports contains port if {
	port_range := numbers.range(1,65535)
	
	# 1. get allowd/denied ranges
    some port in port_range
    allowd_ranges := get_allowd_ranges(input.SecurityGroups[_].Permissions, port)
    denied_ranges := get_denied_ranges(input.SecurityGroups[_].Permissions, port)

	# 2. check if denied_ranges had the lowest priority
	ranges := array.concat(allowd_ranges, denied_ranges)
	min_priority := min(ranges)
	not min_priority in denied_ranges
}

unrestricted_ranges := {"0.0.0.0/0", "::/0"}

get_allowd_ranges(permissions, port) := allowd_ranges if {
	allowd_ranges := [priority |
		some permission in permissions
		permission.Direction == "ingress"
		permission.Policy == "Accept"
		permission.IpProtocol != "ICMP"

		parts := split(permission.PortRange, "/")
		port_range := numbers.range(to_number(parts[0]),to_number(parts[1]))
        port in port_range

		permission.SourceCidrIp in unrestricted_ranges

		priority := permission.Priority
	]
}

get_denied_ranges(permissions, port) := denied_ranges if {
	denied_ranges := [priority |
		some permission in permissions
		permission.Direction == "ingress"
		permission.Policy == "Drop"
		permission.IpProtocol != "ICMP"

		parts := split(permission.PortRange, "/")
		port_range := numbers.range(to_number(parts[0]),to_number(parts[1]))
        port in port_range

		permission.SourceCidrIp in unrestricted_ranges

		priority := permission.Priority
	]
}