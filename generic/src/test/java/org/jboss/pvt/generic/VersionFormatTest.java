package org.jboss.pvt.generic;

import org.jboss.pvt.harness.validators.Validator;
import org.jboss.pvt.harness.validators.VersionFormatCheckValidator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class VersionFormatTest extends PVTSuperTestCase{

    @Test
    public void testVersionFormat() throws Exception {
        Assert.assertTrue(test());
    }

    @Override
    protected Class<? extends Validator> getValidatorClass() {
        return VersionFormatCheckValidator.class;
    }

    @Override
    public String getDescription() {
        return "Test if the jars' version have the redhat-X suffix";
    }
}
