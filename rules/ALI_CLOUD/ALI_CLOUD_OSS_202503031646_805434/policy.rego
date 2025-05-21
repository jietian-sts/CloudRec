package ali_cloud_bucket_logging_enable_6500008_154
import rego.v1

default risk := false
risk if {
    input.LoggingEnabled == null
}