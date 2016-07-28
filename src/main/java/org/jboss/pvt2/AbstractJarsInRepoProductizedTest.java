package org.jboss.pvt2;

import java.io.File;

/**
 * Created by yyang on 7/27/16.
 */
public abstract class AbstractJarsInRepoProductizedTest extends SuperTestCase {
    //TODO: https://jenkins.mw.lab.eng.bos.redhat.com/hudson/view/EAP7/view/EAP7-Prod/job/jboss-eap-7.0.x-handoff-repository-maven-check-check-productized-files/configure


    /**
     * Maven repo root dir
     */
    protected abstract File getRootDir();
}
