## baidu cloud

## Use the ak and sk entered in the code, not get them from the server,You can use this method to unit test

```go

var GetTestAccount = func() (res []schema.CloudAccount) {
	testAccount := schema.CloudAccount{
		CloudAccountId: "test-account",
		CommonCloudAccountAuthenticate: schema.CommonCloudAccountAuthenticate{
			AK: "AK",
			SK: "SK",
		},
	}

	res = append(res, testAccount)

	return res
}

func TestGetResource(t *testing.T) {
	p := schema.GetInstance(schema.PlatformConfig{
		Name: string(constant.BaiduCloud),
		Resources: []schema.Resource{
			GetResource(),
		},

		Service:              &collector.Services{},
		DefaultRegions:       []string{"xxx"},
		DefaultCloudAccounts: GetTestAccount(),
	})

	if err := schema.RunExecutor(p); err != nil {
		log.GetWLogger().Error(err.Error())
	}
}
```

You can run the sample code here > lunar_collector/test/provider/ecs ecs_test.go
