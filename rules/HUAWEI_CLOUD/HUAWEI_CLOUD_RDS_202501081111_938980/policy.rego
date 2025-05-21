package ob_hws_rds_3300003_235

import rego.v1

default risk := false

risk if {
    enable_ssl
}

vpc_id := input.LoadBalancer.vpc_id

enable_ssl if {
    input.Instance.enable_ssl == false
}