package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.exception.PVTException;
import org.jboss.pvt.harness.exception.PVTSystemException;
import org.jboss.pvt.harness.rules.ParameterHandler;

/**
 * Created by rnc on 02/08/16.
 */
public abstract class Validator<T>
{
    private ParameterHandler handler;

    public Validator (ParameterHandler handler)
    {
        this.handler = handler;
    }

    /**
     * Validation logic that should be applied.
     * @return true if it validates successfully.
     * @throws PVTException if an error occurs.
     */
    public abstract boolean validate() throws PVTException;

    /**
     * @return return the current ParameterHandler.
     */
    public ParameterHandler getParameterHandler()
    {
        if ( handler == null)
        {
            throw new PVTSystemException( "Null parameter handler" );
        }
        return handler;
    }

    /**
     * Apply a set of filters to a validator
     * // TODO : Define api here.
     * @param filtering
     */
    public abstract boolean filter (T... filtering);

    public abstract void initialiseFilter (String[] filters);
}
