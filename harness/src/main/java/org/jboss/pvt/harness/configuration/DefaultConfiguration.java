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
import org.jboss.pvt.harness.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isNotEmpty;
import static org.jboss.pvt.harness.utils.FileUtil.downloadZips;

/**
 * Created by rnc on 28/07/16.
 */
// TODO: Eliminate the PVYConfiguration interface?
public class DefaultConfiguration implements PVTConfiguration
{
    private Logger logger = LoggerFactory.getLogger( getClass() );

    protected Configuration config;

    private File distributionDirectory;
    private File sourceDistributionDirectory;
    private File mavenRepositoryDirectory;
    private List<File> auxillaryDistributions;

    public DefaultConfiguration()
    {
        init();
    }

    protected void init ()
    {
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
        }
        else
        {
            logger.warn( "No configuration file found with '{}' ", file );
            config = new Configuration();
        }
        logger.debug( "Established distribution {}, maven repository {}, and product of {}", config.getDistribution(), config.getMavenRepository(), config.getProduct());
        distributionDirectory = downloadZips( config.getDistribution());
        mavenRepositoryDirectory = downloadZips( config.getMavenRepository());
        sourceDistributionDirectory = downloadZips( config.getSourceDistribution());
        auxillaryDistributions = new ArrayList<>(  );
        auxillaryDistributions.addAll( config.getAuxilliaryDistributions()
                                             .stream()
                                             .map( FileUtil::downloadZips )
                                             .collect( Collectors.toList() ) );
    }

    @Override
    public Product getProduct()
    {
        return config.getProduct();
    }

    public File getDistributionDirectory()
    {
        return distributionDirectory;
    }

    @Override
    public File getSourceDistribution()
    {
        return sourceDistributionDirectory;
    }

    @Override
    public List<File> getAuxilliaryDistributions()
    {
        return auxillaryDistributions;
    }

    @Override
    public TestCase getTestCase( String key )
    {
        return config.getTestCases().get(key);
    }

    public File getMavenRepository()
    {
        return mavenRepositoryDirectory;
    }

}
