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
}
