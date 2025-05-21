package gcp_versioning_enable_6500017_141
import rego.v1

default risk := false
risk if {
    not input.Bucket.versioning
}