# AWS

| Type | Resource | Rule | Status |
| :---: | :---: | :---: | :---: |
| Compute | EC2 | EC2 instance exposed to public by SecurityGroups | ✅ |
| | | EC2 instance exposed SSH default port(22) to public by SecurityGroups | ✅ |
| Database | RDS | RDS cluster exposed to public | ✅ |
| | | RDS enables publicly accessible | ✅ |
| | | RDS instance should be encrypted | ✅ |
| Storage | S3 | Bucket should enable audit logs | ✅ |
| | | Bucket should enable verstioning | ✅ |
| Network | ELB | ELB uses inscure protocol | ✅ |
| | | ELB exposed to public | ✅ |


