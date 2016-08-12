package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.TestConfiguration;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.Assert.*;

/**
 * Created by rnc on 12/08/16.
 */
public class ProductJarsPresentInRepoTest
{
    @ClassRule
    public static TestConfiguration tc = new TestConfiguration();

    @Rule
    public TestName testName = new TestName();

    private ProductJarsPresentInRepo pjp;

    @Before
    public void setUp() throws Exception
    {
        pjp = new ProductJarsPresentInRepo( tc );
    }

    @Test
    public void validate() throws Exception
    {
        pjp.initialiseFilter( ( tc.getTestFilter( testName.getMethodName() ) ) );
        assertTrue( pjp.validate() );
    }

    @Test
    @Ignore
    public void initialiseFilter() throws Exception
    {

    }
}