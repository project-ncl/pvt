package org.jboss.pvt2.eap;

import org.jboss.pvt2.AbstractJarDigestSignTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.*;

/**
 * Created by yyang on 7/11/16.
 */
@Category({EAP7.class, EAP6.class})
public class EAPJarDigestUnSignedTest extends AbstractJarDigestSignTest {

    @Override
    protected File getRootDir() {
        return new File(EAP7TestSuite.getTestConfig().getEapDir());
    }

    protected boolean mustSigned(){
        return false;
    }

}
