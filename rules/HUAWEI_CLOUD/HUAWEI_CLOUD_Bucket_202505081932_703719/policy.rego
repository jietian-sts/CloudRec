package cloudrec_8900009_244

import rego.v1

default risk := false

risk if count(risk_msgs) > 0

all_user_uri := {
	"http://acs.amazonaws.com/groups/global/AuthenticatedUsers",
	"http://acs.amazonaws.com/groups/global/AllUsers",
}

risk_permissions := {
	"FULL_CONTROL",
	"READ",
	"WRITE",
	"READ_ACP",
	"WRITE_ACP",
}

risk_msgs contains {"user": user, "permission": permission, "bucket": bucket} if {
	some grant in input.AccessControlPolicy.Grants
	uri := grant.Grantee.URI
	user := split(uri, "/")[count(split(uri, "/")) - 1]
	permission := grant.Permission
	all_user_uri[uri]
	risk_permissions[permission]
	bucket := input.Bucket.Name
}

msg_to_user contains info if {
	some risk_msg in risk_msgs
	info := sprintf("%v 可以在 Bucket %v 上执行 %v 操作", [risk_msg.user, risk_msg.bucket, risk_msg.permission])
}
