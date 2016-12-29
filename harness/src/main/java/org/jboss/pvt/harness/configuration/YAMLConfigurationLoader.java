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
import org.jboss.pvt.harness.exception.PVTSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by rnc on 28/07/16.
 */
public class YAMLConfigurationLoader implements ConfigurationLoader
{
    private Logger logger = LoggerFactory.getLogger( getClass() );

    protected Configuration config;

    public YAMLConfigurationLoader()
    {
    }

    public Configuration loadConfig (File configFile)
    {

            Representer representer = new Representer();
            representer.getPropertyUtils().setSkipMissingProperties( true );
            Yaml yaml = new Yaml( representer );

            try
            {
                File targetConfig = configFile;

                if ( targetConfig.exists() )
                {
                    config = yaml.loadAs( new FileInputStream( targetConfig ), Configuration.class );
                }
                else
                {
                    logger.debug( "Unable to find file {} ", targetConfig );
                    throw new PVTSystemException( "Unable to load yaml file." );
                }
            }
            catch ( FileNotFoundException e )
            {
                throw new PVTSystemException( "Unable to load yaml file.", e );
            }

        return config;
    }

}
