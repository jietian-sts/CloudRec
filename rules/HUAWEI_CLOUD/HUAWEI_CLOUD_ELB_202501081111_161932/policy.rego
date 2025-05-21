package ob_hws_elb_2700001_243

import rego.v1

default risk := false

risk if {
    count(security_policy) == 0
    # 确保有监听器存在
    count(input.ListenerDetails) > 0
}

vpc_id := input.LoadBalancer.vpc_id

security_policy contains p if {
    some p in input.ListenerDetails
    p.ipgroup != null
    p.ipgroup != ""
    p.ipgroup.enable_ipgroup == true
}