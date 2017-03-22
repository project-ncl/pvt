package org.jboss.pvt.harness.validators;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public abstract  class AbstractValidator<T extends Validation> implements Validator<T> {
    @Override
    public abstract T validate(List<String> resources, List<String> filters, Map<String, String> params) throws Exception;
}
