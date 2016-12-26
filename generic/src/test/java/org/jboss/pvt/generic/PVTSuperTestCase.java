package org.jboss.pvt.generic;

import org.jboss.pvt.harness.configuration.pojo.Configuration;
import org.jboss.pvt.harness.configuration.pojo.TestConfig;
import org.jboss.pvt.harness.validators.Validator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The PVT super test case, all PVT test case class should inherit this class
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public abstract class PVTSuperTestCase {

    private Logger logger = LoggerFactory.getLogger( getClass() );

    @BeforeClass
    public static void __beforeClass__() {

    }

    @AfterClass
    public static void __afterClass__() {

    }

    @Before
    public void __before__() {
        logger.debug(PVTTestSuite.getConfiguration().getTestConfig(this.getClass().getName()).toString());
    }

    @After
    public void __after__() {
        logger.debug(PVTTestSuite.getConfiguration().getTestConfig(this.getClass().getName()).toString());
    }

    protected abstract Class<? extends Validator> getValidatorClass();

    protected boolean test() throws Exception{
        Configuration configuration = PVTTestSuite.getConfiguration();
        TestConfig testConfig = configuration.getTestConfig(this.getClass());
        List<String> resources = testConfig.getResources();

        List<String> fullpathResources = new ArrayList<>();
        for(String resource : resources) {
            String fullpath = configuration.getDistrepo() + "/" + resource;
            fullpath = fullpath.replace("%{version}", configuration.getVersion());
            fullpathResources.add(fullpath);
        }

        List<String> filters = testConfig.getFilters();
        Map<String, String> params = testConfig.getParams();
        return getValidatorClass().newInstance().validate(fullpathResources, filters, params);
    }
}
