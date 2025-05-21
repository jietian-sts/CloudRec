package cloudrec_6700006_266

import rego.v1

default risk := false

risk if {
	count(statement_allow_all_action_for_all) > 0
}

statement_allow_all_action_for_all contains risk_action if {
	bucket_policy := json.unmarshal(input.BucketPolicy)

	some statement in bucket_policy.Statement
	risk_action := obs_all_action(statement)
	count(risk_action) > 0
	effect_allow(statement)
	wildcard_principal(statement)
	null_condition(statement)
}

obs_all_actions := {"*", "s3:*", "s3*"}

obs_all_action(statement) := actions if {
	actions := [action |
		some action in statement.Action
		startswith(lower(action), obs_all_actions[_])
	]
}

obs_all_action(statement) := statement.Action if {
	startswith(lower(statement.Action), obs_all_actions[_])
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

msg_to_user contains info if {
	some stmt in statement_allow_all_action_for_all
	info := sprintf("BucketPolicy 允许任意用户在 Bucket [%v] 上执行任意操作", [input.Bucket.Name])
}
