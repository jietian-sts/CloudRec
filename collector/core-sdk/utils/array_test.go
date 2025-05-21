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
	"reflect"
	"testing"
)

func TestExclude(t *testing.T) {
	type args[T comparable] struct {
		slice   []T
		exclude []T
	}
	type testCase[T comparable] struct {
		name string
		args args[T]
		want []T
	}
	tests := []testCase[string]{
		{
			name: "exclude",
			args: args[string]{
				slice:   []string{"a", "b", "c"},
				exclude: []string{"a", "b"},
			},
			want: []string{"c"},
		},
		{
			name: "exclude",
			args: args[string]{
				slice:   []string{"a", "b", "c"},
				exclude: []string{"d", "e"},
			},
			want: []string{"a", "b", "c"},
		},
		{
			name: "exclude",
			args: args[string]{
				slice:   []string{"a", "b", "c"},
				exclude: []string{},
			},
			want: []string{"a", "b", "c"},
		},
		{
			name: "exclude",
			args: args[string]{
				slice:   []string{},
				exclude: []string{"a", "b"},
			},
			want: nil,
		},
		{
			name: "exclude",
			args: args[string]{
				slice:   []string{"a", "b", "c"},
				exclude: nil,
			},
			want: []string{"a", "b", "c"},
		},
		{
			name: "exclude",
			args: args[string]{
				slice:   []string{"a", "b", "c"},
				exclude: []string{"a", "b", "c"},
			},
			want: nil,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := Exclude(tt.args.slice, tt.args.exclude); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("Exclude() = %v, want %v", got, tt.want)
			}
		})
	}
}
