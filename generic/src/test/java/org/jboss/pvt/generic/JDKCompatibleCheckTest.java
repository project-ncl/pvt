package org.jboss.pvt.generic;

import org.jboss.pvt.harness.validators.JDKCompatibleCheckValidator;
import org.jboss.pvt.harness.validators.Validator;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class JDKCompatibleCheckTest extends PVTSuperTestCase {

    @Test
    public void testJDKCompatible() throws Exception {
        assertTrue(test());
    }

    @Override
    protected Class<? extends Validator> getValidatorClass() {
        return JDKCompatibleCheckValidator.class;
    }

    @Override
    public String getDescription() {
        return "Test if the delivered jars are complied by the Java version between the given scope in params. ClassVersion enum: JAVA_0(0), JAVA_12( 46 ), JAVA_13( 47 ), JAVA_14( 48 ), JAVA_15( 49 ), JAVA_16( 50 ), JAVA_17( 51 ), JAVA_18( 52 )";
    }
}
