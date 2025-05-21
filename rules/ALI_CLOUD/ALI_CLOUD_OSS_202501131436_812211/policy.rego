package ob_alicloud_oss_public_account_5100001_205
import rego.v1

default risk := false
default public_oss_account := false

risk if {
    public_oss_account
}

vpc_id := input.LoadBalancer.vpc_id

public_oss_account  if {
    input.BucketPolicyStatus == false
}