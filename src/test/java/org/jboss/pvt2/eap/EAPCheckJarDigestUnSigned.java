package org.jboss.pvt2.eap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.jboss.pvt2.log.PVTLogger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
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
                return file.isFile() && file.getName().endsWith(".jar");
            }

        }, TrueFileFilter.INSTANCE);

        // remove dirs
        for(Iterator<File> it= jarFiles.iterator(); it.hasNext(); ) {
            File file = it.next();
            if(file.isDirectory()) {
                it.remove();
            }
        }

        logger.info("Jars: " + Arrays.toString(jarFiles.toArray()));

        boolean unsigned = true;
        for(File jarFile : jarFiles){
            if(jarFile.isFile()) {
                // bouncycastle jars always are signed
                if (jarFile.getPath().contains("bouncycastle")) {
                    logger.warning("Skip bouncycastle Jar!");
                    continue;
                }
                ZipFile zipFile = new ZipFile(jarFile);
                StringWriter sw = new StringWriter();
                IOUtils.copy(new InputStreamReader(zipFile.getInputStream(zipFile.getEntry("META-INF/MANIFEST.MF"))), sw);
                unsigned = !sw.getBuffer().toString().contains("Digest:");

                if(!unsigned) {
                    logger.severe("Found signed jar: " + jarFile);
                    break;
                }
            }
        }

        Assert.assertTrue(unsigned);
    }
}
