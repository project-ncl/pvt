package org.jboss.pvt.harness;

import org.jboss.pvt.harness.configuration.DefaultConfiguration;
import org.jboss.pvt.harness.configuration.pojo.Configuration;
import org.jboss.pvt.harness.exception.PVTSystemException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Properties;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * Created by rnc on 12/08/16.
 */
public class TestConfiguration extends DefaultConfiguration implements TestRule
{
    private Logger logger = LoggerFactory.getLogger( getClass() );

    protected void init ()
    {
        URL url = this.getClass().getResource( "/pvt.yaml");
        File pCfg = new File(url.getFile());

        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(representer);

        System.out.println ("### pCfg" + pCfg);
        try
        {
            config = yaml.loadAs( new FileInputStream( pCfg ), Configuration.class );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            throw new PVTSystemException( "Unable to load yaml file.", e);
        }

        System.out.println ("### Read YAML " + config);

        /*
        distribution = System.getProperty( "DISTRIBUTION_ZIP" );
        mavenRepo = System.getProperty( "MAVEN_REPO_ZIP", "" );
        product = ProductSupport.valueOf( System.getProperty( "PRODUCT", "ALL" ) );
*/
//        logger.debug( "Established distribution {}, maven repository {}, and product of {}"+ config.getDistributionDirectory());
//                                      config.getMavenRepository()+ config.getProduct());
        downloadZips();
    }
    // TODO: extend ExternalResource to make a JUnit Rule and setup e.g. nested temporary file structure
    // to represent a mock configuration.
    // Possibly use a RuleChain to use other rules e.g. TemporaryFolder to setup a dummy 'unpacked' distribution
    // zip.

    /**
     * Modifies the method-running {@link Statement} to implement this
     * test-running rule.
     *
     * @param base The {@link Statement} to be modified
     * @param description A {@link Description} of the test implemented in {@code base}
     * @return a new statement, which may be the same as {@code base},
     *         a wrapper around {@code base}, or a completely new Statement.
     */
    @Override
    public Statement apply( Statement base, Description description )
    {
//        throw new PVTSystemException( "NYI" );
        return base;
    }
}
