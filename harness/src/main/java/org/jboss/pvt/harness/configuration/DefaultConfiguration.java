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

import org.apache.commons.io.FileUtils;
import org.jboss.pvt.harness.exception.PVTSystemException;
import org.jboss.pvt.harness.utils.ProductSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by rnc on 28/07/16.
 */
public class DefaultConfiguration implements PVTConfiguration
{
    private Logger logger = LoggerFactory.getLogger( getClass() );

    private String mavenRepo;
    private String distribution;
    private ProductSupport product;

    private File distributionZip;

    public DefaultConfiguration()
    {
        distribution = System.getProperty( "DISTRIBUTION_ZIP" );
        mavenRepo = System.getProperty( "MAVEN_REPO_ZIP", "" );
        product = ProductSupport.valueOf( System.getProperty( "PRODUCT", "ALL" ) );

        logger.debug( "Established distribution {}, maven repository {}, and product of {}", distribution, mavenRepo, product );
        downloadZips();
    }


    public File getDistribution()
    {
        return distributionZip;
    }

    public File getDistributionDirectory()
    {
        return distributionZip;
    }

    public File getRepositoryDirectory()
    {
        return new File (mavenRepo); // TODO: Implement repository and distribution zip handling and unzipped.
    }

    @Override
    public Properties getAllConfiguration()
    {
        throw new PVTSystemException( "NYI" );
    }

    @Override
    public String[] getTestFilter( String testName )
    {
        throw new PVTSystemException( "NYI" );
//        return new String[0];
    }

    private void downloadZips()
    {
        try
        {
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
        }
        catch ( IOException e )
        {
            logger.error( "Caught ", e );
            throw new PVTSystemException( "Caught exception processing downloads: " + e );
        }
    }
}
