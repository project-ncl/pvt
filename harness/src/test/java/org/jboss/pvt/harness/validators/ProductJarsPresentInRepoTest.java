package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.configuration.DefaultConfiguration;
import org.jboss.pvt.harness.configuration.PVTConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ProvideSystemProperty;
import org.junit.rules.TestName;

import static org.junit.Assert.assertTrue;

/**
 * Created by rnc on 12/08/16.
 */
public class ProductJarsPresentInRepoTest
{
    @Rule
    public final ProvideSystemProperty provideSystemProperty  = new ProvideSystemProperty
                    ( "PVTCFG", this.getClass().getResource( "/pvt.yaml").getFile().toString());

    @Rule
    public TestName testName = new TestName();

    private ProductJarsPresentInRepoValidator pjp;

    public PVTConfiguration tc;

    @Before
    public void setUp() throws Exception
    {
        tc = new DefaultConfiguration();
        pjp = new ProductJarsPresentInRepoValidator();
    }

    @Test
    public void validate() throws Exception
    {
        assertTrue(! pjp.validate(tc) );
    }

    @Test
    @Ignore
    public void initialiseFilter() throws Exception
    {

    }
}