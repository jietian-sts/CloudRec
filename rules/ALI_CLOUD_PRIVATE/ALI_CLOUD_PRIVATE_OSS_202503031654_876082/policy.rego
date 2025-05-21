package ali_cloud_private_bucket_logging_enable_6500009_265
import rego.v1

default risk := false
risk if {
    input.LoggingEnabled == null
}