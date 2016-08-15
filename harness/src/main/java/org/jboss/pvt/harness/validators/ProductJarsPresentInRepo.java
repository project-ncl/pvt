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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yyang on 7/27/16.
 *
 * TODO: Add description of the test
 */
public final class ProductJarsPresentInRepo implements Validator
{
    //Ref: https://jenkins.mw.lab.eng.bos.redhat.com/hudson/view/EAP7/view/EAP7-Prod/job/jboss-eap-7.0.x-handoff-repository-maven-check-EAP-jars-in-repo/
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    private String[] filters = new String[]{};

    private final PVTConfiguration configuration;

    public ProductJarsPresentInRepo( PVTConfiguration configuration )
    {
        this.configuration = configuration;
    }

    /**
     * Validation logic that should be applied.
     * @return true if it validates successfully.
     * @throws PVTException if an error occurs.
     */
    @Override
    public boolean validate() throws PVTException
    {
        boolean success = false;

        List<File> notPresentJars = new ArrayList<File>();

        Collection<File> productJars =  DirUtils.listFilesRecursively( getConfiguration().getDistributionDirectory(), new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".jar");
            }
        });

        Collection<File> repoJars =  DirUtils.listFilesRecursively(getConfiguration().getRepositoryDirectory(), new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".jar");
            }
        });


        for(File productJar : productJars) {
            if( filter( productJar)){
                logger.info("Ignore jar: " + productJar);
                continue;
            }
            boolean present = false;
            for(File repoJar : repoJars){
                if(repoJar.getName().equals(productJar.getName()) && repoJar.length()==productJar.length()) {
                    present = true;
                    break;
                }
            }

            if(!present){
                notPresentJars.add(productJar);
            }
        }
        if(notPresentJars.isEmpty()) {
            success = true;
        }
        return success;
    }

    @Override
    public PVTConfiguration getConfiguration()
    {
        return configuration;
    }

    /**
     * Apply a set of filters to a validator
     * // TODO : Define api here.
     */
    public boolean filter( File... file) {
        if ( file.length != 1 )
        {
            throw new PVTSystemException( "Unexpected length of arguments for this filter" );
        }
        for(String filter : filters) {
            if(file[0].getPath().contains(filter)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialiseFilter(String[] filters)
    {
        this.filters = filters;
    }

}
