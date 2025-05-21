package login_console_should_enable_mfa_6100001_158
import rego.v1

default risk := false
risk if {

    MFABindRequired == false
    ConsoleLogin == true
}

MFABindRequired := input.LoginProfile.MFABindRequired
ConsoleLogin := input.ConsoleLogin