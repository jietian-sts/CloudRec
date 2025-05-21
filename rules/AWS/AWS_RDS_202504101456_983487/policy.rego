package cloudrec_8100003_287
import rego.v1

default risk := false
risk if {
    isPubliclyAccessible
    count(misconfigrations) > 0
}
messages contains message if {
    risk == true
    message := {
        "Description": "集群启用公网地址，同时安全组规则中配置了 0.0.0.0/0",
        "Misconfigrations": misconfigrations
    }
}

isPubliclyAccessible if {
    input.DBInstance.PubliclyAccessible == true
}

misconfigrations contains ingress_rule if {
    some sg in input.VPCSecurityGroups
    some ip_permission in sg.IpPermissions
    ingress_rule := {
        "FromPort": ip_permission.FromPort,
        "ToPort": ip_permission.ToPort,
        "IpProtocol": ip_permission.IpProtocol,
        "IpRanges": ip_permission.IpRanges
    }
    isDBPortInRule(ingress_rule)
    isProtocolAllowsTCP(ingress_rule)
    isIpRangesAllowAll(ingress_rule)
}

isDBPortInRule(rule) if {
    rule.FromPort != null
    rule.ToPort != null
    input.DBInstance.Endpoint.Port in numbers.range(rule.FromPort ,rule.ToPort)
}
isProtocolAllowsTCP(rule) if {
    rule.IpProtocol in ["tcp", "-1"]
}
isIpRangesAllowAll(rule) if {
    some ip_range in rule.IpRanges
    ip_range.CidrIp == "0.0.0.0/0"
}
