package ob_aws_ec2_ssh_open_2700009_295
import rego.v1

default risk := false

risk if {
	count(sg_groups_ssh_port_rule) != 0
}

instance_id := input.Instance.InstanceId

sg_groups_id contains p if {
	some p in input.Instance.SecurityGroups
}

sg_groups_ssh_port_rule contains p if {
	some sg in input.SecurityGroups
    some p in sg.SecurityGroup.IpPermissions
    p.FromPort <= 22
    p.ToPort >= 22
    
    some cidr in p.IpRanges
    cidr.CidrIp == "0.0.0.0/0"
}