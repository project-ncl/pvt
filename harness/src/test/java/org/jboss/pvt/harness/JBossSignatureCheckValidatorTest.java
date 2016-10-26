package org.jboss.pvt.harness;

import org.jboss.pvt.harness.configuration.DefaultConfiguration;
import org.jboss.pvt.harness.configuration.PVTConfiguration;
import org.jboss.pvt.harness.validators.JBossSignatureCheckValidator;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ProvideSystemProperty;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;

import java.io.File;

/**
 * Created by yyang on 8/3/16.
 */
public class JBossSignatureCheckValidatorTest {
    @Rule
    public final ProvideSystemProperty provideSystemProperty  = new ProvideSystemProperty
                    ( "PVTCFG", this.getClass().getResource( "/pvt.yaml").getFile().toString());

    public PVTConfiguration pvtConfiguration;

    public JBossSignatureCheckValidator test =  new JBossSignatureCheckValidator();

    @Before
    public void before( )
    {
        pvtConfiguration = new DefaultConfiguration();
    }

    @Test
    public void testSigned() throws Exception{
        test.validate(pvtConfiguration);
    }
}
