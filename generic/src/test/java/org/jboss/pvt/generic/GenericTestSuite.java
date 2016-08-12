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

import org.jboss.pvt.harness.configuration.DefaultConfiguration;
import org.jboss.pvt.harness.configuration.PVTConfiguration;
import org.junit.Ignore;
import org.junit.extensions.dynamicsuite.Directory;
import org.junit.extensions.dynamicsuite.Filter;
import org.junit.extensions.dynamicsuite.Sort;
import org.junit.extensions.dynamicsuite.SortBy;
import org.junit.extensions.dynamicsuite.TestClassFilter;
import org.junit.extensions.dynamicsuite.suite.DynamicSuite;
import org.junit.runner.RunWith;

@RunWith(DynamicSuite.class)
//@Filter(DefaultFilter.class)
@Filter(GenericTestSuite.class)
@Sort( SortBy.TESTNAME)
@Directory
/**
 * Standard test suite to share amongst all of the tests. Automatically finds and runs all
 * relevant tests. Also encapsulates a standard per-suite Configuration.
 */
public class GenericTestSuite implements TestClassFilter
{
    public static final PVTConfiguration configuration = new DefaultConfiguration();

    // TODO: Remove this and revert to default filter. Entered to verify it works.
    @Override
    public boolean include( String s )
    {
        System.out.println ("### DEBUG: Including " + s + ':' + s.endsWith( "Test" ));
        return s.endsWith( "Test" );
    }

    @Override
    public boolean include( Class aClass )
    {
        System.out.println ("### DEBUG: Including class " + aClass);
        return aClass.getAnnotation(Ignore.class) == null;
    }
}
