package gcp_bucket_logging_enable_6500016_145
import rego.v1

default risk := false
risk if {
    not input.Bucket.logging
}