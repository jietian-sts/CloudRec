package aws_bucket_logging_enable_6500019_290
import rego.v1

default risk := false
risk if {
   not input.LoggingEnabled
}