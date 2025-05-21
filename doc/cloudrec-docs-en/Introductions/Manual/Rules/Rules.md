# Rules

The Rules module is used to create, query, and manage risk rules. 

![1747662041991-79eb234b-ee75-4092-93af-880501192983.png](./img/BQIzmuWa8Y7C-6PE/1747662041991-79eb234b-ee75-4092-93af-880501192983-661114.png)

# How to create rules 
1. click the [**Add Rule**] button on the right

![1747662091838-3dba189e-59b6-42fc-8780-26dad100ce99.png](./img/BQIzmuWa8Y7C-6PE/1747662091838-3dba189e-59b6-42fc-8780-26dad100ce99-632580.png)



2. select [Cloud Providers] 
3. select [Resource Type]
4. input [rule name] and [rule description]] 
5. select [Rule Group], [Rule Type] and [Risk Level]] 
6. click [Next] to enter the rule development page 

![1747662490152-2d7bcc60-27eb-4e60-8ed3-e5409ac2ec87.png](./img/BQIzmuWa8Y7C-6PE/1747662490152-2d7bcc60-27eb-4e60-8ed3-e5409ac2ec87-998463.png)



7. the [INPUT] section in the upper-right corner of the page body indicates that the system selects an instance configuration from existing resources for rule debugging. The resource type is the same as that selected in the previous step. 
8. [The Rego PlayGround] on The left side of The main body is The place where Rego rules are developed and edited. 
9. Click [Execute] in the upper right corner and run rego. The result is displayed in [OUTPUT] in the lower right corner] 

![1747662575003-04dfa410-d0c5-427b-b8f9-87eb3125e9db.png](./img/BQIzmuWa8Y7C-6PE/1747662575003-04dfa410-d0c5-427b-b8f9-87eb3125e9db-160619.png)



10. after completing the rule development and dryrun, click [Next] to enter the repair suggestion page 
11. complete [Repair Suggestion], [Reference Links], [risk Context Template ]
12. click [Submit] 

![1747662626764-86938fa4-3c84-400e-b146-85687af15e28.png](./img/BQIzmuWa8Y7C-6PE/1747662626764-86938fa4-3c84-400e-b146-85687af15e28-776214.png)

# **<font style="color:rgba(0, 0, 0, 0.88);">Related Resource</font>** function 
associate two different resources to address scenarios where data access across resource types is required for risk analysis.

We collect configurations such as security groups and ACL as a separate resource (Resorce). If you need to perform ECS Security Group configuration detection and LB ACL configuration detection, you need to associate the two resources on the platform. 

## Process 
for example, when analyzing the risk that an Alibaba Cloud ECS instance is open to the entire network, you need to associate [ECS instance] with [security group] resources. 

1. Select Alibaba Cloud as the cloud platform and Compute/ECS as the resource type] 

![1747662840106-938d5359-8520-4256-94e8-a366a620afb7.png](./img/BQIzmuWa8Y7C-6PE/1747662840106-938d5359-8520-4256-94e8-a366a620afb7-171357.png)



2. at this time, you need to associate the resource data of the security group, and click [Related Resources] to configure it. 

![1747662918665-8d6f5b24-adbf-4ee2-bfb6-2a6bc2d571b8.png](./img/BQIzmuWa8Y7C-6PE/1747662918665-8d6f5b24-adbf-4ee2-bfb6-2a6bc2d571b8-872056.png)

+ resource Type: Select the resource type to be associated. 
+ Main resource Key: the ECS resource is used for the associated field, for example: <font style="color:rgb(0, 0, 0);">$.SecurityGroupIds.SecurityGroupId[*] </font>
+ associated resource Key: the field used to associate security group resources, such: <font style="color:rgb(0, 0, 0);">$.SecurityGroup.SecurityGroupId </font>
+ mount field name: the data associated to will be mounted on the new field 
3. results after correlation 

![1736495513801-ffd2e07e-38a0-48f2-b948-9fca54c5f7c4.png](./img/BQIzmuWa8Y7C-6PE/1736495513801-ffd2e07e-38a0-48f2-b948-9fca54c5f7c4-498090.png)

