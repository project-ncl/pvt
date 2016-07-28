package org.jboss.pvt2.eap;

import org.jboss.pvt2.SuperTestCase;
import org.jboss.pvt2.log.PVTLogger;
import org.jboss.pvt2.utils.HttpUtils;
import org.jboss.pvt2.utils.ZipUtils;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.util.logging.Logger;

/**
 * EAPResourceTest should be on the first one of the test suite, to test the sources of EAP are accessable
 * And then, make the sources ready to used by the next tests.
 * Created by yyang on 7/11/16.
 */
@Category({EAP7.class, EAP6.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EAPResourceTest extends SuperTestCase{

    public EAPResourceTest(){

    }

    @Test
    public void test1Configuration(){
        logger.info(EAP7TestSuite.EAPTestConfig.EAP_DIR_KEY + ": " + EAP7TestSuite.EAPTestConfig.getInstance().getEapDir());
        Assert.assertNotNull(EAP7TestSuite.EAPTestConfig.getInstance().getEapDir());

    }

    @Test
    public void test2Download() throws Exception{
        logger.info("Download EAP zip: " + EAP7TestSuite.EAPTestConfig.getInstance().getEapZipUrl());
        HttpUtils.httpDownload(EAP7TestSuite.EAPTestConfig.getInstance().getEapZipUrl());
        Assert.assertTrue(new File(EAP7TestSuite.EAPTestConfig.getInstance().getEapZipName()).exists());

        logger.info("Download Repo zip: " + EAP7TestSuite.EAPTestConfig.getInstance().getRepoZipUrl());
        HttpUtils.httpDownload(EAP7TestSuite.EAPTestConfig.getInstance().getRepoZipUrl());
        Assert.assertTrue(new File(EAP7TestSuite.EAPTestConfig.getInstance().getRepoZipName()).exists());
    }

    @Test
    public void test3Extract() throws Exception{
        logger.info("Extract: " + EAP7TestSuite.EAPTestConfig.getInstance().getEapZipName());
        File zipFile = new File( EAP7TestSuite.EAPTestConfig.getInstance().getEapZipName());
        Assert.assertTrue(zipFile.exists());
        ZipUtils.unzip(zipFile);

        logger.info("Extract: " + EAP7TestSuite.EAPTestConfig.getInstance().getRepoZipName());
        File repoFile = new File( EAP7TestSuite.EAPTestConfig.getInstance().getRepoZipName());
        Assert.assertTrue(repoFile.exists());
        ZipUtils.unzip(repoFile);

    }

}
