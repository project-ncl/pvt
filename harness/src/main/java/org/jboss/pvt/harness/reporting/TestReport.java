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

    // execution time in seconds
    private long during;

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

    public long getDuring() {
        return during;
    }

    public void setDuring(long during) {
        this.during = during;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    @Override
    public String toString() {
        return "TestReport{" +
                "testCase='" + testCase + '\'' +
                ", during=" + during +
                ", validationResult=" + validationResult +
                '}';
    }
}
