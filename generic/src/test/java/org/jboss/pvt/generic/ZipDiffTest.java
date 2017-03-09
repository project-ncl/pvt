package org.jboss.pvt.generic;

import org.jboss.pvt.harness.configuration.pojo.TestConfig;
import org.jboss.pvt.harness.validators.Validator;
import org.jboss.pvt.harness.validators.ZipDiffValidator;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class ZipDiffTest extends PVTSuperTestCase {

    @Test
    public void testZipDiff() throws Exception {
        Assert.assertTrue(test());
    }

    @Override
    protected TestConfig parseTestConfig() {
        TestConfig testConfig = super.parseTestConfig();
        List<String> resources = testConfig.getResources();
        List<String> parsedResources = testConfig.getParsedResources();
        if(parsedResources.size() == 1){
            String leftZip = ""; //TODO:
            resources.add(0, leftZip);
        }
        return testConfig;
    }

    private String guessPreviousVersion(String version) {
        return version;
    }

    @Override
    protected Class<? extends Validator> getValidatorClass() {
        return ZipDiffValidator.class;
    }

    @Override
    public String getDescription() {
        return "Test to generate Zip diff report.";
    }
}
