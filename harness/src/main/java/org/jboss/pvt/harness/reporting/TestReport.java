package org.jboss.pvt.harness.reporting;

import org.jboss.pvt.harness.configuration.pojo.TestConfig;
import org.jboss.pvt.harness.validators.JarsValidation;
import org.jboss.pvt.harness.validators.Validation;

/**
 * Report for a Test
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class TestReport {
    private String testCase;
    private TestConfig testConfig;
    private Validation validation;

    public TestReport(String testCase, TestConfig testConfig) {
        this.testCase = testCase;
        this.testConfig = testConfig;
    }

    public String getTestCase() {
        return testCase;
    }

    public TestConfig getTestConfig() {
        return testConfig;
    }

    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    public long getDuring() {
        if(validation != null) {
            return validation.getDuring();
        }
        else {
            return 0;
        }
    }

    public boolean isValid(){
        return validation.isValid();
    }

    @Override
    public String toString() {
        String s = "TestReport{" +
                "testCase='" + testCase + '\'';

        if(validation != null) {
            s+= ", validationResult='" + validation + "\'";
        }

        s +='}';
        return s;
    }

}
