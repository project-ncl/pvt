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

package org.jboss.pvt.harness;

import org.jboss.pvt.harness.utils.DirUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by yyang on 7/27/16.
 */
public abstract class AbstractProductJarsPresentInRepo {
    //Ref: https://jenkins.mw.lab.eng.bos.redhat.com/hudson/view/EAP7/view/EAP7-Prod/job/jboss-eap-7.0.x-handoff-repository-maven-check-EAP-jars-in-repo/
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Test
    public void testProductJarsPresentInRepo() {

        List<File> notPresentJars = new ArrayList<File>();

        Collection<File> productJars =  DirUtils.listFilesRecursively( getProductDir(), new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".jar");
            }
        });

        Collection<File> repoJars =  DirUtils.listFilesRecursively(getRepoDir(), new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".jar");
            }
        });


        for(File productJar : productJars) {
            if(ignoreCheck(productJar)){
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
        if(!notPresentJars.isEmpty()) {
            logger.debug("Not present jars: " + Arrays.toString(notPresentJars.toArray()));
            Assert.assertTrue(false);
        }
    }

    protected boolean ignoreCheck(File file) {
        for(String filter : getJarIgnoreFilter()) {
            if(file.getPath().contains(filter)) {
                return true;
            }
        }
        return false;
    }

    protected String[] getJarIgnoreFilter() {
        return new String[]{};
    }

    protected abstract File getRepoDir();
    protected abstract File getProductDir();

}
