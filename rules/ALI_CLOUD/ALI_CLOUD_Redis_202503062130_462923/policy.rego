package enable_audit_log_6800006_190
import rego.v1

default risk := false
risk if {
    input.AuditLogConfig.DbAudit == "false"
}