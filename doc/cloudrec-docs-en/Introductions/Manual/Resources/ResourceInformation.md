# Resource Information

Resource Information is a module for querying resource details.

![1747659198471-1fbb7d1b-bd95-4a01-a1f1-a03506239f6e.png](./img/9dZ-MDxN8iNk0tIi/1747659198471-1fbb7d1b-bd95-4a01-a1f1-a03506239f6e-379090.png)



Click on resource name, the metadata JSON details of the resource will pop up from the right sidebar. 

![1747659239667-3fa1dab0-5804-46cc-ac56-a2cf770f9990.png](./img/9dZ-MDxN8iNk0tIi/1747659239667-3fa1dab0-5804-46cc-ac56-a2cf770f9990-897455.png)



Click on the left side of the resource record **triangle symbol** button, additional fields of the resource record, such as cloud account number, cloud platform, IP address, tenant name, and custom fields, are displayed. Among them **IP address** the settings can be read [set the IP address field of the resource ](#dMo99), **custom Fields** the settings can be read [set custom fields for an resource ](#Dorhq). 

![1747659331766-6f8fbe9e-335d-46e7-bf71-35a5a541318f.png](./img/9dZ-MDxN8iNk0tIi/1747659331766-6f8fbe9e-335d-46e7-bf71-35a5a541318f-345527.png)

# Set the IP address field of the resource 
of resources **IP address** the value of the field comes from the core-sdk/schema/resource.go:181 in the collector. `RowField `the structure `Address `the setting of the field. Currently, only the string type is supported. 

![1736938590317-ea86f85c-a58f-49c2-8322-013d89a72191.png](./img/9dZ-MDxN8iNk0tIi/1736938590317-ea86f85c-a58f-49c2-8322-013d89a72191-111765.png)



The following figure shows how to set the Address field for an Alibaba Cloud ECS instance

![1736938873407-6a6f969c-036a-4c3f-8c5b-9de1580e0a2b.png](./img/9dZ-MDxN8iNk0tIi/1736938873407-6a6f969c-036a-4c3f-8c5b-9de1580e0a2b-992760.png)

# Set custom fields for an resource 
after clicking on the resource record to expand, the right **edit** button to jump to the custom field configuration page 

![1747659375890-14b62e10-7314-499c-97b0-ff17556e5e1b.png](./img/9dZ-MDxN8iNk0tIi/1747659375890-14b62e10-7314-499c-97b0-ff17556e5e1b-916504.png)

![1747659397222-e33f0145-8feb-4c38-a5f4-e1aee0824cc0.png](./img/9dZ-MDxN8iNk0tIi/1747659397222-e33f0145-8feb-4c38-a5f4-e1aee0824cc0-653805.png)



click on the right **edit** button to edit custom fields, click **Add field** add a custom field 

![1747661947928-ab05df3e-cd63-41a0-80fb-85bb5d8e6dee.png](./img/9dZ-MDxN8iNk0tIi/1747661947928-ab05df3e-cd63-41a0-80fb-85bb5d8e6dee-109846.png)



use jsonPath to get the value of the field, [jsonPath syntax description ](#yBJLR)below this section. Click the right Save button to complete the configuration 

![1747659487728-01dc0c80-55e6-48a2-aca7-a9110f67f3c8.png](./img/9dZ-MDxN8iNk0tIi/1747659487728-01dc0c80-55e6-48a2-aca7-a9110f67f3c8-837164.png)



at the top of the page, you are prompted that the save is successful and you can preview the value obtained by jsonPath. Click on the upper left corner **return** arrow returns to the resource information interface. 

![1747659566076-f4cc130e-2492-44e7-bc3e-505f24d5bd66.png](./img/9dZ-MDxN8iNk0tIi/1747659566076-f4cc130e-2492-44e7-bc3e-505f24d5bd66-821087.png)



You can see the custom fields configured using jsonPath in the resource details. **The configuration of the custom field takes effect for all records of the same resource type.**

![1747659628719-c1b72266-3096-4ca0-b5eb-17501bb65d2c.png](./img/9dZ-MDxN8iNk0tIi/1747659628719-c1b72266-3096-4ca0-b5eb-17501bb65d2c-088596.png)

### jsonPath Syntax simple description 
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
| JsonPath | Result | Value |
| --- | --- | --- |
| `$.store.bicycle.color` | The bicycle color | red |
| `$.store.book[*].author` | The authors of all books | ["Nigel Rees","Evelyn Waugh","Herman Melville","J. R. R. Tolkien"] |
| `$..author` | All authors | ["Nigel Rees","Evelyn Waugh","Herman Melville","J. R. R. Tolkien"] |
| `$.store..price` | The price of everything | [8.95,12.99,8.99,22.99,19.95] |
| `$..book.length()` | The number of books | 4 |


Reference documentation: [JsonPath GitHub](https://github.com/json-path/JsonPath)
