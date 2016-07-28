package org.jboss.pvt;

import org.jboss.pvt.utils.DirUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

/**
 * Created by yyang on 7/27/16.
 */
public abstract class AbstractProductJarsPresentInRepo extends SuperTestCase {
    //Ref: https://jenkins.mw.lab.eng.bos.redhat.com/hudson/view/EAP7/view/EAP7-Prod/job/jboss-eap-7.0.x-handoff-repository-maven-check-EAP-jars-in-repo/

    @Test
    public void testProductJarsPresentInRepo() {

        List<File> notPresentJars = new ArrayList<File>();

        Collection<File> productJars =  DirUtils.listFilesRecursively(getProductDir(), new FileFilter() {
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
            logger.severe("Not present jars: " + Arrays.toString(notPresentJars.toArray()));
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
