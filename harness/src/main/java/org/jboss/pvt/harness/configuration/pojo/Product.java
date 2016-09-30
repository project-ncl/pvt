package org.jboss.pvt.harness.configuration.pojo;

/**
 * Created by rnc on 27/09/16.
 */
public class Product
{
    private String name;
    private String version;
    private String milestone;

/*    public Product( String name, String version, String milestone )
    {
        this.name = name;
        this.version = version;
        this.milestone = milestone;
    }
*/
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getMilestone()
    {
        return milestone;
    }

    public void setMilestone(String milestone)
    {
        this.milestone = milestone;
    }
}
