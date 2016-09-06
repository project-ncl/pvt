package org.jboss.pvt.harness;

import org.jboss.pvt.harness.validators.JarDigestSignValidator;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

/**
 * Created by yyang on 8/3/16.
 */
public class JarDigestSignTest {
    @Rule
    public TemporaryFolder tf = new TemporaryFolder();
    @ClassRule
    public static TestConfiguration pvtConfiguration = new TestConfiguration();

    public JarDigestSignValidator test =  new JarDigestSignValidator();

    @Test
    public void testSigned() throws Exception{
        File rootDir = tf.newFolder();
        test.validate(pvtConfiguration);
    }
}
