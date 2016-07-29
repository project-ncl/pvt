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

package org.jboss.pvt.harness.rules;

import org.apache.commons.io.FileUtils;
import org.jboss.pvt.harness.PVTException;
import org.jboss.pvt.harness.ProductSupport;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by rnc on 28/07/16.
 */
public class ParameterHandler extends ExternalResource
{
    private Logger logger = LoggerFactory.getLogger( getClass() );

    private String mavenRepo;
    private String distribution;
    private ProductSupport product;

    private File distributionZip;

    @Override
    protected void before ()
    {
        distribution = System.getProperty( "DISTRIBUTION_ZIP" );
        mavenRepo = System.getProperty( "MAVEN_REPO_ZIP" );
        product = ProductSupport.valueOf( System.getProperty( "PRODUCT", "ALL" ) );

        logger.debug( "Established distribution {}, maven repository {}, and product of {}", distribution, mavenRepo, product );

        downloadZips();
    }

    @Override
    protected void after()
    {

    }

    public File getDistribution()
    {
        return distributionZip;
    }

    private void downloadZips()
    {
        if ( distributionZip != null && distributionZip.exists() )
        {
            logger.warn ("Avoiding duplicate download of {} ", distribution);
        }
        try
        {
            if ( distribution.startsWith( "http" ) )
            {
                distributionZip = new File( System.getProperty( "basedir" ) + "/target/" + distribution.substring(
                                distribution.lastIndexOf( "/" ) + 1 ) );
                FileUtils.copyURLToFile( new URL( distribution ), distributionZip );
            }
            else
            {
                distributionZip = new File( System.getProperty( "basedir" ) + "/target/" + distribution.substring(
                                distribution.lastIndexOf( "/" ) + 1 ) );
                FileUtils.copyFile( new File( distribution ), distributionZip );
            }
            logger.debug ( "Create distribution {} ", distributionZip );
        }
        catch ( IOException e )
        {
            logger.error( "Caught ", e );
            throw new PVTException( "Caught exception processing downloads: " + e );
        }
    }
}
