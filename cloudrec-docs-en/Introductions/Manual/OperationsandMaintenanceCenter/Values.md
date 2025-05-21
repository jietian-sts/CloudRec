# Values

The variable management module is used to add, edit, and query global variables that can be used in Rego risk inspection rules. 

For example, using variable management, you can use the export IP list of an enterprise as a global variable in different Rego rules. 

![1737353197824-6a6a91e2-fdf3-4169-9178-3cb89e70e469.png](./img/ud3r1-L7ES_gVHH1/1737353197824-6a6a91e2-fdf3-4169-9178-3cb89e70e469-500394.png)



#### Addition of variables 
1. click on the right **new Variable **button to pop up the edit box for the new variable 
2. fill in `variable name `, `variable Path `, `variable value `
    1. variable Name: The name of the variable. It is recommended that the name be readable and meaningful. 
    2. Variable path: as the unique value of the variable, to prevent conflicts. Only names supported by Rego rule are allowed. For example, Chinese is not supported. 
    3. Variable value: the actual content of the variable, in json format. 
3. Click **determine **, complete the addition of variables 

![1737353487429-a38ef7d8-dad6-43ad-a47d-a162a88298a7.png](./img/ud3r1-L7ES_gVHH1/1737353487429-a38ef7d8-dad6-43ad-a47d-a162a88298a7-233208.png)

#### use of Variables 
1. when writing the Rego rule, click on the right. **Variable **label 

![1737354648856-f58c33d4-beab-4a3a-b926-d91b062cf65e.png](./img/ud3r1-L7ES_gVHH1/1737354648856-f58c33d4-beab-4a3a-b926-d91b062cf65e-425688.png)

2. to view and select the required variables, click **save **

![1737354415708-9e90471d-673e-415f-85cb-c76063ed2aa8.png](./img/ud3r1-L7ES_gVHH1/1737354415708-9e90471d-673e-415f-85cb-c76063ed2aa8-415395.png)

3. use `data.${variable path} `reference variable

![1737354526956-92119acb-b213-4481-a89c-6494faf5946f.png](./img/ud3r1-L7ES_gVHH1/1737354526956-92119acb-b213-4481-a89c-6494faf5946f-216551.png)





