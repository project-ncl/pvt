package org.jboss.pvt2.eap;

import org.jboss.pvt2.log.PVTLogger;
import org.jboss.pvt2.utils.HttpUtils;
import org.jboss.pvt2.utils.ZipUtils;
import org.junit.*;

import java.io.File;
import java.util.logging.Logger;

/**
 * EAPResourceTest should be on the first one of the test suite, to test the sources of EAP are accessable
 * And then, make the sources ready to used by the next tests.
 * Created by yyang on 7/11/16.
 */
public class EAPResourceTest {

    private static Logger logger = PVTLogger.getLogger(EAPResourceTest.class.getName());


    public EAPResourceTest(){

    }

    @BeforeClass
    public static void _setupClass(){
        logger.info("@BeforeClass _setupClass");
    }

    @AfterClass
    public static void _afterClass() {
        logger.info("@AfterClass _afterClass");
    }

    @Before
    public void _setUp() {
        logger.info("@Before _setup");
    }

    @After
    public void _teardown() {
        logger.info("@After _teardown");
    }

    @Test
    public void testConfiguration(){
        logger.info(EAPTestSuite.EAPTestConfig.EAP_DIR_KEY + ": " + EAPTestSuite.EAPTestConfig.getEapDir());
        Assert.assertNotNull(EAPTestSuite.EAPTestConfig.getEapDir());

    }

    @Test
    public void testDownload() throws Exception{
        logger.info("Download EAP ZIP: " + EAPTestSuite.EAPTestConfig.getEapZipUrl());
        HttpUtils.httpDownload(EAPTestSuite.EAPTestConfig.getEapZipUrl());

    }

    @Test
    public void testExtract() throws Exception{
        File zipFile = new File( EAPTestSuite.EAPTestConfig.getEapZipName());
        Assert.assertTrue(zipFile.exists());
        ZipUtils.unzip(zipFile);
    }

}


