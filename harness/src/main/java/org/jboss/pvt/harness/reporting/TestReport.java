package org.jboss.pvt.harness.reporting;

import org.jboss.pvt.harness.configuration.pojo.TestConfig;
import org.jboss.pvt.harness.validators.ValidationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Report for a Test
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class TestReport {
    private String testCase;
    private TestConfig testConfig;
    private ValidationResult validationResult;

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

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    public long getDuring() {
        if(validationResult != null) {
            return validationResult.getDuring();
        }
        else {
            return 0;
        }
    }

    @Override
    public String toString() {
        String s = "TestReport{" +
                "testCase='" + testCase + '\'';

        if(validationResult != null) {
            s+= ", validationResult='" + validationResult + "\'";
        }

        s +='}';
        return s;
    }

}
