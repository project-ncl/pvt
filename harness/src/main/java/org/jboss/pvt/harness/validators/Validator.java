package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.exception.PVTException;

import java.util.List;
import java.util.Map;

/**
 * Updated by yyang on 16/DEC/16
 * Created by rnc on 02/08/16.
 */
public interface Validator {
    /**
     * Validation logic that should be applied.
     * @param resources what resources[zips, jars, etc...] this validator run against
     * @param filters filters for the jars, the jars will not be validated
     * @param params the parameter map passed to this validator
     * @return true if it validates successfully.
     * @throws PVTException if an error occurs.
     */
    boolean validate(List<String> resources, List<String> filters, Map<String, String> params) throws Exception;

}
