package cloudrec_8100005_254
import rego.v1

default risk := false
risk if {
    isPubliclyAccessible
    count(misconfigrations) > 0
}
messages contains message if {
    risk == true
    message := {
        "Description": "腾讯云 CDB实例开通外网访问，且安全组配置全网可访问",
        "Misconfigrations": misconfigrations
    }
}

isPubliclyAccessible if {
    input.InstanceInfo.WanStatus == 1
}

misconfigrations contains Info if {
    some sg in input.SecurityGroup
    some inbound in sg.Inbound
    Info := {
        "SecurityGroupName": sg.SecurityGroupName,
        "SecurityGroupRemark": sg.SecurityGroupRemark,
        "InboundRule": inbound
    }
    inbound.Action == "ACCEPT"
    isWanPortInPortRange(inbound)
    isProtocolAllowsTCP(inbound)
    isIpRangesAllowAll(inbound)
}

isWanPortInPortRange(inbound) := true if {
    ports := split(inbound.PortRange, ",")
    some port in ports
    input.InstanceInfo.WanPort == to_number(port)
} else := true if {
    inbound.PortRange == "ALL"
}

isProtocolAllowsTCP(inbound) if {
    inbound.IpProtocol in ["tcp", "ALL"]
}
isIpRangesAllowAll(inbound) if {
    inbound.CidrIp in ["0.0.0.0/0", "::/0"]
}
