package org.jboss.pvt.harness.reporting;

import org.jboss.pvt.harness.configuration.pojo.Configuration;
import org.jboss.pvt.harness.configuration.pojo.TestConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Report for a TestSuite
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class Report {
    private Configuration configuration;
    private Map<String, TestReport> testReports = new HashMap<>();

    // execution time in seconds
    private long during;

    public Report(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Map<String, TestReport> getTestReports() {
        return testReports;
    }

    public void setTestReports(Map<String, TestReport> testReports) {
        this.testReports = testReports;
    }

    public void addTestReport(TestReport testReport){
        this.testReports.put(testReport.getTestCase(), testReport);
        this.during += testReport.getDuring();
    }

    public long getDuring() {
        return during;
    }

    public void setDuring(long during) {
        this.during = during;
    }


    @Override
    public String toString() {
        return "Report{" +
                "during=" + during +
                ", testReports=" + testReports +
                '}';
    }
}


