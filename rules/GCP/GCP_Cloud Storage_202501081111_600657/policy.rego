package bucket_default_object_acl_with_public_access_4400007_148
import rego.v1

default risk := false
risk if {
    defaultObjectAclWithUnrestrictedPrincipal
}

unrestrictedPrincipal := {"allUsers", "allAuthenticatedUsers"}
defaultObjectAclWithUnrestrictedPrincipal if {
    some acl in input.Item.defaultObjectAcl
    unrestrictedPrincipal[acl.entity]
}