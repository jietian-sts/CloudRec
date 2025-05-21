1. After the agent is deployed to the public cloud, the whitelist size can be reduced.
2. Set IP whitelist based on agent deployment location in Apsara Stack.
# Create a Permission Policy
1. Visit the Tengxun CAM console: [https://console.cloud.tencent.com/cam/overview](https://console.cloud.tencent.com/cam/overview)
2. In the left menu bar, select **Policy** > **New Custom Policy**
3. Click **Create by Policy Syntax**
4. Select **Blank Template**
5. Copy the code below and paste it into the input box, modify the value in `qcs:ip` to be the export IP of the collector deployment server, and click **Confirm**

```json
{
    "statement": [
        {
            "action": "*",
            "condition": {
                "numeric_equal": {
                    "qcs:read_only_action": 1
                },
                "ip_equal": {
                    "qcs:ip": [
                        "some ip/cidr here"
                    ]
                }
            },
            "effect": "allow",
            "resource": "*"
        }
    ],
    "version": "2.0"
}

```
6. Input **Policy Name**: `CloudRec`; **Description**: `Using for CloudRec Collector`
7. Click **Confirm**, complete permission policy creation
# Create and authorize user groups
1. In the left menu bar, select **User Group**
2. Click **New User Group**
3. Input user group name: `cloudrec`, comment: `use for cloudrec collector`
4. Choose policy by searching `cloudrec`
5. Click **Next**
6. Click **Done**
# Create user and add to user group
1. In the left menu bar, select **User** > **User List**
2. Click **New User** > **Quick Create**
3. Username: `cloudrec`, access method: `API`, user permission: `cloudrec`
4. Click **Create User**
5. Download CSV file, save AK/SK
6. In the left menu bar, select **User Group** > `cloudrec`
7. Add user to group
8. Click **OK**
# Enter the cloud account to the CloudRec platform
1. Login Platform
2. Select **Cloud Account Management** in the left-side menu bar
3. Click on the right **Add a Cloud Account**
4. In turn:
    1. Input **Account ID**
    2. Input **Account Alias**
    3. Select **Tenant**
    4. Select **Platform**, here is **Tencent Cloud**
    5. Input **AK** which is the option obtained above (**SecretId**)
    6. Input **SK** which is the option obtained above (**SecretKey**)
    7. Select **Cloud Services** (this option limits the range of cloud resources accessed by the cloud account. If this option is not specified, all resources are accessed.)
    8. Input **Site**, the option is the proprietary cloud site
5. Click **OK**, verify the validity of the cloud account voucher and complete the Tencent cloud account entry
