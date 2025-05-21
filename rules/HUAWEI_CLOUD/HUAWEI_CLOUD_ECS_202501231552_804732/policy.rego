package cloudrec_5500002_250

import rego.v1

default risk := false

risk if {
    count(unencrypte_disk) > 0
}

region := input.RegionId
instance_id := input.ServerDetail.id
instance_name := input.ServerDetail.name

unencrypte_disk[p] if {
    some p in input.Volumes
    p.bootable != "true"
    p.encrypted == false
}
