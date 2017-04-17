package org.jboss.pvt.generic;

import org.jboss.pvt.harness.configuration.pojo.Configuration;
import org.jboss.pvt.harness.configuration.pojo.TestConfig;
import org.jboss.pvt.harness.validators.Validator;
import org.jboss.pvt.harness.validators.ZipDiffValidator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class ZipDiffTest extends PVTSuperTestCase {

/* config example
org.jboss.pvt.generic.ZipDiffTest:
        resources: ['jboss-brms-%{diffVersion}-deployable-eap6.x.zip, jboss-brms-%{diffVersion}-engine.zip','jboss-brms-6.4.0.GA-deployable-eap6.x.zip, jboss-brms-6.4.0.GA-engine.zip']
        filters: ['.*class','.*js']
        params: {diffVersion: '6.4.0.CR1', expectChanges: '', expectAdds : '', expectRemoves : '', expectUnchanges: '', expectChangeCount: '1,100', expectAddCount: '1,300', expectRemoveCount: '1,400', expectUnchangeCount: '3,400', }
*/

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
        List<String> parsedResource = new ArrayList<>();
        if(resources == null || resources.isEmpty()) {
            throw new IllegalArgumentException("resources");
        }

        String diffVersion = guessDiffVersion(configuration);
        // previous resources
        parsedResource.add(0, parseZipsFullPath(resources.get(0),configuration.getDistrepo(), diffVersion));
        // current resources
        parsedResource.add(1, parseZipsFullPath(resources.get(resources.size()-1),configuration.getDistrepo(), configuration.getVersion()));

        testConfig.setParsedResources(parsedResource);
        return testConfig;
    }

    private String parseZipsFullPath(String resources, String distRepo, String version){
        String[] zips = resources.split(",");
        String[] fullPathZips = new String[zips.length];
        distRepo = distRepo.replace("%{version}", version);
        for(int i=0; i<zips.length; i++){
            String fullpath = zips[i].trim();
            if(!fullpath.contains("://")) { // relative path
                fullpath = distRepo + (distRepo.endsWith("/") ? "" : "/") + fullpath;
            }
            fullpath = fullpath.replace("%{version}", version);
            fullpath = fullpath.replace("%{diffVersion}", version);
            fullPathZips[i] = fullpath;
        }
        return String.join(",", fullPathZips);
    }

    private String guessDiffVersion(Configuration configuration) {
        TestConfig testConfig = configuration.getTestConfig(this.getClass());
        String diffVersion = testConfig.getParams().get(ZipDiffValidator.PARAM_DIFF_VERSION);
        if(diffVersion != null && !diffVersion.trim().isEmpty()) {
            return diffVersion;
        }
        else {
            //TODO: auto detect the diffVersion
            throw new IllegalArgumentException("No diffVersion defined");
        }
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
