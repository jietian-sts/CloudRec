package ob_tencent_cdb_3600009_261
import rego.v1

default risk := false

risk if {
	cdb_without_sg
}

instance_id := input.InstanceInfo.InstanceId

cdb_without_sg if {
    count(input.SecurityGroup) == 0
}