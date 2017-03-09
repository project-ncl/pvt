package org.jboss.pvt.harness.validators;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The result object returned by Validator
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public abstract class Validation {

    private boolean valid;
    private Object testcase;
    private Throwable throwable;
    private long during;
    private List<File> filtered = new ArrayList<>();

    public Validation(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<File> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<File> filtered) {
        this.filtered = filtered;
    }

    public Object getTestcase() {
        return testcase;
    }

    public void setTestcase(Object testcase) {
        this.testcase = testcase;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public long getDuring() {
        return during;
    }

    public void setDuring(long during) {
        this.during = during;
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                "valid=" + valid +
                ", during=" + during +
                ", filtered=" + filtered +
                '}';
    }

}
