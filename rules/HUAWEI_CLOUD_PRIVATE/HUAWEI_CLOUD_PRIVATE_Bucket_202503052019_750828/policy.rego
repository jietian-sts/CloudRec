package cloudrec_6700010_270

import rego.v1

default risk := false

risk if {
	bucket_allows_public_read_access
}

all_user_uri := {
	"http://acs.amazonaws.com/groups/global/AuthenticatedUsers",
	"http://acs.amazonaws.com/groups/global/AllUsers",
}

risk_permissions := {
	"FULL_CONTROL",
	"READ",
	# 	"WRITE",
	# 	"READ_ACP",
	# 	"WRITE_ACP",
}

bucket_allows_public_read_access if {
	# Case 1: public read access allowed via ACL
	count(risk_msgs) > 0
}

bucket_allows_public_read_access if {
	# Case 2: public read access allowed via access policy
	count(statement_allow_read) > 0
}

# ======================================================================
# ========================= Case 1 check logic =========================
# ======================================================================

risk_msgs contains {"user": user, "permission": permission, "bucket": bucket} if {
	some grant in input.AccessControlPolicy.Grants
	uri := grant.Grantee.URI
	user := split(uri, "/")[count(split(uri, "/")) - 1]
	permission := grant.Permission
	all_user_uri[uri]
	risk_permissions[permission]
	bucket := input.Bucket.Name
}

# ======================================================================
# ========================= Case 2 check logic =========================
# ======================================================================
statement_allow_read contains risk_action if {
	bucket_policy := json.unmarshal(input.BucketPolicy)

	some statement in bucket_policy.Statement
	risk_action := obs_read_action(statement)
	count(risk_action) > 0
	effect_allow(statement)
	wildcard_principal(statement)
	null_condition(statement)
}

obs_read_actions := {"*", "s3:*", "s3*", "s3:list", "s3:get"}

obs_read_action(statement) := actions if {
	actions := [action |
		some action in statement.Action
		startswith(lower(action), obs_read_actions[_])
	]
}

obs_read_action(statement) := statement.Action if {
	startswith(lower(statement.Action), obs_read_actions[_])
}

wildcard_principal(statement) if {
	statement.Principal == "*"
}

wildcard_principal(statement) if {
	statement.Principal[_] == "*"
}

wildcard_principal(statement) if {
	statement.Principal.AWS[_] == "*"
}

effect_allow(statement) if {
	statement.Effect == "Allow"
}

null_condition(statement) if {
	object.get(statement, "Condition", null) == null
}

# ======================================================================
# ========================= msg_to_user logic ==========================
# ======================================================================
msg_to_user contains info if {
	some stmt in statement_allow_read
	info := sprintf("BucketPolicy 允许任意用户在 Bucket [%v] 上执行: %v", [input.Bucket.Name, concat("、", stmt)])
}

msg_to_user contains info if {
	some risk_msg in risk_msgs
	info := sprintf("%v 可以在 Bucket %v 上执行 %v 操作", [risk_msg.user, risk_msg.bucket, risk_msg.permission])
}
