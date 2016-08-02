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

import org.apache.commons.io.IOUtils;
import org.jboss.pvt.harness.utils.DirUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.zip.ZipFile;

/**
 * Created by yyang on 7/11/16.
 */

public abstract class AbstractJarDigestSignTest {

    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Test
    public void testSigned() throws Exception {
        File eapDir = getRootDir();

        Collection<File> jarFiles = DirUtils.listFilesRecursively( eapDir, new FileFilter() {
            public boolean accept(File pathname) {
                return  pathname.isFile() && pathname.getName().endsWith(".jar");
            }
        });

        logger.info("Jars: " + Arrays.toString(jarFiles.toArray()));

        boolean signed = mustSigned();
        for(File jarFile : jarFiles){
            if(jarFile.isFile()) {
                if (shouldExclude(jarFile)) {
                    logger.warn("Skip Jar, " + jarFile);
                    continue;
                }
                ZipFile zipFile = new ZipFile(jarFile);
                StringWriter sw = new StringWriter();
                IOUtils.copy(new InputStreamReader(zipFile.getInputStream(zipFile.getEntry("META-INF/MANIFEST.MF"))), sw);
                signed = sw.getBuffer().toString().contains("Digest:");

                if(signed != mustSigned()) {
                    logger.debug("Found " + (mustSigned() ? "unsigned" : "signed" ) + " jar: " + jarFile);
                    break;
                }
            }
        }


        Assert.assertTrue(signed == mustSigned());
    }

    /**
     * which class need to be excluded
     */
    protected String[] getExcludesJarFilter(){
        if(!mustSigned()) {
            // bouncycastle jars always are signed
            return new String[]{"bouncycastle/"};
        }
        else {
            return new String[]{};
        }
    }

    /**
     * If the jars must signed or not
     */
    protected boolean mustSigned(){
        return true;
    }

    private boolean shouldExclude(File file){
        boolean exclude = false;
        for(String filter : getExcludesJarFilter()){
            if(file.getPath().contains(filter)) {
                exclude = true;
                break;
            }
        }
        return exclude;
    }

    /**
     * The root dir to scan jars
     */
    protected abstract File getRootDir();

}
