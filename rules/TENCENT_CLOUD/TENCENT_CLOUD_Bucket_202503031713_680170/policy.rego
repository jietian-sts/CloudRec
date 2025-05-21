package tencent_cloud_versioning_enable_6500014_260
import rego.v1

default risk := false
risk if {
    versioning == null
}
risk if {
    versioning.Status != "Enabled"
}

versioning := input.Versioning