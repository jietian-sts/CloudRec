# Collector FAQ

### Q: Can I deploy more than one Collector? I have 50 cloud accounts, and a few cloud products have been opened under each cloud account. How many collectors do I need to deploy? 
A: Yes, the Collector obtains 10 cloud accounts from the Server at A time. The 10 cloud accounts will be executed in sequence, and multiple regions of the same type of cloud products under A single cloud account will also be executed concurrently. According to the following configuration, the Collector will trigger scheduling every 5 minutes. If the previous round of tasks is not completed, the current round of tasks will be skipped, and different cloud platforms will not affect each other. You can adjust the Cron configuration or start multiple collectors as needed. 

```yaml
# Collector name, if not configured, hostname will be used
AgentName: "Alibaba Cloud、Huawei Cloud、AWS、Tencent Cloud Collector"
# The server URL, http://localhost:8080 is used by default, and can be adjusted according to actual conditions
ServerUrl: "http://localhost:8080"

# eg：@every 30s、@every 5m、@every 1h
# @every 5m means obtaining an account every five minutes. If the current task is finished, skip this task.
Cron: "@every 5m"

# If RunOnlyOnce is set to false, the program will be executed once immediately, but the program will not exit. It will be run regularly according to the Cron cycle.
# If RunOnlyOnce is set to true, the program will be executed once immediately and then exit.
RunOnlyOnce: false

# Access token, which is used to authenticate the request. You can get it from the server
AccessToken: "<token>"
```

### Q: I have a large number of Alibaba Cloud accounts. I want to deploy multiple collectors, but the number of AWS accounts is relatively small. I only need to deploy one Collector. Can this be achieved? 
A: Yes, each cloud platform retains the main method and can be started independently.



