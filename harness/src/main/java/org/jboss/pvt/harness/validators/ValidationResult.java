package org.jboss.pvt.harness.validators;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class ValidationResult {

    private boolean valid = false;
    private List<String> filtered = new ArrayList<>();
    private List<String> passed = new ArrayList<>();
    private List<String> notPassed = new ArrayList<>();

    private Throwable throwable;

    private long during;

    private ValidationResult(boolean valid, long during, List<String> filtered, List<String> passed, List<String> notPassed) {
        this.valid = valid;
        this.during = during;
        this.filtered = filtered;
        this.passed = passed;
        this.notPassed = notPassed;
    }

    public static ValidationResult pass(long during, List<String> filtered, List<String> passed){
        return new ValidationResult(true, during, filtered, passed, Collections.emptyList());
    }

    public static ValidationResult notPass(long during, List<String> filtered, List<String> passed, List<String> notPassed){
        return new ValidationResult(false, during, filtered, passed, notPassed);
    }

    private static List<String> toStringList(List<File> files){
        List<String> list = new ArrayList<>(files.size());
        for(File file : files){
            list.add(file.getPath());
        }
        return list;
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

    public List<String> getFiltered() {
        return filtered;
    }

    public List<String> getPassed() {
        return passed;
    }

    public List<String> getNotPassed() {
        return notPassed;
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
