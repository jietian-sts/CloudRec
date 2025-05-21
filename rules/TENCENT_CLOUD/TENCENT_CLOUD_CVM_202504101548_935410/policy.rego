package cloudrec_8100004_258
import rego.v1

default risk = false
risk if {
    hasPublicIp
    port_22_is_allowed
}
messages contains message if {
    risk == true
    message := {
        "Description": "",
        "result": ""## ##,
    }
}

hasPublicIp if {
    count(input.Instance.PublicIpAddresses) > 0
}
port_22_is_allowed if {
    count(accept_22_index) > 0
    count(deny_22_index) == 0
}
port_22_is_allowed if {
    min(accept_22_index) < min(deny_22_index)
}

accept_22_index contains index if {
    some ingress in input.SecurityGroups[_].SecurityGroupPolicySet.Ingress
    ingress.Action == "ACCEPT"
    cidr_allowed(array.concat(split(ingress.CidrBlock,","), split(ingress.Ipv6CidrBlock,",")))
    protocol_allowed(ingress.Protocol)
    port_allowed(ingress.Port)
    index := ingress.PolicyIndex
}
deny_22_index contains index if {
    some ingress in input.SecurityGroups[_].SecurityGroupPolicySet.Ingress
    ingress.Action == "DENY"
    cidr_allowed(array.concat(split(ingress.CidrBlock,","), split(ingress.Ipv6CidrBlock,",")))
    protocol_allowed(ingress.Protocol)
    port_allowed(ingress.Port)
    index := ingress.PolicyIndex
}

cidr_allowed(cidr) if {
    "0.0.0.0/0" in cidr
}else if {
    "::/0" in cidr
}
protocol_allowed(protocol) if {
    protocol in ["tcp", "ALL"]
}
port_allowed(ports) if {
    ports == "ALL"
}else if {
    "22" in split(ports, ",")
}
