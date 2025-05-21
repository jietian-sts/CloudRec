package ob_hws_iam_3500001_240

import rego.v1

default risk := false

risk if {
    password_validity_period
}

vpc_id := input.LoadBalancer.vpc_id

password_validity_period if {
    input.DomainPasswordPolicy.password_validity_period == 0
}
