package org.jboss.pvt.harness;

import org.jboss.pvt.harness.configuration.DefaultConfiguration;
import org.jboss.pvt.harness.configuration.PVTConfiguration;
import org.jboss.pvt.harness.exception.PVTSystemException;
import org.jboss.pvt.harness.utils.HttpUtils;
import org.jboss.pvt.harness.utils.ZipUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * Created by rnc on 12/08/16.
 */
public class TestConfiguration implements PVTConfiguration, TestRule
{

    // TODO: extend ExternalResource to make a JUnit Rule and setup e.g. nested temporary file structure
    // to represent a mock configuration.
    // Possibly use a RuleChain to use other rules e.g. TemporaryFolder to setup a dummy 'unpacked' distribution
    // zip.

//    public static String baseDir = "dist";
    public static final String PROPERTIES_CONFIG_FILE = "pvt-eap7.properties";

    private File distribution;
    private File distributionDirectory;
    private File repository;
    private File repositoryDirectory;

    private Properties properties = new Properties();

    @Override
    public File getDistribution()
    {
        return distribution;
    }

    @Override
    public File getDistributionDirectory()
    {
        return distributionDirectory;
    }

    @Override
    public  File getRepository() {
        return repository;
    }

    @Override
    public File getRepositoryDirectory()
    {
        return repositoryDirectory;
    }

    @Override
    public Properties getAllConfiguration()
    {
        return properties;
    }

    /**
     * Return any filters from the configuration for the test.
     * @param testName Name of the test.
     * @return current set of filters.
     */
    @Override
    public String[] getTestFilter( String testName )
    {
        return new String[0];
    }

    @Override
    public String getConfiguration(Class testClass, String key)
    {
        return getAllConfiguration().getProperty(testClass.getName() + "." + key);
    }

    @Override
    public String getConfiguration(String key)
    {
        return getAllConfiguration().getProperty(key);
    }


    @Override
    public String[] getArrayConfiguration(Class testClass, String key)
    {
        String res = getConfiguration( testClass, key );
        if ( isNotEmpty (res) )
        {
            return res.split(",");
        }
        else
        {
            return new String[0];
        }
    }

    /**
     * Modifies the method-running {@link Statement} to implement this
     * test-running rule.
     *
     * @param base The {@link Statement} to be modified
     * @param description A {@link Description} of the test implemented in {@code base}
     * @return a new statement, which may be the same as {@code base},
     *         a wrapper around {@code base}, or a completely new Statement.
     */
    public Statement apply(Statement base, Description description) {
        return statement(base);
    }

    private Statement statement(final Statement base) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                try {
                    base.evaluate();
                } finally {
                    after();
                }
            }
        };
    }

    /**
     * Override to set up your specific external resource.
     *
     * @throws Throwable if setup fails (which will disable {@code after}
     */
    protected void before() throws Throwable {
        // load properties
        if(properties.isEmpty()) {

            try {
                properties.load(new FileReader(PROPERTIES_CONFIG_FILE));
            } catch (Exception e) {
                throw new PVTSystemException("File to load config file: " + PROPERTIES_CONFIG_FILE, e);
            }
        }
        prepareResources();
    }

    /**
     * Override to tear down your specific external resource.
     */
    protected void after() {
        // do nothing
    }

    protected synchronized void prepareResources() throws Exception{

        if(distribution == null) {
            String distributionUrl = getConfiguration(PRODUCT_ZIP_URL_KEY);
            String filename = distributionUrl.substring(distributionUrl.lastIndexOf("/") + 1);
            distribution = new File(filename);
            if(!distribution.exists()) {
                distribution = HttpUtils.httpDownload(distributionUrl);
            }
            ZipUtils.unzip(distribution);
            String distributionDirectoryName = getConfiguration(PRODUCT_ZIP_NAME_KEY);
            if(!new File(distributionDirectoryName).exists()){
                throw new PVTSystemException("Distribution Directory " + distributionDirectoryName + " not exists!");
            }
            distributionDirectory = new File(distributionDirectoryName);
        }

        if(repository == null) {
            String repoUrl = getConfiguration(REPO_ZIP_URL_KEY);
            String filename = repoUrl.substring(repoUrl.lastIndexOf("/") + 1);
            repository = new File(filename);
            if(!repository.exists()) {
                repository = HttpUtils.httpDownload(repoUrl);
            }
            ZipUtils.unzip(repository);
            String repositoryDirectoryName = getConfiguration(REPO_ZIP_NAME_KEY);
            if(!new File(repositoryDirectoryName).exists()) {
                throw new PVTSystemException("Repository Directory " + repositoryDirectoryName + " not exists!");
            }
            repositoryDirectory = new File(repositoryDirectoryName);
        }

    }
}
