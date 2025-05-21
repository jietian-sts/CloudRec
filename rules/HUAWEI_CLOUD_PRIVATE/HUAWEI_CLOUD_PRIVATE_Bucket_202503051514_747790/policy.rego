package cloudrec_6700002_276

import rego.v1

default risk := false

risk if {
	count(statement_allow_all_action) > 0
}

statement_allow_all_action contains risk_action if {
	bucket_policy := json.unmarshal(input.BucketPolicy)
	some statement in bucket_policy.Statement
	risk_action := obs_wildcard_actions(statement)
	count(risk_action) > 0
	effect_allow(statement)
	null_condition(statement)
}

wildcard_action := {"*", "s3:*", "s3*"}

obs_wildcard_actions(statement) := actions if {
	actions := [action |
		some action in statement.Action
		action in wildcard_action
	]
}

obs_wildcard_actions(statement) := statement.Action if {
	lower(statement.Action) in wildcard_action
}

effect_allow(statement) if {
	statement.Effect == "Allow"
}

null_condition(statement) if {
	object.get(statement, "Condition", null) == null
}

msg_to_user contains info if {
	some stmt in statement_allow_all_action
	info := sprintf("BucketPolicy 允许在 Bucket [%v] 上执行任意操作", [input.Bucket.Name])
}
