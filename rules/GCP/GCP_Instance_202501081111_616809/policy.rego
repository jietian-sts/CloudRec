package vm_with_default_service_account_4000002_135
import rego.v1

default risk := false
risk if {
    vm_with_default_service_account
}

email := input.Instance.serviceAccounts[_].email
default_service_account_pattern := "^\\d+-compute@developer.gserviceaccount.com$"

vm_with_default_service_account if {
    regex.match(default_service_account_pattern,email)
}