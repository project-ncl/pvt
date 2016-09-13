package org.jboss.pvt.harness.configuration;

import java.io.File;
import java.util.Properties;

/**
 * Created by rnc on 12/08/16.
 */
public interface PVTConfiguration
{
    public static final String PRODUCT_VERSION_KEY = "PRODUCT_VERSION";
    public static final String PRODUCT_VERSION_LAST_KEY = "PRODUCT_VERSION_LAST";
    public static final String PRODUCT_TARGET_RELEASE_VERSION = "PRODUCT_TARGET_RELEASE_VERSION";
    public static final String PRODUCT_ZIP_URL_KEY = "PRODUCT_ZIP_URL";
    public static final String PRODUCT_ZIP_NAME_KEY = "PRODUCT_ZIP_NAME";
    public static final String PRODUCT_DIR_KEY = "PRODUCT_DIR";
    public static final String PRODUCT_SRC_ZIP_URL_KEY = "PRODUCT_SRC_ZIP_URL";
    public static final String PRODUCT_SRC_ZIP_NAME_KEY = "PRODUCT_SRC_ZIP_NAME";
    public static final String PRODUCT_SRC_DIR_KEY ="PRODUCT_SRC_DIR";
    public static final String MEAD_REPO_URL_KEY = "MEAD_REPO_URL";
    public static final String REPO_ZIP_URL_KEY = "REPO_ZIP_URL";
    public static final String REPO_ZIP_NAME_KEY = "REPO_ZIP_NAME";
    public static final String REPO_DIR_KEY = "REPO_DIR";
    public static final String QUICKSTARTS_ZIP_URL_KEY = "QUICKSTARTS_ZIP_URL";
    public static final String QUICKSTARTS_ZIP_NAME_KEY = "QUICKSTARTS_ZIP_NAME";
    public static final String QUICKSTARTS_DIR_KEY = "QUICKSTARTS_DIR";

    File getDistribution();

    File getDistributionDirectory();

    File getRepository();

    File getRepositoryDirectory();

    //TODO: Define type of configuration (Properties, JSON or YAML).
    Properties getAllConfiguration();

    /**
     * Return any filters from the configuration for the test.
     * @param testName Name of the test.
     * @return current set of filters.
     */
    String[] getTestFilter (String testName);

    String[] getArrayConfiguration(Class testClass, String key);

    String getConfiguration(Class testClass, String key);

    String getConfiguration(String key);
}
