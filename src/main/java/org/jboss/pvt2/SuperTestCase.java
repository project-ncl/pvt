package org.jboss.pvt2;

import org.jboss.pvt2.log.PVTLogger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.logging.Logger;

/**
 * The top test case class
 * Created by yyang on 7/27/16.
 */
public abstract class SuperTestCase {
    private static Logger logger = PVTLogger.getLogger(SuperTestCase.class);

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

}
