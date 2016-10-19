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

import org.apache.commons.io.IOUtils;
import org.jboss.pvt.harness.configuration.PVTConfiguration;
import org.jboss.pvt.harness.exception.PVTException;
import org.jboss.pvt.harness.exception.PVTSystemException;
import org.jboss.pvt.harness.utils.DirUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipFile;

/**
 * Created by yyang on 7/11/16.
 */

public class JBossSignatureCheckValidator implements Validator{

    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Override
    public boolean validate( PVTConfiguration pvtConfiguration) throws PVTException {
        File eapDir = pvtConfiguration.getDistributionDirectory();

        Collection<File> jarFiles = DirUtils.listFilesRecursively( eapDir, new FileFilter() {
            public boolean accept(File pathname) {
                return  pathname.isFile() && pathname.getName().endsWith(".jar");
            }
        });

        logger.info("Jars: " + Arrays.toString(jarFiles.toArray()));

        boolean signed = mustSigned(pvtConfiguration);
        try {
            for (File jarFile : jarFiles) {
                if (jarFile.isFile()) {
                    if (shouldExclude(jarFile, pvtConfiguration)) {
                        logger.warn("Skip Jar, " + jarFile);
                        continue;
                    }
                    ZipFile zipFile = new ZipFile(jarFile);
                    StringWriter sw = new StringWriter();
                    IOUtils.copy(new InputStreamReader(zipFile.getInputStream(zipFile.getEntry("META-INF/MANIFEST.MF"))), sw);
                    signed = sw.getBuffer().toString().contains("Digest:");

                    if (signed != mustSigned(pvtConfiguration)) {
                        logger.debug("Found " + (mustSigned(pvtConfiguration) ? "unsigned" : "signed") + " jar: " + jarFile);
                        break;
                    }
                }
            }
        }
        catch (IOException e)
        {
            throw new PVTSystemException(e);
        }

        return (signed == mustSigned(pvtConfiguration) );
    }

    /**
     * which class need to be excluded
     */
    protected List<String> getExcludesJarFilter( PVTConfiguration pvtConfiguration){
        if(!mustSigned(pvtConfiguration)) {
            // bouncycastle jars always are signed
            return pvtConfiguration.getTestCase(this.getClass().toString()).getFilters();
        }
        else {
            return Collections.emptyList();
        }
    }

    /**
     * If the jars must signed or not
     */
    protected boolean mustSigned(PVTConfiguration pvtConfiguration)
    {
        return pvtConfiguration.getTestCase( this.getClass().toString() ).isExclusion();
    }

    private boolean shouldExclude(File file, PVTConfiguration pvtConfiguration){
        boolean exclude = false;
        for(String filter : getExcludesJarFilter(pvtConfiguration)){
            if(file.getPath().contains(filter)) {
                exclude = true;
                break;
            }
        }
        return exclude;
    }

}
