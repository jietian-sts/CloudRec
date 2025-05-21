package root_account_ak_enabled_3800002_167
import rego.v1

default risk := false
risk if {
	root_with_accessKey
}

root_with_accessKey if {
    input.AccountSecurityPracticeReport.AccountSecurityPracticeUserInfo.RootWithAccessKey > 0
}