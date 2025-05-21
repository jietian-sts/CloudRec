package ob_aws_rds_storage_encrypted_5300002_294

import rego.v1

default risk := false

risk if {
    input.DBInstance.StorageEncrypted != true
}

