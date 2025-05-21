package enable_nfs_acl_6800003_202
import rego.v1

default risk := false
risk if {
    input.NfsAcl.Enabled == false
}