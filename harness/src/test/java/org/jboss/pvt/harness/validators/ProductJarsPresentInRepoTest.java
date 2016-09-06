package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.TestConfiguration;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.Assert.assertTrue;

/**
 * Created by rnc on 12/08/16.
 */
public class ProductJarsPresentInRepoTest
{
    @ClassRule
    public static TestConfiguration tc = new TestConfiguration();

    @Rule
    public TestName testName = new TestName();

    private ProductJarsPresentInRepoValidator pjp;

    @Before
    public void setUp() throws Exception
    {
        pjp = new ProductJarsPresentInRepoValidator();
    }

    @Test
    public void validate() throws Exception
    {
        assertTrue( pjp.validate(tc) );
    }

    @Test
    @Ignore
    public void initialiseFilter() throws Exception
    {

    }
}