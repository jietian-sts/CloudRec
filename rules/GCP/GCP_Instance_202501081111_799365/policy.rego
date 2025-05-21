package vm_with_default_service_account_and_full_access_scope_4000003_137
import rego.v1

default risk := false
risk if {
    vm_with_default_service_account
    scopes_with_full_access
}
messages contains message if {
    risk == true
    message := {
        "Description": "GCP VM实例配置了默认Service Account, 且Scope配置为Allow full access",
        "ServiceAccount": email,
        "Scopes": scopes
    }
}

email := input.Instance.serviceAccounts[_].email
scopes := input.Instance.serviceAccounts[_].scopes
default_service_account_pattern := "^\\d+-compute@developer.gserviceaccount.com$"

vm_with_default_service_account if {
    regex.match(default_service_account_pattern,email)
}

scopes_with_full_access if {
    "https://www.googleapis.com/auth/cloud-platform" in scopes
}