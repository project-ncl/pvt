package org.jboss.pvt.harness.validators;

import java.io.File;
import java.util.*;

/**
 * Validation Result for JarsValidator, include everything a test return
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class JarsValidation extends Validation{

    private List<File> passed = new ArrayList<>();
    private List<File> notPassed = new ArrayList<>();


    public JarsValidation(boolean valid, long during, List<File> filtered, List<File> passed, List<File> notPassed) {
        super(valid);
        this.setDuring(during);
        this.setFiltered(filtered);
        this.passed = passed;
        this.notPassed = notPassed;
    }

    public static JarsValidation pass(long during, List<File> filtered, List<File> passed){
        return new JarsValidation(true, during, filtered, passed, Collections.emptyList());
    }

    public static JarsValidation notPass(long during, List<File> filtered, List<File> passed, List<File> notPassed){
        return new JarsValidation(false, during, filtered, passed, notPassed);
    }

    public List<File> getPassed() {
        return passed;
    }

    public List<File> getNotPassed() {
        return notPassed;
    }


    public Map<String, List<String>> getGroupedFiltered() {
        return createGroupedMap(getFiltered());
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
}
