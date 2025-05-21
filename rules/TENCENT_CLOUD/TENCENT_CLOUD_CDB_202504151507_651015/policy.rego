package cloudrec_8400002_256
import rego.v1

default risk := false
risk if {
	cdb_without_audit
}
messages contains message if {
    risk == true
    message := {
        "Description": "腾讯云 CDB 数据库实例未开启审计日志"
    }
}

cdb_without_audit if {
    input.AuditConfig == null
}
