package org.jboss.pvt.harness;

import org.jboss.pvt.harness.configuration.YAMLConfigurationLoader;
import org.jboss.pvt.harness.configuration.ConfigurationLoader;
import org.jboss.pvt.harness.validators.JDKCompatibleCheckValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ProvideSystemProperty;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yyang on 8/3/16.
 */
public class JDKCompatibleCheckValidatorTest {
    public JDKCompatibleCheckValidator test =  new JDKCompatibleCheckValidator();

    @Test
    public void testJDKCompatible() throws Exception{
        Map<String, String> params = new HashMap<>();
        params.put(JDKCompatibleCheckValidator.PARAM_MIN_VERSION, "0");
        params.put(JDKCompatibleCheckValidator.PARAM_MAX_VERSION, "52");
        Assert.assertTrue(test.validate(Arrays.asList(new String[]{"https://github.com/release-engineering/pom-manipulation-ext/archive/pom-manipulation-parent-2.4.zip"}),
                Collections.emptyList(), params));
    }
}
