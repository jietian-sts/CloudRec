package aliyun_private_listener_with_unsafe_protocal_6500003_264
import rego.v1

default risk := false
risk if {
    count(listenerPortAndProtocal) > 0
}

unsafe_protocal := ["http", "tcp", "udp"]

listenerPortAndProtocal contains l if {
    some l in input.LoadBalancerAttribute.ListenerPortsAndProtocal.ListenerPortAndProtocal
    l.ListenerProtocal in unsafe_protocal
}