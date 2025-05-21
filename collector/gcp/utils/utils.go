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

package utils

import (
	"strings"
)

func ParseValue(url string) string {
	lastSlashIndex := strings.LastIndex(url, "/")
	if lastSlashIndex != -1 {
		extracted := url[lastSlashIndex+1:]
		return extracted
	}

	return ""
}

func GetResourceID(url string) string {
	parts := strings.Split(url, "/")
	if parts[len(parts)-1] == "" {
		return ""
	}

	targetPart := parts[len(parts)-1]
	return targetPart
}

func GetResourceType(url string) string {
	parts := strings.Split(url, "/")
	if parts[len(parts)-1] == "" {
		return ""
	}

	targetPart := parts[len(parts)-2]
	return targetPart
}

func GetResourceRegion(url string) string {
	// https://compute.googleapis.com/compute/v1/projects/{project}/regions/{region}/addresses/{address}
	// https://compute.googleapis.com/compute/v1/projects/{project}/global/addresses/{address}

	parts := strings.Split(url, "/")
	if parts[len(parts)-1] == "" {
		return ""
	}

	targetPart := parts[len(parts)-3]
	return targetPart
}
