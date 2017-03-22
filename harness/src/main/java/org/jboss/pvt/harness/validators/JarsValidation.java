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

    public JarsValidation(boolean valid, List<File> filtered, List<File> passed, List<File> notPassed) {
        super(valid, filtered);
        this.setFiltered(filtered);
        this.passed = passed;
        this.setFailed(notPassed);
    }

    public static JarsValidation pass( List<File> filtered, List<File> passed){
        return new JarsValidation(true,filtered, passed, Collections.emptyList());
    }

    public static JarsValidation notPass(List<File> filtered, List<File> passed, List<File> notPassed){
        return new JarsValidation(false, filtered, passed, notPassed);
    }

    public List<File> getPassed() {
        return passed;
    }

}