## description 
### value method 
path Rule Description 

```json
{
    "store": {
        "book": [
            {
                "category": "reference",
                "author": "Nigel Rees",
                "title": "Sayings of the Century",
                "price": 8.95
            },
            {
                "category": "fiction",
                "author": "Evelyn Waugh",
                "title": "Sword of Honour",
                "price": 12.99
            },
            {
                "category": "fiction",
                "author": "Herman Melville",
                "title": "Moby Dick",
                "isbn": "0-553-21311-3",
                "price": 8.99
            },
            {
                "category": "fiction",
                "author": "J. R. R. Tolkien",
                "title": "The Lord of the Rings",
                "isbn": "0-395-19395-8",
                "price": 22.99
            }
        ],
        "bicycle": {
            "color": "red",
            "price": 19.95
        }
    },
    "expensive": 10
}
```

| <font style="color:rgb(31, 35, 40);">JsonPath </font> | <font style="color:rgb(31, 35, 40);">Result </font> | <font style="color:rgb(31, 35, 40);">Value </font> |
| --- | --- | --- |
| `$.store.bicycle.color ` | <font style="color:rgb(31, 35, 40);">The bicycle color</font> | <font style="color:rgb(31, 35, 40);">red </font> |
| `<font style="color:rgb(31, 35, 40);">$.store.book[*].author </font>` | <font style="color:rgb(31, 35, 40);">The authors of all books </font> | <font style="color:rgb(31, 35, 40);">["Nigel Rees","Evelyn Waugh","Herman Melville","J. R. R. Tolkien"] </font> |


reference documentation: [https://github.com/json-path/JsonPath ](https://github.com/json-path/JsonPath)

### association Method 
the main resource Key and the associated resource Key are evaluated by using the json path expression. When the two values are equal, they are associated. One-to-one and one-to-many associations are supported. 



+ One-to-one association example: 



sample data A: 

```json

{
  "field1":"i-xxxxxxxxxx",
  "field2":"xxx"
}
```

sample data B: 

```json

{
  "field3":"i-xxxxxxxxxx",
  "field4":"xxx"
}
```

configure $.field1 and $.field3, and mount the field name newField to generate the result. 

```json

{
  "field1":"i-xxxxxxxxxx",
  "field2":"xxx",
  "newField":{
     "field3":"i-xxxxxxxxxx",
      "field4":"xxx"
  }
}
```

+ One-to-many association example 

sample data A: 

```json

{
  "field1":["i-xxxxxxxxxx"],
  "field2":"xxx"
}
```

sample data B: 

```json

{
  "field3":"i-xxxxxxxxxx",
  "field4":"xxx"
}
```

configure $.field1 and $.field3, and mount the field name newField to generate the result. 

```json

{
  "field1":"i-xxxxxxxxxx",
  "field2":"xxx",
  "newField":[
    {
     "field3":"i-xxxxxxxxxx",
      "field4":"xxx"
    }
  ]
}
```



# Risk Context Template 
the risk context template is used when subscribing to alarms to facilitate subscribers to quickly understand risk details. How to use the configuration to subscribe to alarms Read [subscribe to Alerts ](https://cloudrec.yuque.com/org-wiki-cloudrec-iew3sz/hocvhx/rqvy5gapmz43g29p).

Example of results 

![1737355455650-109f0e35-663e-4170-9466-7cd99540b275.png](./img/BQIzmuWa8Y7C-6PE/1737355455650-109f0e35-663e-4170-9466-7cd99540b275-065646.png)

#### how to configure risk context templates 
1. **when not configured, the full output of the Rego rule is used as the context by default.**
2. Use the jsonPath output from the run of the Rego rule to take the value from. The configuration method is as follows: 

```json
{$.Instance.name} is in risk
```

![1747663107589-54f7087c-64d2-4d0d-bd6a-2b59ee457568.png](./img/BQIzmuWa8Y7C-6PE/1747663107589-54f7087c-64d2-4d0d-bd6a-2b59ee457568-721827.png)

# next Reading 


