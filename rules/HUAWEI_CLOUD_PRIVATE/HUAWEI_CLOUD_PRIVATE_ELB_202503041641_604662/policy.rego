package cloudrec_6600003_271

import rego.v1

default risk := false

risk if {
	count(msg) > 0
}

risk_protocal := ["TCP", "UDP", "HTTP"]

msg contains {name: listener.protocol} if {
	some listener in input.ListenerDetails
	listener.protocol in risk_protocal
	name := listener.name
}

msg_to_user contains sprintf("监听器 %v 使用了不安全的监听协议: %v", [k, v]) if {
	some k, v in msg[_]
}

msg_to_user2 := concat("\n", msg_to_user)
