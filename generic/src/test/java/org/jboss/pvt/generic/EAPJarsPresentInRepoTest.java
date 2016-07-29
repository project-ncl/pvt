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

package org.jboss.pvt.generic;

import org.jboss.pvt.harness.AbstractProductJarsPresentInRepo;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;

/**
 * Created by yyang on 7/11/16.
 */
@Ignore //TODO : Remove Junit categories and make test generic.
@Category({EAP7.class, EAP6.class})
public class EAPJarsPresentInRepoTest extends AbstractProductJarsPresentInRepo{

    @Override
    protected File getRepoDir() {
        return new File(EAP7TestSuite.getTestConfig().getRepoDir());
    }

    @Override
    protected File getProductDir() {
        return new File(EAP7TestSuite.getTestConfig().getEapDir());
    }

    @Override
    protected String[] getJarIgnoreFilter() {
        return new String[]{"jboss-modules.jar", "jboss-cli-client.jar", "launcher.jar", "jboss-client.jar", "jboss-seam-int.jar", "-jandex.jar"};
    }
}
