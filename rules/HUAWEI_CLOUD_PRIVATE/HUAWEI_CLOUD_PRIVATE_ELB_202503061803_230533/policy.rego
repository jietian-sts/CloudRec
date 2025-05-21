package cloudrec_6800002_281

import rego.v1

default risk := false

risk if count(unsafe_ciphers_policies) > 0

# https://support.huaweicloud.com/usermanual-elb/elb_ug_jt_0022.html
# TLS 1.0, TLS 1.1 are unsafe TLS ciphers policies
unsafe_tls_ciphers_policies := {"tls-1-0", "tls-1-1"}

unsafe_ciphers_policies contains {"id": listener.id, "tls_ciphers_policy": listener.tls_ciphers_policy} if {
	some listener in input.ListenerDetails
	contains(listener.tls_ciphers_policy, unsafe_tls_ciphers_policies[_])
}

msg_to_user contains info if {
	some unsafe_ciphers_policy in unsafe_ciphers_policies
	info := sprintf("监听器 %v 使用了不安全的密码套件 %v", [unsafe_ciphers_policy.id, unsafe_ciphers_policy.tls_ciphers_policy])
}
