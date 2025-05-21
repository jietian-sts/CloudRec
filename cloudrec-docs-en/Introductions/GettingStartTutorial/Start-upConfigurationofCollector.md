# Start-up Configuration of Collector

# ⚙️ Default Configuration 
```yaml

# Collector name, if not configured, hostname will be used
AgentName: "Alibaba CloudHuawei Cloud,AWS,Tencent Cloud,GCP,Baidu Cloud Collector"
# The server URL, http://localhost:8080 is used by default, and can be adjusted according to actual conditions
ServerUrl: "http://localhost:8080"

# eg：@every 30s、@every 5m、@every 1h
# @every 5m means obtaining an account every five minutes. If the current task is finished, skip this task.
Cron: "@every 5m"

# If RunOnlyOnce is set to false, the program will be executed once immediately, but the program will not exit. It will be run regularly according to the Cron cycle.
# If RunOnlyOnce is set to true, the program will be executed once immediately and then exit.
RunOnlyOnce: false

# Access token, which is used to authenticate the request. You can get it from the server
AccessToken: "change your access token"

#  Deployment site. If the deployment site is configured as 'H1', only cloudAccount of this site can be obtained. If the deployment site is not configured, all cloudAccount can be obtained.
#  eg:["H1"]
#  eg:["H1","H2"]
Sites: [  ]

# Pay attention to the risk error information. If the error message contains text, the risk will be submitted to the server
AttentionErrorTexts: [ "NoPermission", "NotAuthorized", "NotApplicable",
                       "Forbidden.RAM", "Forbidden", "Throttling.User", "Throttling", "InvalidAccessKeyId.NotFound", "ServiceUnavailable", "Forbidden" ]
```

# Documentation 
## i. Configuration Overview 
This configuration file is used to set the relevant parameters of the collector, including the collector name, server address, collection cycle, operation mode, access token, deployment site, and error information that needs attention. 

## II. Detailed Description of Configuration Items 
### 1. AgentName 
+ The name of the collector. If not configured, the host name is used as the collector name. 
+ **Example**: `AgentName: "Alibaba CloudHuawei Cloud,AWS,Tencent Cloud,GCP,Baidu Cloud Collector" `
+ **Description**: This name can be used to identify the collector and facilitate differentiation in the environment of multiple collectors. 

### 2. ServerUrl 
+ The URL address of the server to which the collector sends the collected data. Default Use `http://localhost:8080 `, Can be adjusted according to the actual situation. 
+ **Example**:`ServerUrl: "http://localhost:8080" `
+ **Description**: Ensure that the URL address is accessible, otherwise the collector will not be able to send data to the server. 

### 3. Cron 
+ The execution cycle of the collection task, which is configured by using the Cron expression. 
+ **Example**: `Cron: "@every 5m" `
+ **Description**: `@every 5m `indicates that the acquisition task is performed every five minutes. If the current task is completed within the cycle, the task is skipped. Common expressions are also `@every 30s `(Executed every 30 seconds), `@every 1h `(Executed every hour), etc. 

### 4. RunOnlyOnce 
+ Controls the operating mode of the collector. If set `false `, the collector will immediately perform a collection task and will not exit, but follow `Cron `the configured cycle runs periodically; If set `true`, the collector will immediately perform a collection task and then exit. 
+ **Example**: `RunOnlyOnce: false `
+ **Description**: Select the appropriate operation mode according to actual needs. If you need to collect data continuously, it is recommended that you set it `false `; If you only need to collect data once, you can set it `true `. 

### 5. AccessToken 
+ An access token to authenticate the request. The token may be obtained from the server. 
+ **Example**: `AccessToken: "change your access token" `
+ **Description**: Before using the collector, you need to replace the token with a valid token obtained from the server, otherwise the collector will not be able to pass the authentication. 

### 6. Sites 
+ The configuration of the deployment site. If a deployment site is configured, the collector will only obtain the cloud account information of the site. If it is not configured, the collector will obtain all cloud account information. 
+ **Example**: `Sites: [ ] `
+ **Description**You can fill in the site name in square brackets, such `["S1"] `or `["T1","H2"] `. 

### 7. AttentionErrorTexts 
+ A list of error message texts that need attention. If an error message that occurs during the collection process contains the text in the list, the associated risk will be submitted to the server. 
+ **Example**: `AttentionErrorTexts: [ "NoPermission", "NotAuthorized", "NotApplicable", "Forbidden.RAM", "Forbidden", "Throttling.User", "Throttling", "InvalidAccessKeyId.NotFound", "ServiceUnavailable", "Forbidden"] `
+ **Description**: By configuring this list, important errors in the collection process can be found and handled in time.

