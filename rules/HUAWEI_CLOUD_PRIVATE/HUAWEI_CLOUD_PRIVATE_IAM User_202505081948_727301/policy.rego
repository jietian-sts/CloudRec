package cloudrec_8900013_283
import rego.v1

default risk := false
risk if {
    is_domain_owner
    has_credentials
}

is_domain_owner if {
    input.UserDetail.is_domain_owner == true
}

has_credentials if {
    count(input.Credentials) > 0
}