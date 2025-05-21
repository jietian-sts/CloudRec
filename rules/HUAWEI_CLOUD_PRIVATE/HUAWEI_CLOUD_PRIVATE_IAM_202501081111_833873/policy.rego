package hws_ram_login_access_2300002_282

import rego.v1

default risk := false

risk if {
	input.LoginProtects.enabled == false
	input.UserAttribute.access_mode in risk_access_mode
}

risk if {
	input.LoginProtects == null
	input.UserAttribute.access_mode in risk_access_mode
}

risk_access_mode := ["default", "console"]

name := input.UserDetail.name

access_mode := input.UserDetail.access_mode

description := input.UserDetail.description
