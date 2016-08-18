package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.configuration.PVTConfiguration;
import org.jboss.pvt.harness.exception.PVTException;
import org.jboss.pvt.harness.utils.DirUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This job verifies that all jars in the maven repo zip are built from source and in Mead
 * https://jenkins.mw.lab.eng.bos.redhat.com/hudson/view/EAP7/view/EAP7-Prod/job/jboss-eap-7.0.x-handoff-repository-maven-check-check-productized-files
 * Created by yyang on 7/29/16.
 *
 * https://jenkins.mw.lab.eng.bos.redhat.com/hudson/view/EAP7/view/EAP7-Prod/job/jboss-eap-7.0.x-handoff-repository-maven-check-check-productized-files/configure
 * http://git.app.eng.bos.redhat.com/git/jbossqe/eap-tests-scripts.git/tree/compare-maven-repo/check-maven-repo-for-productized-files.sh
 */
public class RepoJarsProductizedValidator implements Validator {

    @Override
    public Result validate(PVTConfiguration pvtConfiguration) throws PVTException {

        Collection<File> notProductized_all = DirUtils.listFilesRecursively(pvtConfiguration.getRepositoryDirectory(), new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".pom") && (pathname.getName().contains("todo") || !pathname.getName().contains("redhat-"));
            }
        });

        Collection<File> productized = DirUtils.listFilesRecursively(pvtConfiguration.getRepositoryDirectory(), new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".pom") && pathname.getName().contains("redhat-") && (!pathname.getName().contains("todo"));
            }
        });

        Collection<File> notProductized_redhat_todo = new ArrayList<File>();
        Collection<File> notProductized_boms = new ArrayList<File>();
        Collection<File> notProductized_upstream = new ArrayList<File>();
        Collection<File> notProductized = new ArrayList<File>();
        for(File file: notProductized_all) {
            if(!file.getName().contains("org/jboss/bom")) {
                notProductized.add(file);
            }
            if(file.getName().contains("redhat-0-todo")){
                notProductized_redhat_todo.add(file);
            }
            if(!file.getName().contains("redhat-0-todo") && !file.getName().contains("org/jboss/bom")){
                notProductized_upstream.add(file);
            }
            if(!file.getName().contains("redhat-0-todo") && file.getName().contains("org/jboss/bom")){
                notProductized_boms.add(file);
            }
        }

        //TODO: write report
        return notProductized.isEmpty() ? Result.TRUE : Result.FALSE;
    }

}
