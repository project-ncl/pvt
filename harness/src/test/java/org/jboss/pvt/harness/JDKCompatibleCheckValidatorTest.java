package org.jboss.pvt.harness;

import org.jboss.pvt.harness.configuration.DefaultConfiguration;
import org.jboss.pvt.harness.configuration.PVTConfiguration;
import org.jboss.pvt.harness.validators.JBossSignatureCheckValidator;
import org.jboss.pvt.harness.validators.JDKCompatibleCheckValidator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ProvideSystemProperty;

/**
 * Created by yyang on 8/3/16.
 */
public class JDKCompatibleCheckValidatorTest {
    @Rule
    public final ProvideSystemProperty provideSystemProperty  = new ProvideSystemProperty
                    ( "PVTCFG", this.getClass().getResource( "/pvt.yaml").getFile().toString());

    public PVTConfiguration pvtConfiguration;

    public JDKCompatibleCheckValidator test =  new JDKCompatibleCheckValidator();

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
