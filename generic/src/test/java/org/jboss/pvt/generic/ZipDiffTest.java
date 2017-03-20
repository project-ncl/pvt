package org.jboss.pvt.generic;

import org.jboss.pvt.harness.configuration.pojo.Configuration;
import org.jboss.pvt.harness.configuration.pojo.TestConfig;
import org.jboss.pvt.harness.validators.Validator;
import org.jboss.pvt.harness.validators.ZipDiffValidator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class ZipDiffTest extends PVTSuperTestCase {

    @Test
    public void testZipDiff() throws Exception {
        Assert.assertTrue(test());
    }

    /**
     * support multiple resources, resources = ['a0.zip,b0.zip','a1.zip,b1.zip'];
     * guess previous version
     */
    @Override
    protected TestConfig parseTestConfig() {
        Configuration configuration = PVTTestSuite.getConfiguration();
        TestConfig testConfig = configuration.getTestConfig(this.getClass());

        List<String> resources = testConfig.getResources();
        if(resources == null || resources.isEmpty()) {
            throw new IllegalArgumentException("resources");
        }
        if(resources.size() == 1){
            String leftZip = ""; //TODO: guess leftZips
            resources.add(0, leftZip);
        }
        else if(resources.size() ==2){
            if(resources.get(0).contains(",") && resources.get(1).contains(",")){
                resources.set(0, parseZipsString(resources.get(0).split(","),configuration.getDistrepo()));
                resources.set(1, parseZipsString(resources.get(1).split(","),configuration.getDistrepo()));
            }
        }
        else {
            throw new IllegalArgumentException("Need 1 or 2 resources.");
        }
        return super.parseTestConfig();
    }

    private String parseZipsString(String[] zips, String distRepo){
        String[] fullPathZips = new String[zips.length];
        for(int i=0; i<zips.length; i++){
            String fullpath = zips[i].trim();
            if(!fullpath.contains("://")) { // relative path
                fullpath = distRepo + (distRepo.endsWith("/") ? "" : "/") + fullpath;
            }
            fullPathZips[i] = fullpath;
        }
        return String.join(",", fullPathZips);
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
