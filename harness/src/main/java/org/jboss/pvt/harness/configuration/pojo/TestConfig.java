/*
 * Copyright (C) 2016 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.pvt.harness.configuration.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rnc on 27/09/16.
 */
public class TestConfig {

    /**
     * which resources this test will run again
     */
    private List<String> resources = new ArrayList<>(  );


    private List<String> parsedResources = new ArrayList<>();

    /**
     * Contain a list of strings that should be filtered out from this test case.
     * format: java regexp
     */
    private List<String> filters = new ArrayList<>(  );


    private Map<String, String> params = new HashMap<>();

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }


    public List<String> getParsedResources() {
        return parsedResources;
    }

    public void setParsedResources(List<String> parsedResources) {
        this.parsedResources = parsedResources;
    }

    @Override
    public String toString() {
        return "TestConfig{" +
                "resources=" + resources +
                ", filters=" + filters +
                ", params=" + params +
                '}';
    }

}
