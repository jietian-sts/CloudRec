package cloudrec_7000001_191
import rego.v1
import data

default risk := false
risk if {
	has_public_address
	count(security_groups_misconfig) != 0
}
messages contains message if {
	risk == true
	message := {
		"Description": "SecurityGroup has a misconfiguration of ingress SourceIP",
		"Misconfig": security_groups_misconfig,
	}
}

public_ip_address := input.Instance.PublicIpAddress.IpAddress
has_public_address if {
    count(public_ip_address) > 0
}

security_groups_misconfig contains sg_rule if {
	sg_rule := input.SecurityGroups[_].Permissions[_]
	cidr := sg_rule.SourceCidrIp
    acl_ip_is_special_purpose_address(cidr) == false
	parts := split(cidr, "/")
	size := to_number(parts[1])
	size <= 8
	sg_rule.Direction == "ingress"
	sg_rule.Policy == "Accept"
}

acl_ip_is_special_purpose_address(acl_ip) := true if {
    count(net.cidr_contains_matches(data.special_purpose_cidr.cidr_list, acl_ip)) > 0
}else := false