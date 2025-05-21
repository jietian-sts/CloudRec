package service_account_with_key_file_5400002_144
import rego.v1

default risk := false
risk if {
    count(keys) > 0
}

keys := input.Keys