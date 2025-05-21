package aws_versioning_enable_6500018_292
import rego.v1

default risk := false
risk if {
    not versioning.Status
}

versioning := input.Versioning