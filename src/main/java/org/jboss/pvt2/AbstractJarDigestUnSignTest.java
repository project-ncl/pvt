package org.jboss.pvt2;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.jboss.pvt2.log.PVTLogger;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.zip.ZipFile;

/**
 * Created by yyang on 7/11/16.
 */

public abstract class AbstractJarDigestUnSignTest extends SuperTestCase {

    private static Logger logger = PVTLogger.getLogger(AbstractJarDigestUnSignTest.class);

    @Test
    public void testSigned() throws Exception {
        File eapDir = getRootDir();
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

        boolean signed = mustSigned();
        for(File jarFile : jarFiles){
            if(jarFile.isFile()) {
                if (shouldExclude(jarFile)) {
                    logger.warning("Skip Jar, " + jarFile);
                    continue;
                }
                ZipFile zipFile = new ZipFile(jarFile);
                StringWriter sw = new StringWriter();
                IOUtils.copy(new InputStreamReader(zipFile.getInputStream(zipFile.getEntry("META-INF/MANIFEST.MF"))), sw);
                signed = sw.getBuffer().toString().contains("Digest:");

                if(signed != mustSigned()) {
                    logger.severe("Found " + (mustSigned() ? "unsigned" : "signed" ) + " jar: " + jarFile);
                    break;
                }
            }
        }

        Assert.assertTrue(signed == mustSigned());
    }

    protected String[] getExcludesFilter(){
        // bouncycastle jars always are signed
        return new String[]{"bouncycastle"};
    }

    protected boolean mustSigned(){
        return false;
    }

    private boolean shouldExclude(File file){
        boolean exclude = false;
        for(String filter : getExcludesFilter()){
            if(file.getName().contains(filter)) {
                exclude = true;
                break;
            }
        }
        return exclude;
    }

    protected abstract File getRootDir();
}
