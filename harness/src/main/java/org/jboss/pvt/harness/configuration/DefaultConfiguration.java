/*
 * Copyright (C) 2016 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.pvt.harness.configuration;

import org.jboss.pvt.harness.configuration.pojo.Configuration;
import org.jboss.pvt.harness.configuration.pojo.Product;
import org.jboss.pvt.harness.configuration.pojo.TestCase;
import org.jboss.pvt.harness.exception.PVTSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * Created by rnc on 28/07/16.
 */
// TODO: Eliminate the PVYConfiguration interface?
public class DefaultConfiguration implements PVTConfiguration
{
    private Logger logger = LoggerFactory.getLogger( getClass() );

/*    private String mavenRepo;
    private String distribution;
    private ProductSupport product;

    private File distributionZip;*/

    protected Configuration config;

    public DefaultConfiguration()
    {
        init();
    }

    protected void init ()
    {
        // TODO:
        // Currently configuration file is passed in via the system property
        // PVTCFG
        String file = System.getProperty( "PVTCFG" );

        if ( isNotEmpty( file ))
        {
            Representer representer = new Representer();
            representer.getPropertyUtils().setSkipMissingProperties( true );
            Yaml yaml = new Yaml( representer );

            try
            {
                File targetConfig = new File( file );

                if ( targetConfig.exists() )
                {
                    config = yaml.loadAs( new FileInputStream( targetConfig ), Configuration.class );
                }
                else
                {
                    logger.debug( "Unable to find file {} ", file );
                    throw new PVTSystemException( "Unable to load yaml file." );
                }
            }
            catch ( FileNotFoundException e )
            {
                throw new PVTSystemException( "Unable to load yaml file.", e );
            }

        /*
        distribution = System.getProperty( "DISTRIBUTION_ZIP" );
        mavenRepo = System.getProperty( "MAVEN_REPO_ZIP", "" );
        product = ProductSupport.valueOf( System.getProperty( "PRODUCT", "ALL" ) );
*/
        }
        else
        {
            logger.warn( "No configuration file found with '{}' ", file );
            config = new Configuration();
        }
        logger.debug( "Established distribution {}, maven repository {}, and product of {}", config.getDistributionDirectory(), config.getMavenRepository(), config.getProduct());
        downloadZips();
    }

    @Override
    public Product getProduct()
    {
        return config.getProduct();
    }

    public File getDistributionDirectory()
    {
        return config.getDistributionDirectory();
    }

    @Override
    public File getSourceDistribution()
    {
        return config.getSourceDistribution();
    }

    @Override
    public List<File> getAuxilliaryZips()
    {
        return config.getAuxilliaryZips();
    }

    @Override
    public TestCase getTestCase( String key )
    {
        return config.getTestCase();
    }

    public File getMavenRepository()
    {
        return config.getMavenRepository(); // TODO: Implement repository and distribution zip handling and unzipped.
    }

    protected void downloadZips()
    {
        try
        {
            /*
            if ( distribution.startsWith( "http" ) )
            {
                distributionZip = new File( System.getProperty( "basedir" ) + "/target/" + distribution.substring(
                                distribution.lastIndexOf( "/" ) + 1 ) );
                if ( distributionZip.exists() )
                {
                    logger.warn ("Avoiding duplicate download of {} ", distribution);
                }
                else
                {
                    logger.debug( "Copying URL {} to {}", distribution, distributionZip );
                    FileUtils.copyURLToFile( new URL( distribution ), distributionZip );
                }
            }
            else
            {
                distributionZip = new File( System.getProperty( "basedir" ) + "/target/" + distribution.substring(
                                distribution.lastIndexOf( "/" ) + 1 ) );
                if ( distributionZip.exists() )
                {
                    logger.warn ("Avoiding duplicate download of {} ", distribution);
                }
                else
                {
                    FileUtils.copyFile( new File( distribution ), distributionZip );
                }
            }
            logger.debug ( "Create distribution {} ", distributionZip );
            */
        }
        catch ( Exception e )
        {
            logger.error( "Caught ", e );
            throw new PVTSystemException( "Caught exception processing downloads: " + e );
        }
    }
}
