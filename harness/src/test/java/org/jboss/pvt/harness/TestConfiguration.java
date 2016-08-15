package org.jboss.pvt.harness;

import org.jboss.pvt.harness.configuration.PVTConfiguration;
import org.jboss.pvt.harness.exception.PVTSystemException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.File;
import java.util.Properties;

/**
 * Created by rnc on 12/08/16.
 */
public class TestConfiguration implements PVTConfiguration, TestRule
{

    // TODO: extend ExternalResource to make a JUnit Rule and setup e.g. nested temporary file structure
    // to represent a mock configuration.
    // Possibly use a RuleChain to use other rules e.g. TemporaryFolder to setup a dummy 'unpacked' distribution
    // zip.

    @Override
    public File getDistribution()
    {
        return null;
    }

    @Override
    public File getDistributionDirectory()
    {
        return new File(".");
    }

    @Override
    public File getRepositoryDirectory()
    {
        return new File(".");
    }

    @Override
    public Properties getAllConfiguration()
    {
        return null;
    }

    @Override
    public String[] getTestFilter( String testName )
    {
        return new String[0];
    }

    /**
     * Modifies the method-running {@link Statement} to implement this
     * test-running rule.
     *
     * @param base The {@link Statement} to be modified
     * @param description A {@link Description} of the test implemented in {@code base}
     * @return a new statement, which may be the same as {@code base},
     *         a wrapper around {@code base}, or a completely new Statement.
     */
    @Override
    public Statement apply( Statement base, Description description )
    {
//        throw new PVTSystemException( "NYI" );
        return base;
    }
}
