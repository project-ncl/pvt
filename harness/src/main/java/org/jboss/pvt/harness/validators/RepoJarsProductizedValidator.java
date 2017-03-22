package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.configuration.ConfigurationLoader;
import org.jboss.pvt.harness.exception.PVTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This job verifies that all jars in the maven repo zip are built from source and in Mead
 * https://jenkins.mw.lab.eng.bos.redhat.com/hudson/view/EAP7/view/EAP7-Prod/job/jboss-eap-7.0.x-handoff-repository-maven-check-check-productized-files
 * Created by yyang on 7/29/16.
 *
 * https://jenkins.mw.lab.eng.bos.redhat.com/hudson/view/EAP7/view/EAP7-Prod/job/jboss-eap-7.0.x-handoff-repository-maven-check-check-productized-files/configure
 * http://git.app.eng.bos.redhat.com/git/jbossqe/eap-tests-scripts.git/tree/compare-maven-repo/check-maven-repo-for-productized-files.sh
 */
public class RepoJarsProductizedValidator implements Validator {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Validation validate(List resources, List filters, Map params) throws Exception {
        return null;
    }

    //    @Override
    public boolean validate( ConfigurationLoader pvtConfiguration) throws PVTException {

        Collection<File> notProductized_all = null;
//        DirUtils.listFilesRecursively( pvtConfiguration.getMavenRepository(), new FileFilter() {
//            public boolean accept(File pathname) {
//                return pathname.getName().endsWith(".pom") && (pathname.getName().contains("todo") || !pathname.getName().contains("redhat-"));
//            }
//        });

        Collection<File> productized = null;
//        DirUtils.listFilesRecursively( pvtConfiguration.getMavenRepository(), new FileFilter() {
//            public boolean accept(File pathname) {
//                return pathname.getName().endsWith(".pom") && pathname.getName().contains("redhat-") && (!pathname.getName().contains("todo"));
//            }
//        });

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

        logger.info("Productized: " + productized.size()
                + ", NOT Productized all: " + notProductized_all.size()
                + ", NOT Productized BOMs: " + notProductized_boms.size()
                        + ", NOT Productized redhat-todo: " + notProductized_redhat_todo.size()
                        + ", NOT Productized upstream: " + notProductized_upstream.size()

        );
        logger.info("Productized artifacts: " + collectionToString(productized));
        logger.warn("NOT Productized artifacts all: " + collectionToString(notProductized));
        logger.warn("NOT Productized BOMs: " + collectionToString(notProductized_boms));
        logger.warn("NOT Productized redhat-todo: " + collectionToString(notProductized_redhat_todo));
        logger.warn("NOT Productized upstream: " + collectionToString(notProductized_upstream));
        //TODO: write report to file ???
        return notProductized.isEmpty();
    }

    private String collectionToString(Collection<File> files){
        StringBuffer sb = new StringBuffer();
        for(File file: files) {
            sb.append(file.getPath()).append("\n");
        }
        return sb.toString();
    }
}
