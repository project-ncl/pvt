package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.exception.PVTException;
import org.jboss.pvt.harness.configuration.ParameterHandler;

/**
 * Created by rnc on 02/08/16.
 */
public interface Validator<T>
{
    ParameterHandler handler = new ParameterHandler();

    /**
     * Validation logic that should be applied.
     * @return true if it validates successfully.
     * @throws PVTException if an error occurs.
     */
    boolean validate() throws PVTException;

    /**
     * @return return the current ParameterHandler.
     */
    default ParameterHandler getParameterHandler()
    {
        System.out.println ("### Using instance " + System.identityHashCode( handler ));
        return handler;
    }

    /**
     * Apply a set of filters to a validator
     * // TODO : Define api here.
     * @param filtering
     */
    boolean filter (T... filtering);

    void initialiseFilter (String[] filters);
}
