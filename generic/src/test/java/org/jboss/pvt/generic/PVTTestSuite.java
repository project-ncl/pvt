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

import org.apache.commons.io.FileUtils;
import org.jboss.pvt.harness.configuration.YAMLConfigurationLoader;
import org.jboss.pvt.harness.configuration.pojo.Configuration;
import org.jboss.pvt.harness.exception.PVTSystemException;
import org.junit.Ignore;
import org.junit.extensions.dynamicsuite.Directory;
import org.junit.extensions.dynamicsuite.Filter;
import org.junit.extensions.dynamicsuite.TestClassFilter;
import org.junit.extensions.dynamicsuite.suite.DynamicSuite;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

@RunWith(DynamicSuite.class)
//@Filter(DefaultFilter.class)
@Filter(PVTTestSuite.class)
//@Sort( SortBy.TESTNAME)
@Directory
/**
 * Standard test suite to share amongst all of the tests. Automatically finds and runs all
 * relevant tests. Also encapsulates a standard per-suite Configuration.
 */
public class PVTTestSuite implements TestClassFilter
{

    // use this property to specify config file, instead of default pvt.yaml, support http://
    private static final String PROPERTY_CONFIG = "pvt.config";
    private static final String PROPERTY_VERSION = "pvt.version";

    public static String configFile = "pvt.yaml";

    public static Configuration configuration;

    private static Logger logger = LoggerFactory.getLogger(PVTTestSuite.class);

    public PVTTestSuite() {
        init();
    }

    private void init() throws PVTSystemException {


        if(System.getProperty(PROPERTY_CONFIG) != null && !System.getProperty(PROPERTY_CONFIG).trim().isEmpty()) {
            configFile = System.getProperty(PROPERTY_CONFIG).trim();
        }
        logger.info("Config file: " + configFile);
        File confFile = new File(configFile);
        if(configFile.startsWith("http://")){
            try {
                FileUtils.copyURLToFile(new URL(configFile), confFile);
            }
            catch (Exception e) {
                throw new PVTSystemException(e);
            }
        }
        else {
            confFile = FileUtils.toFile(PVTTestSuite.class.getResource("/" + configFile));
        }
        logger.info("Config file loaded: " + confFile);
        configuration = new YAMLConfigurationLoader().loadConfig(confFile);

        if(System.getProperty(PROPERTY_VERSION) != null && !System.getProperty(PROPERTY_VERSION).trim().isEmpty()) {
            // override the version defined in config file
            configuration.setVersion(System.getProperty(PROPERTY_VERSION).trim());
        }
    }

    public static Configuration getConfiguration(){
        return configuration;
    }


    @Override
    public boolean include( String s )
    {
        for(String testClassName : configuration.getTests().keySet() ){
            if(s.equals(testClassName)) {
                logger.info ("Including Test case " + s);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean include( Class aClass )
    {
        for(String testClassName : configuration.getTests().keySet() ){
            if(aClass.getName().equals(testClassName)) {
                logger.info ("Including Test case class " + aClass.getName());
                return aClass.getAnnotation(Ignore.class) == null;
            }
        }
        return false;
    }
}
