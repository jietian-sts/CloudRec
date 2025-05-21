package ob_tencent_clb_4800003_259
import rego.v1

default risk := false

risk if {
    input.LoadBalancer.LoadBalancerType == "OPEN"
	clb_without_sg
}

loadbalancer_id := input.LoadBalancer.LoadBalancerId

clb_without_sg if {
    count(input.SecureGroups) == 0
}