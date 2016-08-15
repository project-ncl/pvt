package org.jboss.pvt.harness.configuration;

import java.io.File;
import java.util.Properties;

/**
 * Created by rnc on 12/08/16.
 */
public interface PVTConfiguration
{
    File getDistribution();

    File getDistributionDirectory();

    File getRepositoryDirectory();

    //TODO: Define type of configuration (Properties, JSON or YAML).
    Properties getAllConfiguration();

    /**
     * Return any filters from the configuration for the test.
     * @param testName Name of the test.
     * @return current set of filters.
     */
    String[] getTestFilter (String testName);
}
