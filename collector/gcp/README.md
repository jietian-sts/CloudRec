## test

## Use the ak and sk entered in the code, not get them from the server,You can use this method to unit test

```go
var GetTestAccount = func() (res []schema.CloudAccount) {
	testAccount := schema.CloudAccount{
		CloudAccountId: "you need input your CloudAccountId",
		AK:             "you need input your AK",
		SK:             "you need input your SK",
	}

	res = append(res, testAccount)

	return res
}


// TestGetEcsData Test to get ecs instance data, normal case
func TestGetEcsData(t *testing.T) {

	p := schema.GetInstance(schema.PlatformConfig{
		Name: string(constant.AlibabaCloud),
		Resources: []schema.Resource{
			GetEcsData(),
		},

		Service:              &collector.Services{},
		DefaultRegions:       []string{"cn-hangzhou"},
		DefaultCloudAccounts: GetTestAccount(),
	})

	if err := schema.RunExecutor(p); err != nil {
		log.GetWLogger().Error(err.Error())
	}
}


// TestGetEcsDataUserErrRegions Test to get ecs instance data, abnormal case use not exist region
func TestGetEcsDataUserErrRegions(t *testing.T) {

	p := schema.GetInstance(schema.PlatformConfig{
		Name: string(constant.AlibabaCloud),
		Resources: []schema.Resource{
			GetEcsData(),
		},

		Service:              &collector.Services{},
		DefaultRegions:       []string{"cn-hangzhou-not-exist"},
		DefaultCloudAccounts: GetTestAccount(),
	})

	if err := schema.RunExecutor(p); err != nil {
		log.GetWLogger().Error(err.Error())
	}
}

```

You can run the sample code here > lunar_collector/test/provider/ecs ecs_test.go
