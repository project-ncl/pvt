package org.jboss.pvt2.eap;

import org.apache.commons.io.IOUtils;
import org.jboss.pvt2.log.PVTLogger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.*;
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
        File[] jarFiles = eapDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        });

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
