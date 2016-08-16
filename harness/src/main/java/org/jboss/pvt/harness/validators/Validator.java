package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.configuration.PVTConfiguration;
import org.jboss.pvt.harness.exception.PVTException;

/**
 * Created by rnc on 02/08/16.
 */
public interface Validator
{
    public static enum Result {
        TRUE, FALSE, UNDETERMINED;

        @Override
        public String toString() {
            return super.toString();
        }

        public boolean getBooleanResult(){
            if(this.equals(TRUE)) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    /**
     * Validation logic that should be applied.
     * @param pvtConfiguration pvt configuration instance, include the filters,
     *                         filter_key is = ${classname}.filter
     *                         ex:
     * @return true if it validates successfully.
     * @throws PVTException if an error occurs.
     */
    Result validate(PVTConfiguration pvtConfiguration) throws PVTException;

}
