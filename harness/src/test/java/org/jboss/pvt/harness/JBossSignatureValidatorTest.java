package org.jboss.pvt.harness;

import org.jboss.pvt.harness.validators.NOSignatureCheckValidator;
import org.junit.*;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by yyang on 8/3/16.
 */
public class JBossSignatureValidatorTest {

    public NOSignatureCheckValidator test =  new NOSignatureCheckValidator();

    @Before
    public void before() {
    }

    @Test
    public void testSigned() throws Exception{
        Assert.assertTrue(test.validate(Arrays.asList(new String[]{"https://github.com/release-engineering/pom-manipulation-ext/archive/pom-manipulation-parent-2.4.zip"}),
                Collections.emptyList(), Collections.emptyMap()));
    }


}
