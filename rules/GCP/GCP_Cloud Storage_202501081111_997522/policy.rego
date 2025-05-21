package bucket_public_access_4500002_136
import rego.v1

default risk := false
risk if {
    not isEnforcedPublicAccessPrevention
    iamPolicyWithUnrestrictedPrincipal
}
risk if {
    not isEnforcedPublicAccessPrevention
    not isBucketLevelAccessEnabled
    bucketAclWithUnrestrictedPrincipal
}

isEnforcedPublicAccessPrevention if {
    input.Item.iamConfiguration.publicAccessPrevention == "enforced"
}
isBucketLevelAccessEnabled if {
    input.Item.iamConfiguration.uniformBucketLevelAccess.enabled == true
}

unrestrictedPrincipal := {"allUsers", "allAuthenticatedUsers"}
iamPolicyWithUnrestrictedPrincipal if {
    some binding in input.IamPolicy.bindings
    unrestrictedPrincipal[binding.members[_]]
}
bucketAclWithUnrestrictedPrincipal if {
    some acl in input.Item.acl
    unrestrictedPrincipal[acl.entity]
}