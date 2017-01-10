package org.jboss.pvt.harness.reporting;

import org.jboss.pvt.harness.configuration.pojo.Configuration;

import java.util.*;

/**
 * Report for a TestSuite
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class Report {
    private Configuration configuration;
    private Map<String, TestReport> testReports = new TreeMap<>();

    public Report(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Map<String, TestReport> getTestReports() {
        return testReports;
    }

    public Map<String, TestReport> getNotPassedTestReports() {
        Map<String, TestReport> notPassedTestReports = new TreeMap<>();
        for(Map.Entry<String, TestReport> entry : testReports.entrySet()){
            if(!entry.getValue().isValid()) {
                notPassedTestReports.put(entry.getKey(), entry.getValue());
            }
        }
        return notPassedTestReports;
    }

    public TestReport getTestReport(Class testClazz) {
        return getTestReport(testClazz.getName());
    }

    public TestReport getTestReport(String testClazz) {
        return testReports.get(testClazz);
    }

    public void setTestReports(Map<String, TestReport> testReports) {
        this.testReports = testReports;
    }

    public void addTestReport(TestReport testReport){
        this.testReports.put(testReport.getTestCase(), testReport);
    }

    public long getDuring() {
        long during = 0;
        for(TestReport testReport : testReports.values()){
            during += testReport.getDuring();
        }
        return during;
    }

    public int getCount() {
        return testReports.size();
    }

    public int getPassed(){
        int passed = 0;
        for(TestReport testReport : testReports.values()) {
            if(testReport.getValidationResult().isValid()) {
                passed++;
            }
        }
        return passed;
    }

    public int getNotPassed(){
        int notPassed = 0;
        for(TestReport testReport : testReports.values()) {
            if(!testReport.getValidationResult().isValid()) {
                notPassed++;
            }
        }
        return notPassed;
    }

    @Override
    public String toString() {
        return "Report{" +
                "during=" + getDuring() +
                "ms, testReports=" + testReports +
                '}';
    }
}


