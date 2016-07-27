package org.jboss.pvt2.eap;

import org.jboss.pvt2.SuperTestCase;
import org.jboss.pvt2.log.PVTLogger;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.logging.Logger;

/**
 * Created by yyang on 7/11/16.
 */
@Category({EAP7.class, EAP6.class})
public class EAPTestCase2 extends SuperTestCase{

    @Test
    public void test2(){
        logger.info("test2");
    }

}
