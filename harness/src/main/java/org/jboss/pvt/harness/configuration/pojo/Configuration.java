package org.jboss.pvt.harness.configuration.pojo;

import java.io.File;
import java.util.List;

/**
 * Created by rnc on 12/08/16.
 */
public class Configuration
{
    Product product = new Product();
    TestCase testCase = new TestCase();

    // TODO: Should these be strings to better handle URLs?
    File distributionDirectory;
    File sourceDistribution;
    File mavenRepository;
    List<File> auxilliaryZips;

    public File getDistributionDirectory()
    {
        return distributionDirectory;
    }

    public void setDistributionDirectory( File distributionDirectory )
    {
        this.distributionDirectory = distributionDirectory;
    }

    public File getSourceDistribution()
    {
        return sourceDistribution;
    }

    public void setSourceDistribution( File sourceDistribution )
    {
        this.sourceDistribution = sourceDistribution;
    }

    public File getMavenRepository()
    {
        return mavenRepository;
    }

    public void setMavenRepository( File mavenRepository )
    {
        this.mavenRepository = mavenRepository;
    }

    public List<File> getAuxilliaryZips()
    {
        return auxilliaryZips;
    }

    public void setAuxilliaryZips( List<File> auxilliaryZips )
    {
        this.auxilliaryZips = auxilliaryZips;
    }

    public TestCase getTestCase()
    {
        return testCase;
    }

    public void setTestCase( TestCase testCase )
    {
        this.testCase = testCase;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct( Product product )
    {
        this.product = product;
    }


    /*
    Product getProduct();

    File getDistributionDirectory();

    File getSourceDistribution();

    File[] getAuxilliaryZips();

    TestCase getTestCase( String key );

    File getMavenRepository();
*/
}
