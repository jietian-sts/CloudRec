// Licensed to the Apache Software Foundation (ASF) under one or more
// contributor license agreements.  See the NOTICE file distributed with
// this work for additional information regarding copyright ownership.
// The ASF licenses this file to You under the Apache License, Version 2.0
// (the "License"); you may not use this file except in compliance with
// the License.  You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package schema

import (
	"encoding/json"
	"fmt"
	"github.com/core-sdk/constant"
	"github.com/core-sdk/log"
	"io"
	"io/ioutil"
	"net/http"
	"strings"
	"time"
)

type Client struct {
	Platform        string
	Sites           []string
	ServerUrl       string
	OnceToken       string
	PersistentToken string
}

func NewClientWithPersistentToken(platform string, sites []string, serverUrl string, persistentToken string) *Client {
	c := &Client{
		Platform:        platform,
		Sites:           sites,
		ServerUrl:       serverUrl,
		PersistentToken: persistentToken,
	}
	return c
}

func NewClientWithOnceToken(platform string, serverUrl string, onceToken string) *Client {
	c := &Client{
		Platform:  platform,
		ServerUrl: serverUrl,
		OnceToken: onceToken,
	}
	return c
}

// SendSupportResourceType 发送 agent 支持的资产类型
func (c *Client) SendSupportResourceType(registryValue, platform string, resourceList []SupportResource) {
	t := time.NewTimer(time.Second * 10)
	defer t.Stop()

	req := &SupportResourceTypeListRequest{
		Platform:      platform,
		PlatformName:  constant.GetPlatformName(platform),
		RegistryValue: registryValue,
		ResourceList:  resourceList,
	}

	param, err := json.Marshal(req)
	if err != nil {
		return
	}
	resp, err := c.postWithPersistentToken("/api/agent/acceptSupportResourceType", string(param), c.PersistentToken)
	if err != nil {
		return
	}

	defer func(Body io.ReadCloser) {
		err := Body.Close()
		if err != nil {
			return
		}
	}(resp.Body)
	body, err := ioutil.ReadAll(resp.Body)
	res := &res{}
	_ = json.Unmarshal(body, &res)
	if res.Code != constant.SuccessCode {
		log.GetWLogger().Error("send support resource type error")
		return
	}
	return
}

// LoadAccountFromServer Get cloud account information from the server
func (c *Client) LoadAccountFromServer(registryValue string) (cloudAccountList []CloudAccount) {
	t := time.NewTimer(time.Second * 60)
	defer t.Stop()
	req := &AccountParam{
		Platform:      c.Platform,
		RegistryValue: registryValue,
	}

	if len(c.Sites) != 0 {
		req.Sites = c.Sites
	}

	param, err := json.Marshal(req)
	if err != nil {
		return nil
	}
	resp, err := c.postWithPersistentToken("/api/agent/listCloudAccount", string(param), c.PersistentToken)
	if err != nil {
		return nil
	}

	defer func(Body io.ReadCloser) {
		err := Body.Close()
		if err != nil {
			return
		}
	}(resp.Body)
	body, err := ioutil.ReadAll(resp.Body)
	res := &res{}
	_ = json.Unmarshal(body, &res)
	if res.Code != constant.SuccessCode {
		if res.Msg != nil {
			log.GetWLogger().Error(fmt.Sprintf("load account from server error: %s", res.Msg))
		}
		return nil
	}

	cloudAccountList, err = GetCloudAccountAuthenticator(res.Content)

	return
}

// SendRunningFinishSignal 发送运行结束信号
func (c *Client) SendRunningFinishSignal(cloudAccountId string) (err error) {
	t := time.NewTimer(time.Second * 10)
	defer t.Stop()
	paramMap := make(map[string]interface{}, 1)
	paramMap["cloudAccountId"] = cloudAccountId
	param, err := json.Marshal(paramMap)
	if err != nil {
		return
	}

	resp, err := c.postWithPersistentToken("/api/agent/acceptRunningFinishSignal", string(param), c.PersistentToken)

	if err != nil {
		log.GetWLogger().Error(fmt.Sprintf("runningFinishSignal error: %s", err.Error()))
		return
	}
	defer resp.Body.Close()

	return err
}

// postWithOnceToken
func (c *Client) postWithOnceToken(action, body, onceToken string) (resp *http.Response, err error) {
	request, err := http.NewRequest("POST", c.ServerUrl+action, strings.NewReader(body))
	if err != nil {
		return nil, err
	}
	request.Header.Set("Content-Type", "application/json;charset=UTF-8")
	request.Header.Set("ONCETOKEN", onceToken)
	client := http.Client{
		Timeout: 60 * time.Second,
	}
	return client.Do(request)
}

// postWithPersistentToken
func (c *Client) postWithPersistentToken(action, body, persistentToken string) (resp *http.Response, err error) {
	request, err := http.NewRequest("POST", c.ServerUrl+action, strings.NewReader(body))
	if err != nil {
		return nil, err
	}
	request.Header.Set("Content-Type", "application/json;charset=UTF-8")
	request.Header.Set("PERSISTENTTOKEN", persistentToken)
	client := http.Client{
		Timeout: 60 * time.Second,
	}
	return client.Do(request)
}

func (c *Client) SendResource(cloudAccount CloudAccount, resource Resource, resourceInstanceList []*ResourceInstance, version string) {
	dataPushRequest := DataPushRequest{
		Platform:             c.Platform,
		Version:              version,
		CloudAccountId:       cloudAccount.CloudAccountId,
		PlatformName:         constant.GetPlatformName(c.Platform),
		ResourceType:         resource.ResourceType,
		ResourceTypeName:     resource.ResourceTypeName,
		ResourceGroupType:    resource.ResourceGroupType,
		DocLink:              resource.Desc,
		ResourceInstancesAll: resourceInstanceList,
	}

	req, err := json.Marshal(dataPushRequest)
	if err != nil {
		log.GetWLogger().Error(fmt.Sprintf("sendResource error: %s", err.Error()))
		return
	}

	paramMap := make(map[string]interface{}, 1)
	paramMap["data"] = string(req)
	param, err := json.Marshal(paramMap)
	if err != nil {
		return
	}

	resp, err := c.postWithPersistentToken("/api/agent/resource", string(param), c.PersistentToken)
	if err != nil {
		log.GetWLogger().Error(fmt.Sprintf("sendResource error: %s", err.Error()))
		return
	}
	defer func(Body io.ReadCloser) {
		err := Body.Close()
		if err != nil {
			log.GetWLogger().Error(err.Error())
		}
	}(resp.Body)
	_, err = ioutil.ReadAll(resp.Body)
	if err != nil {
		log.GetWLogger().Error(fmt.Sprintf("Error reading response body: %s", err.Error()))
	}

	log.GetWLogger().Info(fmt.Sprintf("CloudAccountId %s Submit %d %s resource data to the server %s successfully", cloudAccount.CloudAccountId, len(resourceInstanceList), resource.ResourceType, c.ServerUrl))
}
