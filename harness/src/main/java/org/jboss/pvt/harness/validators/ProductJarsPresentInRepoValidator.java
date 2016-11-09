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

package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.configuration.PVTConfiguration;
import org.jboss.pvt.harness.exception.PVTException;
import org.jboss.pvt.harness.exception.PVTSystemException;
import org.jboss.pvt.harness.utils.DirUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.Files.isRegularFile;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by yyang on 7/27/16.
 *
 * TODO: Add description of the test
 */
public final class ProductJarsPresentInRepoValidator implements Validator
{
    //Ref: https://jenkins.mw.lab.eng.bos.redhat.com/hudson/view/EAP7/view/EAP7-Prod/job/jboss-eap-7.0.x-handoff-repository-maven-check-EAP-jars-in-repo/
    private final Logger logger = LoggerFactory.getLogger( getClass() );


    /**
     * Validation logic that should be applied.
     * @return true if it validates successfully.
     * @throws PVTException if an error occurs.
     */
    @Override
    public boolean validate( PVTConfiguration pvtConfiguration) throws PVTException
    {
        if ( pvtConfiguration.getMavenRepository() == null || pvtConfiguration.getDistributionDirectory() == null )
        {
            return false;
        }

        try
        {
            Set<String>repoJars = Files.walk (pvtConfiguration.getMavenRepository().toPath()).
                            filter( p -> p.toString().endsWith(".jar") && isRegularFile(p)).
                            map ( p-> p.toFile().getName() ).
                            collect( toSet() );

            long count = Files.walk (pvtConfiguration.getDistributionDirectory().toPath()).
                            filter(p -> isRegularFile( p ) && p.toString().endsWith(".jar") && filter( pvtConfiguration, p.toFile() ) ).
                            filter( p -> ! repoJars.contains( p.toFile().getName() ) ).
                            count();

            return ( count == 0);
        }
        catch ( IOException e )
        {
             throw new PVTException( "Caught " , e);
        }
    }


    /**
     * Apply a set of filters to a validator
     * // TODO : Define api here.
     */
    public boolean filter(PVTConfiguration pvtConfiguration, File... file) {
        if ( file.length != 1 )
        {
            throw new PVTSystemException( "Unexpected length of arguments for this filter" );
        }
        for(String filter : pvtConfiguration.getTestCase(this.getClass().toString()).getFilters()) {
            if(file[0].getPath().contains(filter)) {
                return false;
            }
        }
        return true;
    }
}
