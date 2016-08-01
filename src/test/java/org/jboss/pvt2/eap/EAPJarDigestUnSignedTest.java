package org.jboss.pvt2.eap;

import org.jboss.pvt2.AbstractJarDigestSignTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.*;

/**
 * This job checks for absence of sha1 digests in manifest, that cause performance degradation in certain JVMs
 * From version 7.0.0.ER7 job fails because of org/bouncycastle artifacts are digitally signed - this is correct. Other fails are real failures
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
