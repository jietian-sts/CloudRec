# Create a Permission Policy
1. Access the AWS IAM console: [https://us-east-1.console.aws.amazon.com/iam/home#/home](https://us-east-1.console.aws.amazon.com/iam/home#/home)
2. In the left menu bar, select **Access management** > **Policies**
3. Click **Create Policy**
4. Select **JSON**
5. Copy the code below and paste it into the input box, ~~change the value in Condition.IpAddress["acs:SourceIp"] to the exit IP address of the collector deployment server~~, click **Next**

6. Input **Policy name**: `CloudRec`
7. Click **Create Policy**, complete policy creation
# Create and Authorize User Groups
1. In the left menu bar, select **Access management** > **User Groups**
2. Click **Create Group**
3. Input **User group name**: `CloudRec`
4. In **Attach permissions policies**, search for the newly created Policy `CloudRec` and select it
5. Click **Create user group**, complete the user group creation
# Create Users and Access Credentials
1. In the left menu bar, select **Access management** > **Users**
2. Click **Create User**
3. **User name**: `cloudrec`
4. Click **Next**
5. In **Permissions options**, select **Add user to group**
6. In **Add user to group**, select the user group you just created (`CloudRec`)
7. Click **Next**
8. Click **Create User**, complete User Creation
9. Search for and select the newly created user in the user list (`cloudrec`), click the user name to enter the details page
10. Select **Security credentials** tab, find **Access keys** bar, click **Create access keys**
11. In **Use case**, select **Other**
12. Click **Next**
13. In **Description tag value**, enter a description: `Use for CloudRec Collector`
14. Click **Create access key**
15. Copy and save **Access key** and **Secret access key**
16. Click **Download .csv file** to download and save the CSV file
17. Click **Done**, complete user and credential creation
# Enter the Cloud Account to the CloudRec Platform
1. Login to Platform
2. Select **Cloud Account** in the left-side menu bar
3. Click on the right **Add**
4. In turn:
    1. Input **Account ID**
    2. Input **Account Alias**
    3. Select **Tenant**
    4. Select **Cloud Provider** (here is **AWS**)
    5. Input **AK** (the option is obtained above: **Access key**)
    6. Input **SK** (the option is obtained above: **Secret access key**)
    7. Select **Cloud Services** (this option limits the range of cloud resources accessed by the cloud account, and is not set by default to access all)
    8. Input **site** (the option is the proprietary cloud site)
5. Click **OK**, verify the validity of the cloud account voucher, and complete the AWS account entry
