```txt
/lunar_collector
├── README.md
├── LICENSE
├── LEGAL.md
├── config.yaml
├── go.mod
├── go.sum
├── alicloud/
├── alicloud-private/
├── aws/
├── baidu/
├── gcp/
├── hws/
├── tencent/
├── core-sdk/
├── deploy_cloudrec/

Notes:
- Each cloud provider directory (alicloud, aws, baidu, gcp, hws, tencent) contains core submodules: collector, platform, deployment scripts, and configuration files.
- core-sdk is the common core library containing configurations, constants, logging, and utilities.
- deploy_cloudrec contains universal deployment-related scripts and configurations.
```