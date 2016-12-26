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

import org.jboss.pvt.harness.exception.PVTException;
import org.jboss.pvt.harness.validators.ProductJarsPresentInRepoValidator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by yyang on 7/11/16.
 */
public class JarsPresentInRepoTest
{

    private ProductJarsPresentInRepoValidator pjp = new ProductJarsPresentInRepoValidator();

    @Before
    public void checkResources(){

    }

    @Test
    public void test1() throws PVTException
    {
        // TODO: Should possibly come from a config file?
        // This should really be
        // pjp.initialiseFilter( pjp.getConfiguration().getTestFilter( testName.getMethodName() ) );
//        pjp.initialiseFilter(new String[]{"jboss-modules.jar", "jboss-cli-client.jar", "launcher.jar", "jboss-client.jar", "jboss-seam-int.jar", "-jandex.jar"} );

//        assertTrue ( pjp.validate(PVTTestSuite.yamlConfigLoader) );
    }
}
