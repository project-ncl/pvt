package org.jboss.pvt.harness.configuration;

import java.io.File;
import java.util.Map;
import java.util.Properties;

/**
 * Created by rnc on 12/08/16.
 */
public abstract class PVTConfiguration
{
    public abstract File getDistribution();

    public abstract File getDistributionDirectory();

    public abstract File getRepositoryDirectory();

    //TODO: Define type of configuration (Properties, JSON or YAML).
    public abstract Properties getAllConfiguration();

    public String getConfiguration(String key){
        return getAllConfiguration().getProperty(key);
    }

    public String getConfiguration(Class testClass, String key){
        return getAllConfiguration().getProperty(testClass.getName() + "." + key);
    }

    public Properties getConfigurationsOfTest(Class testClass){
        Properties prop = new Properties();
        for(Object key : getAllConfiguration().keySet()) {
            if(key.toString().startsWith(testClass.getName())) {
                prop.setProperty(key.toString(), getAllConfiguration().getProperty(key.toString()));
            }
        }
        return prop;
    }

    public String[] getArrayConfiguration(Class testClass, String key) {
        return getConfiguration(testClass, key).split(",");
    }
}
