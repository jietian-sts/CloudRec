package cloudrec_5400001_232

import rego.v1

default risk := false

risk if {
    count(public_ip) > 0
}

instance_id := input.Instance.id
instance_name := input.Instance.name

public_ip[p] if {
    some p in input.Instance.public_ips
    p != null
}
