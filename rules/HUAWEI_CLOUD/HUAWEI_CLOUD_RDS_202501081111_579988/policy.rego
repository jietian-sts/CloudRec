package ob_hws_rds_3300002_249

import rego.v1

default risk := false

risk if {
    auditlog_policy
}

vpc_id := input.LoadBalancer.vpc_id

auditlog_policy if {
	input.AuditlogPolicy != null
    input.AuditlogPolicy.keep_days == 0
}