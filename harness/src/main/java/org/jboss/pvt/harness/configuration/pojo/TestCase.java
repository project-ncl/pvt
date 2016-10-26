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
 * Created by rnc on 27/09/16.
 */
public class TestCase
{
    /**
     * Contain a list of strings that should be filtered out from this test case.
     */
    @Getter @Setter
    private List<String> filters = new ArrayList<>(  );

    /**
     * Denote if this entire test should be excluded for the product
     */
    @Getter @Setter
    private boolean exclusion;

    @Getter @Setter
    private String versions;

}
