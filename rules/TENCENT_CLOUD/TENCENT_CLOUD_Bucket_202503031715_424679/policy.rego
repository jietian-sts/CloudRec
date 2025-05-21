package tencent_cloud_bucket_logging_enable_6500015_255
import rego.v1

default risk := false
risk if {
    logging == null
}

logging := input.BucketLogging