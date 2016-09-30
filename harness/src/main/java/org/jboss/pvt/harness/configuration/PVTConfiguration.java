package org.jboss.pvt.harness.configuration;

import org.jboss.pvt.harness.configuration.pojo.Product;
import org.jboss.pvt.harness.configuration.pojo.TestCase;

import java.io.File;
import java.util.List;

/**
 * Created by rnc on 12/08/16.
 */
public interface PVTConfiguration
{
    Product getProduct();

    File getDistributionDirectory();
    
    File getSourceDistribution();

    List<File> getAuxilliaryZips();

    TestCase getTestCase ( String key);

    File getMavenRepository();
}
