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
	"fmt"
	"github.com/google/uuid"
	"net"
	"os"
)

func LocalIP() string {
	ips, _ := LocalIPv4s()
	return ips[0]
}

func GenerateRegistryValue() string {
	ips, err := LocalIPv4s()
	if err != nil {
		fmt.Println("not found local ip")
	}
	hostname, err := os.Hostname()
	if err != nil {
		fmt.Println("not found hostname")
	}

	newUUID, _ := uuid.NewUUID()
	return hostname + "_" + ips[0] + "_" + newUUID.String()
}

func LocalIPv4s() ([]string, error) {
	var ips []string
	addrs, err := net.InterfaceAddrs()
	if err != nil {
		return ips, err
	}

	for _, a := range addrs {
		if ipnet, ok := a.(*net.IPNet); ok && !ipnet.IP.IsLoopback() && ipnet.IP.To4() != nil {
			ips = append(ips, ipnet.IP.String())
		}
	}

	return ips, nil
}
