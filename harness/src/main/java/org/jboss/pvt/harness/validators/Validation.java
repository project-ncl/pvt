package org.jboss.pvt.harness.validators;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    private List<File> failed = new ArrayList<>();


    public Validation(boolean valid) {
        this.valid = valid;
    }

    public Validation(boolean valid, List<File> filtered) {
        this.valid = valid;
        this.filtered = filtered;
    }

    public Validation(List<File> filtered, List<File> failed) {
        this.valid = false;
        this.filtered = filtered;
        this.failed = failed;
    }

    public List<File> getFailed() {
        return failed;
    }

    public void setFailed(List<File> failed) {
        this.failed = failed;
        if(!failed.isEmpty()) {
            this.valid = false;
        }
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

    public Map<String, List<String>> getGroupedFiltered() {
        return createGroupedMap(getFiltered());
    }

    public Map<String, List<String>> getGroupedFailed() {
        return createGroupedMap(getFailed());
    }

    protected Map<String, List<String>> createGroupedMap(List<File> data) {
        Map<String, List<String>> groupedMap = new TreeMap<>();
        for(File file : data){
            String key = file.getName();
            String value = file.getParentFile().getPath();
            List<String> values = new ArrayList<>();
            if(groupedMap.containsKey(key)){
                values = groupedMap.get(key);
            }
            values.add(value);
            groupedMap.put(key, values);
        }
        return groupedMap;
    }

}
