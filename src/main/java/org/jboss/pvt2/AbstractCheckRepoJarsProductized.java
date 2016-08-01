package org.jboss.pvt2;

import org.jboss.pvt2.utils.DirUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;

/**
 * This job verifies that all jars in the maven repo zip are built from source and in Mead
 * https://jenkins.mw.lab.eng.bos.redhat.com/hudson/view/EAP7/view/EAP7-Prod/job/jboss-eap-7.0.x-handoff-repository-maven-check-check-productized-files
 * Created by yyang on 7/29/16.
 */
public abstract class AbstractCheckRepoJarsProductized extends SuperTestCase{

    public void testRepoJarsProductized(){
        Collection<File> notProductized = DirUtils.listFilesRecursively(getRepoDir(), new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".pom") && (pathname.getName().contains("todo") || !pathname.getName().contains("redhat"));
            }
        });

        Collection<File> productized = DirUtils.listFilesRecursively(getRepoDir(), new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".pom") && pathname.getName().contains("redhat") && (!pathname.getName().contains("todo"));
            }
        });

    }

    protected abstract File getRepoDir();
}
