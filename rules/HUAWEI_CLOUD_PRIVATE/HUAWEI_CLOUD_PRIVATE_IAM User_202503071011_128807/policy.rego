package hws_iam_admin_use_ak_6900001_267

import rego.v1

default risk := false

risk if {
	input.UserAttribute.is_domain_owner == true
	count(input.Credentials) > 0
}
