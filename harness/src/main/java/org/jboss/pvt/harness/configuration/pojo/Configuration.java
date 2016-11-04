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

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rnc on 12/08/16.
 */
public class Configuration
{
    @Getter @Setter
    private Product product = new Product();

    @Getter @Setter
    private TestCase testCase = new TestCase();

    @Getter @Setter
    private String distribution;

    @Getter @Setter
    private String sourceDistribution;

    @Getter @Setter
    private String mavenRepository;

    @Getter @Setter
    private Map<String, TestCase> testCases = new HashMap<>();


    /**
     * This should be configured to be any other zip distributions supplied
     * by the product.
     */
    @Getter @Setter
    private List<String> auxilliaryDistributions = new ArrayList<>(  );
}
