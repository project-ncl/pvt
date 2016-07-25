package org.jboss.pvt2.eap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.jboss.pvt2.log.PVTLogger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by yyang on 7/11/16.
 */
@Category({EAP7.class, EAP6.class})
public class EAPCheckJarDigestUnSigned {

    private static Logger logger = PVTLogger.getLogger(EAPCheckJarDigestUnSigned.class);

    @Test
    public void testUnsigned() throws Exception {
        File eapDir = new File(EAP7TestSuite.getTestConfig().getEapDir());
        Collection<File> jarFiles = FileUtils.listFilesAndDirs(eapDir, new AbstractFileFilter() {
            @Override
            public boolean accept(File file) {
                logger.info(file.toString());
                return file.isFile() && file.getName().endsWith(".jar");
            }

        }, new AbstractFileFilter() {
            @Override
            public boolean accept(File file) {
                logger.info(file.toString());
                return file.isDirectory();
            }
        });


        logger.info(Arrays.toString(jarFiles.toArray()));

        boolean unsigned = true;

        for(File jarFile : jarFiles){
            logger.info("Check Jar: " + jarFile.toString());
            if(jarFile.getPath().contains("bouncycastle")){
                logger.info("Skip bouncycastle Jar!");
                continue;
            }
            ZipFile zipFile = new ZipFile(jarFile);
            StringWriter sw = new StringWriter();
            IOUtils.copy(new InputStreamReader(zipFile.getInputStream(zipFile.getEntry("META-INF/MANIFEST.MF"))), sw);
            unsigned = !sw.getBuffer().toString().contains("Digest:");
        }

        Assert.assertTrue(unsigned);
    }
}
