package tencent_cloud_listener_with_unsafe_protocal_6500004_253
import rego.v1

default risk := false
risk if {
    count(listenerPortAndProtocal) > 0
}

unsafe_protocal := ["HTTP", "TCP", "UDP"]

listenerPortAndProtocal contains l if {
    some l in input.Listeners
    l.Protocol in unsafe_protocal
}