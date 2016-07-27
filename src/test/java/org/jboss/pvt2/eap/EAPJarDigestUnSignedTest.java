package org.jboss.pvt2.eap;

import org.jboss.pvt2.AbstractJarDigestUnSignTest;
import org.junit.experimental.categories.Category;

import java.io.*;

/**
 * Created by yyang on 7/11/16.
 */
@Category({EAP7.class, EAP6.class})
public class EAPJarDigestUnSignedTest extends AbstractJarDigestUnSignTest {

    @Override
    protected File getRootDir() {
        return new File(EAP7TestSuite.getTestConfig().getEapDir());
    }

}
