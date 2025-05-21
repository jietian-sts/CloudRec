1. Determine the network ACL after deploying collector.
2. Set IP whitelist based on agent deployment location in Apsara stack.
## Create Policy
1. Access the Alibaba Cloud RAM console: [RAM Console](https://ram.console.aliyun.com/overview)
2. In the left menu bar, select **Permission Management > Permission Policy**
3. Click **Create a Permission Policy**
4. Select **Script Editing**
5. Copy the code below and paste it into the input box, change the value of `Condition.IpAddress["acs:SourceIp"]` to the Public IP of the collector, and click **Confirm**
```json
{
    "Version": "1",
    "Statement": [
        {
            "Action": [
                "*:Describe*",
                "*:List*",
                "*:Get*",
                "*:BatchGet*",
                "*:Query*",
                "*:BatchQuery*",
                "actiontrail:LookupEvents",
                "actiontrail:Check*",
                "dm:Desc*",
                "dm:SenderStatistics*",
                "ram:GenerateCredentialReport"
            ],
            "Resource": "*",
            "Effect": "Allow",
            "Condition": {
                "IpAddress": {
                    "acs:SourceIp": [
                        "some ip/cidr here"
                    ]
                }
            }
        }
    ]
}

```

6. Input **Name**: "CloudRec"; **Remarks**: "Using for CloudRec Collector"
7. Click **Confirm**, complete permission policy creation
## Create and Authorize User Groups
1. In the left menu bar, select **Identity Management > User Groups**
2. Click **Create a User Group**
3. Input:
   - **User group name**: "CloudRec"
   - **Display name**: "CloudRec"
   - **Remarks**: "Use for CloudRec Collector"
4. Click **Confirm**, complete the user group creation
5. Find the user group you just created, in the far right **Operation** column, click **Add Permissions**
6. Configure:
   - **Resource Scope**: Account level
   - **Authorized Subject**: The default is the user group selected in the previous step
   - **Permission Policy**: Search for the permissions policy you just created "CloudRec"
7. Click **Confirm New Authorization**, complete authorization
## Create a User and Add to a User Group
1. In the left menu bar, select **Identity Management > Users**
2. Click **Create User**
3. Configure:
   - **Login Name**: "cloudrec"
   - **Display Name**: "cloudrec"
   - **Access mode**: Use permanent AccessKey access
4. Click **Determine**
5. Console pop-up **Security Verification** window, select the available **Verification Method**
6. After authentication is completed, the user is successfully created
7. Download CSV file, copy and save:
   - **AccessKey ID**
   - **AccessKey Secret**
8. Select the user, click below **Add to User Group**
9. Configure:
   - **User selection**: The default is the user selected in the previous step
   - **User Group Selection**: Search for the user group name you just created "CloudRec"
10. Click **Determine**
11. Console pop-up **Security Verification** window, select the available **Verification Method**
12. Successfully added to user group after authentication
13. Click **Complete**, complete the user group addition
## Enter the Cloud Account to the CloudRec Platform
1. Login Platform
2. Select **Cloud Account Management** in the left-side menu bar
3. Click on the right **Add a Cloud Account**
4. Configure:
   1. Input **Account ID**
   2. Input **Account Alias**
   3. Select **Tenant**
   4. Select **Platform**, here is **Alibaba Cloud**
   5. Input **AK**, the option is obtained above (**AccessKey ID**)
   6. Input **SK**, the option is obtained above (**AccessKey Secret**)
   7. Select **Cloud Services** (this option limits the range of cloud resources accessed by the cloud account. If this option is not specified, all resources are accessed)
   8. Input **Site**, the option is the proprietary cloud site
5. Click **Determine**, verify the validity of the cloud account voucher, and complete the Alibaba Cloud account entry