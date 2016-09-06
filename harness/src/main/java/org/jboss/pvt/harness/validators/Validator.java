package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.configuration.PVTConfiguration;
import org.jboss.pvt.harness.exception.PVTException;

/**
 * Created by rnc on 02/08/16.
 */
public interface Validator
{
    /**
     * Validation logic that should be applied.
     * @param pvtConfiguration pvt configuration instance, include the filters,
     *                         filter_key is = ${classname}.filter
     *                         ex:
     * @return true if it validates successfully.
     * @throws PVTException if an error occurs.
     */
    boolean validate( PVTConfiguration pvtConfiguration) throws PVTException;

}
