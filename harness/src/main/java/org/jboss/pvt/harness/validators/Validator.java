package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.configuration.DefaultConfiguration;
import org.jboss.pvt.harness.configuration.PVTConfiguration;
import org.jboss.pvt.harness.exception.PVTException;

/**
 * Created by rnc on 02/08/16.
 */
public interface Validator
{
    PVTConfiguration getConfiguration();

    /**
     * Validation logic that should be applied.
     * @return true if it validates successfully.
     * @throws PVTException if an error occurs.
     */
    boolean validate() throws PVTException;

    void initialiseFilter (String[] filters);
}
