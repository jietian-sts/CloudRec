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
	"testing"
)

func TestGetResouceID(t *testing.T) {

	// Test case 1
	url1 := "https://www.googleapis.com/compute/v1/projects/intense-emblem-404402/global/forwardingRules/lb-3yfa82kthq6zwfnmmq2t-6443-tcp"
	expected := "lb-3yfa82kthq6zwfnmmq2t-6443-tcp"
	if result := GetResourceID(url1); result != expected {
		t.Errorf("Expected '%s', got '%s'", expected, result)
	}

	// Test case 2
	url2 := ""
	expected2 := ""
	if result2 := GetResourceID(url2); result2 != expected2 {
		t.Errorf("Expected '%s', got '%s'", expected2, result2)
	}
}

func TestGetResouceType(t *testing.T) {

	// Test case 1
	url1 := "https://www.googleapis.com/compute/v1/projects/intense-emblem-404402/global/forwardingRules/lb-3yfa82kthq6zwfnmmq2t-6443-tcp"
	expected := "forwardingRules"
	if result := GetResourceType(url1); result != expected {
		t.Errorf("Expected '%s', got '%s'", expected, result)
	}

	// Test case 2
	url2 := ""
	expected2 := ""
	if result2 := GetResourceType(url2); result2 != expected2 {
		t.Errorf("Expected '%s', got '%s'", expected2, result2)
	}
}

func TestGetResouceRegion(t *testing.T) {

	// Test case 1
	url1 := "https://www.googleapis.com/compute/v1/projects/intense-emblem-404402/global/forwardingRules/lb-3yfa82kthq6zwfnmmq2t-6443-tcp"
	expected := "global"
	if result := GetResourceRegion(url1); result != expected {
		t.Errorf("Expected '%s', got '%s'", expected, result)
	}

	// Test case 2
	url2 := ""
	expected2 := ""
	if result2 := GetResourceRegion(url2); result2 != expected2 {
		t.Errorf("Expected '%s', got '%s'", expected2, result2)
	}

	// Test case 3
	url3 := "https://www.googleapis.com/compute/v1/projects/intense-emblem-404402/regions/region/forwardingRules/lb-3yfa82kthq6zwfnmmq2t-6443-tcp"
	expected3 := "region"
	if result3 := GetResourceRegion(url3); result3 != expected3 {
		t.Errorf("Expected '%s', got '%s'", expected3, result3)
	}
}
