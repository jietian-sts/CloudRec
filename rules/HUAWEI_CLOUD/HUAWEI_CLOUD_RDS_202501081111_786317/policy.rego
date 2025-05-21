package ob_hws_rds_3300004_234

import rego.v1

default risk := false

risk if {
    disk_encryption_id
}

vpc_id := input.LoadBalancer.vpc_id

disk_encryption_id if {
    input.Instance.disk_encryption_id == ""
}