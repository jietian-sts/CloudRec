# Alibaba Cloud

| Type  | Resource | Rule Name | Status  |
| :---: | :---: | :---: | :---: |
| calculation  | cloud server ECS  | ECS high-risk Port open  | ✅  |
| | | ECS opens port 22 to the public network  | ✅  |
| | | The entire ECS network is open  | ✅  |
| | | ECS outbound traffic is fully connected  | ✅  |
| Storage  | object Storage OSS  | OSS enabled public read/write  | ✅  |
| | | OSS bucket allows anonymous operations  | ✅  |
| | | The bucket permission is set to public-read.  | ✅  |
| | | The bucket permission is set to public-read-write.  | ✅  |
| | | OSS does not configure network policy risk  | ✅  |
| | | OSS access to any VPC  | ✅  |
| | | OSS anti-theft chain settings include *  | ✅  |
| | | OSS cross-domain origin configuration includes * | ✅  |
| | | OSS manifest file leakage risk  | ✅  |
| | File storage NAS  | public network exposure risk of file store NAS  | ✅  |
| | | NFS ACL whitelist not enabled  | ✅  |
| | | SMB ACL whitelist not enabled  | ✅  |
| Database  | apsaradb for RDS  | the instance port is open to the entire network.  | ✅  |
| | Cloud Native database PolarDB  | the polardb port is open across the network (or the ACL setting is improper)  | ✅  |
| | Cloud Database oceanbase  | OceanBase is open to the whole network  | ✅  |
| | Apsaradb for mongodb  | MongoDB is open to the entire network.  | ✅  |
| | HBase cloud database  | cloud Database HBase public network exposure risk  | ✅  |
| | Cloud Database ClickHouse  | cloud Database ClickHouse public network exposure risk  | ✅ |
| | Cloud Database ClickHouse  | cloud Database ClickHouse set public network ACL network segment range is too large  | ✅  |
| | Cloud native data warehouse AnalyticDB PostgreSQL  | the cloud-native data warehouse AnalyticDB PostgreSQL is open to the public network.  | ✅  |
| | Redis  | enable public proxy for the instance  | ✅  |
| | | Instance ports are open across the network  | ✅  |
| | | The instance does not have audit logs enabled  | ✅  |
| | Cloud-native multimodal database Lindorm  | cloud-native multimode database Lindorm public network exposure risk  | ✅  |
| Middleware  | cloud message queue for Kafka  | open access to the public network for kafka instances  | ✅  |
| | Message Queue RocketMQ version 5.0  | message Queuing RocketMQ public network exposure risk  | ✅  |
| | The microservice engine MSE  | microservice engine MSE public network exposure risk  | 🚧（TBD） |
| Container  | container mirroring service ACR  | instance public network exposure risk  | ✅  |
| | Container Service ACK  | IngressNightmare  | ✅  |
| Security  | cloud Security Center (sas)  | cloud security center is not installed on the ECS instance  | ✅  |
| | Cloud Firewall  | cloud Firewall does not set the default non-network policy.  | ✅  |
| | | There are assets not covered by Firewall  | 🚧（TBD） |
| | access control RAM  | User AK not used for more than one year  | ✅  |
| | | AK exists under the sub-account that can be logged on to the console.  | ✅  |
| | | Enable AccessKey for the primary account  | ✅  |
| | | Login risk of sub-account control account  | ✅  |
| | | RAM user permissions are too large  | ✅  |
| | | RAM roles are authorized to external accounts  | ✅  |
| | | RAM role is authorized to ECS | ✅  |
| | Digital Certificate Management Service (formerly SSL Certificate)  | SSL Certificate purchased on the cloud expires or is about to expire  | ✅  |
| | Private network VPC  | public network exposure of the security group  | ✅  |
| | | Inappropriate security group settings  | ✅  |
| | Load balancing SLB  | the SLB non-standard port is open across the network or the ACL is set improperly.  | ✅  |
| | Applied load balancing ALB  | open ALB port without ACL configuration, directly open to the public network  | ✅  |
| | Network-based load balancing NLB  | NLB high-risk Port exposure  | ✅  |
| Big Data Computing  | search analysis service Elasticsearch version  | the Elasticsearch port is open across the network (or the ACL setting is improper)  | ✅  |
| | | The kibana instance is open for public access.  | ✅  |
| | Cloud Native big data computing service MaxCompute  | no whitelist protection risk for MaxCompute projects  | ✅ |


