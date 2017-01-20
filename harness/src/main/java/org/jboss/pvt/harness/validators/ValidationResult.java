package org.jboss.pvt.harness.validators;

import java.io.File;
import java.util.*;

/**
 * Validation Result, include everything a test return
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class ValidationResult {

    private boolean valid = false;
    private List<File> filtered = new ArrayList<>();
    private List<File> passed = new ArrayList<>();
    private List<File> notPassed = new ArrayList<>();
    private Object testCaseObject;

    private Throwable throwable;

    private long during;



    private ValidationResult(boolean valid, long during, List<File> filtered, List<File> passed, List<File> notPassed) {
        this.valid = valid;
        this.during = during;
        this.filtered = filtered;
        this.passed = passed;
        this.notPassed = notPassed;
    }

    public static ValidationResult pass(long during, List<File> filtered, List<File> passed){
        return new ValidationResult(true, during, filtered, passed, Collections.emptyList());
    }

    public static ValidationResult notPass(long during, List<File> filtered, List<File> passed, List<File> notPassed){
        return new ValidationResult(false, during, filtered, passed, notPassed);
    }

    public void setTestCaseObject(Object testCaseObject) {
        this.testCaseObject = testCaseObject;
    }

    public Object getTestCaseObject() {
        return testCaseObject;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public boolean isValid() {
        return valid;
    }

    public List<File> getFiltered() {
        return filtered;
    }

    public List<File> getPassed() {
        return passed;
    }

    public List<File> getNotPassed() {
        return notPassed;
    }

    public Map<String, List<String>> getGroupedFiltered() {
        return createGroupedMap(filtered);
    }
    public Map<String, List<String>> getGroupedNotPassed() {
        return createGroupedMap(notPassed);
    }

    private Map<String, List<String>> createGroupedMap(List<File> data) {
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

    public long getDuring() {
        return during;
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                "valid=" + valid +
                ", during=" + during +
                ", filtered=" + filtered +
                ", passed=" + passed +
                ", notPassed=" + notPassed +
                '}';
    }
}
