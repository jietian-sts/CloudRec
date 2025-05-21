# GCP

| Type | Resource | Rule name | Status |
| :---: | :---: | :---: | :---: |
| Compute | Compute instance | Compute instance should not have a public IP address | ✅ |
| | | Compute instance should not use the default Compute Engine service account with full API access | ✅ |
| | | Compute instance connection through serial ports should not be enabled | 🚧（TBD） |
| | | Check for Publicly Shared Disk Images | 🚧（TBD） |
| | Cloud Function | GCP Function should not use Default Service Account | 🚧（TBD） |
| | | Publicly Accessible Functions | 🚧（TBD） |
| | | GCP Function using Default Service Account | 🚧（TBD） |
| | | GCP Function using Service Account with Basic Roles | 🚧（TBD） |
| Network | Firewall | VPC firewall rule should not allow public access | ✅ |
| | Cloud Armor | Cloud Armor policy should not allow access from any IP address | ✅ |
| | | Cloud Armor policy default rule action should be 'Deny' | 🚧（TBD） |
| Database | BigQuery | BigQuery Datasets should be private | 🚧（TBD） |
| | Cloud SQL | Cloud SQL SQL server instance should have 'external scripts enabled' flag set to 'off' | 🚧（TBD） |
| | | Cloud SQL database instance should not be open to the world |  |
| Storage | Bucket | Bucket anonymously or publicly accessible through IAM policy should not be allowed | ✅ |
| | | Bucket anonymously or publicly accessible through default object ACL should not be allowed | ✅ |
| | | Bucket anonymous and public access should not be allowed | ✅ |
| Container | ArtifactRegistry | Check for Publicly Accessible Artifact Registry Repositories | 🚧（TBD） |
| Security | Cloud Organization | Public IP access on creating Vertex AI notebooks instances and runtimes should be disabled by an Organization Policy |  |
| | KMS | KMS Key should not use 'allUsers' or 'allAuthenticatedUsers' permissions | 🚧（TBD） |
| | | Check for Publicly Accessible Cloud KMS Keys | 🚧（TBD） |


