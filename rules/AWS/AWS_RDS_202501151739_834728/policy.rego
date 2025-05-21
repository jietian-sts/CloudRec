package ob_aws_rds_public_5200001_291

import rego.v1

default risk := false

risk if {
    count(public_rds_mysql_rule) != 0
}

risk if {
    count(public_rds_oracle_rule) != 0
}

risk if {
    count(public_rds_postgresql_rule) != 0
}

risk if {
    count(public_rds_microsoft_sql_server_rule) != 0
}

sg_id := input.VPCSecurityGroups[0].GroupId

public_rds_mysql_rule[ip_permission] if {
    some group in input.VPCSecurityGroups
    some ip_permission in group.IpPermissions
    ip_permission.FromPort <= 3306
    ip_permission.FromPort >= 3306
    some ip_range in ip_permission.IpRanges
    ip_range.CidrIp == "0.0.0.0/0"
}

public_rds_oracle_rule[ip_permission] if {
    some group in input.VPCSecurityGroups
    some ip_permission in group.IpPermissions
    ip_permission.FromPort <= 1521
    ip_permission.FromPort >= 1521
    some ip_range in ip_permission.IpRanges
    ip_range.CidrIp == "0.0.0.0/0"
}

public_rds_postgresql_rule[ip_permission] if {
    some group in input.VPCSecurityGroups
    some ip_permission in group.IpPermissions
    ip_permission.FromPort <= 5432
    ip_permission.FromPort >= 5432
    some ip_range in ip_permission.IpRanges
    ip_range.CidrIp == "0.0.0.0/0"
}

public_rds_microsoft_sql_server_rule[ip_permission] if {
    some group in input.VPCSecurityGroups
    some ip_permission in group.IpPermissions
    ip_permission.FromPort <= 1433
    ip_permission.FromPort >= 1433
    some ip_range in ip_permission.IpRanges
    ip_range.CidrIp == "0.0.0.0/0"
}