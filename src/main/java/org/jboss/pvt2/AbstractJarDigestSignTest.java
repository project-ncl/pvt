package org.jboss.pvt2;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.jboss.pvt2.log.PVTLogger;
import org.jboss.pvt2.utils.DirUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
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

public abstract class AbstractJarDigestSignTest extends SuperTestCase {

    @Test
    public void testSigned() throws Exception {
        File eapDir = getRootDir();

        Collection<File> jarFiles = DirUtils.listFilesRecursively(eapDir, new FileFilter() {
            public boolean accept(File pathname) {
                return  pathname.isFile() && pathname.getName().endsWith(".jar");
            }
        });

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
