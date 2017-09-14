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
import org.jboss.pvt.harness.configuration.pojo.TestConfig;
import org.jboss.pvt.harness.exception.PVTSystemException;
import org.jboss.pvt.harness.reporting.Report;
import org.jboss.pvt.harness.reporting.Reporter;
import org.jboss.pvt.harness.reporting.TestReport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
import java.util.Map;

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
    private static final String PROPERTY_CONFIG = "product.config";
    private static final String PROPERTY_VERSION = "product.version";
    private static final String PROPERTY_TARGET = "product.target";
    private static final String REPORT_FILEPATH = "report.filepath";

    public static final String DEFAULT_OUTPUT_PATH = "../target/pvt_report_%{product}_%{version}";
    public static final String DEFAULT_OUTPUT_DIRECTORY = "../target";
    public static final String DEFAULT_OUTPUT_NAME = "/pvt_report_%{product}_%{version}";

   
    public static String configFile = "pvt.yaml";

    public static Configuration configuration;

    private static Logger logger = LoggerFactory.getLogger(PVTTestSuite.class);

    private static Report report;

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

        if(System.getProperty(PROPERTY_TARGET) != null && !System.getProperty(PROPERTY_TARGET).trim().isEmpty()) {
            // override the version defined in config file
            configuration.setTarget(System.getProperty(PROPERTY_TARGET).trim());
        }

        // init report
        report = new Report(configuration);
        for(Map.Entry<String, TestConfig> entry : configuration.getTests().entrySet()){
            report.addTestReport(new TestReport(entry.getKey(), entry.getValue()));
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

    @BeforeClass
    public static void setUp() {
        logger.info("TestSuite setting up");
    }

    @AfterClass
    public static void tearDown() {
        logger.info("TestSuite tearing down");
        String out_file;
        try {
            logger.info("Report rendering");
            if(System.getProperty(REPORT_FILEPATH) != null && !System.getProperty(REPORT_FILEPATH).trim().isEmpty()) {
                String file_path = System.getProperty(REPORT_FILEPATH).trim();
                File directory = new File(file_path);
                
                //special case for ending with the char '/'. if it is a directory,clear it for convenience
                //if not, report error
                if(file_path.charAt(file_path.length()-1)=='/'){
                    file_path = file_path.substring(0,file_path.length()-1);
                    directory = new File(file_path);
                    // if not a directory
                    if(!directory.isDirectory()){
                        logger.info("failed to access given directory");
                        throw new Exception();
                    }
                }
                
                // check if directory is provided. If the case, we use given directory + default name
                if(directory.isDirectory()){
                    out_file = file_path.concat(DEFAULT_OUTPUT_NAME);
                    out_file = replace_property(report, out_file);                   
                }
                else{
                     //check if the case: directory and name are both provided
                     if(file_path.indexOf('/')>=0){
                        String sub_location = file_path.substring(0,file_path.lastIndexOf('/'));
                        if(new File(sub_location).isDirectory()){
                            out_file = file_path;
                        }
                        else{
                            logger.info("failed to access given directory");
                            throw new Exception();
                        }
                     }
                     //if the case:name only or no '/' given, we use default directory + given name
                     else 
                     {
                        //Add in default directory
                        out_file = DEFAULT_OUTPUT_DIRECTORY.concat(file_path);
                     }                   
                }
            }
            //if nothing is provided, use default directory + default name
            else{
                out_file= DEFAULT_OUTPUT_PATH;
                out_file=replace_property(report, out_file);
            }
            Reporter.getFreemarkerReporter().render(report,out_file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String replace_property(Report report, String name) {
        name = name.replace("%{product}", report.getConfiguration().getProduct())
                .replace("%{version}",report.getConfiguration().getVersion())
                .replace("%{target}",report.getConfiguration().getTarget());
                //.replace("%{date}", new SimpleDateFormat("yyMMdd").format(new Date()));
        return name;
    }

    public static TestReport getTestReport(Class testClass) {
        return report.getTestReport(testClass);
    }
}

