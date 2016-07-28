package org.jboss.pvt;

import org.jboss.pvt.log.PVTLogger;
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
    public final static Logger staticLogger = PVTLogger.getLogger(SuperTestCase.class);
    public final Logger logger = PVTLogger.getLogger(this.getClass());


    @BeforeClass
    public static void _setupClass(){
        staticLogger.info("@BeforeClass _setupClass");
    }

    @AfterClass
    public static void _afterClass() {
        staticLogger.info("@AfterClass _afterClass");
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
