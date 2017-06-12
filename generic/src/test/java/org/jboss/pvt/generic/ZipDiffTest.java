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

        String compareVersion = parseCompareVersion(configuration);
        for(String resource : resources) {
            String pairResource = parsePairResourceFullPath(resource, configuration.getDistrepo(), configuration.getVersion(), compareVersion);
            parsedResource.add(pairResource);
        }

        testConfig.setParsedResources(parsedResource);
        return testConfig;
    }

    private String parsePairResourceFullPath(String resource, String distRepo, String version, String compareVersion){
        String thisResource;
        String compareResource;
        if(!resource.contains(",")) { //
            thisResource = resource;
            compareResource = resource;
        }
        else {
            thisResource = resource.split(",")[0];
            compareResource = resource.split(",")[1];
        }

        thisResource = getFullPathResource(thisResource, distRepo, version);
        compareResource = getFullPathResource(compareResource, distRepo, compareVersion);

        return String.join(", ", thisResource, compareResource);
    }

    private String getFullPathResource(String resource, String distRepo, String version){
        distRepo = distRepo.replace("%{version}", version);
        String fullpath = resource;
        if(!fullpath.contains("://")) { // relative path
            fullpath = distRepo + (distRepo.endsWith("/") ? "" : "/") + fullpath;
        }
        fullpath = fullpath.replace("%{version}", version).replace("%{target}", PVTTestSuite.getConfiguration().getTarget());
        return fullpath;
    }

    private String parseCompareVersion(Configuration configuration) {
        TestConfig testConfig = configuration.getTestConfig(this.getClass());
        String compareVersion = System.getProperty(ZipDiffTest.class.getName() + "." + ZipDiffValidator.PARAM_DIFF_VERSION);
        if(compareVersion != null && !compareVersion.trim().isEmpty()) {
            return compareVersion;
        }
        else {

            compareVersion = testConfig.getParams().get(ZipDiffValidator.PARAM_DIFF_VERSION);
            if (compareVersion != null && !compareVersion.trim().isEmpty()) {
                return compareVersion;
            } else {
                //TODO: auto detect the diffVersion
                throw new IllegalArgumentException("No compare defined.");
            }
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
