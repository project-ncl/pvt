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

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by rnc on 12/08/16.
 */
public class Configuration
{
    private String product;

    private String version;

    /**
     * The path the dist store
     */
    private String distrepo;

    private Map<String,TestConfig> tests = new TreeMap<>();

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDistrepo() {
        return distrepo;
    }

    public void setDistrepo(String distrepo) {
        this.distrepo = distrepo;
    }

    public Map<String, TestConfig> getTests() {
        return tests;
    }

    public TestConfig getTestConfig(String clazz) {
        return tests.get(clazz);
    }

    public TestConfig getTestConfig(Class clazz) {
        return tests.get(clazz.getName());
    }

    public void setTests(Map<String, TestConfig> tests) {
        this.tests = tests;
    }
}
