package org.jboss.pvt.harness.reporting;

import java.io.File;
import java.io.IOException;

/**
 * Report Writer
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public abstract class Reporter {

    public static final String DEFAULT_TEMPLATE = "/report.template.html";
    public static final String DEFAULT_OUTPUT_PATH = "./reports/pvt_report_%{product}_%{version}_%{date}.html";

    private String outFile;

    private final static Reporter freemarkerReport = new FreemarkerReporter();

    public static Reporter getFreemarkerReporter(){
        return freemarkerReport;
    }

    public Reporter() {
        this(DEFAULT_OUTPUT_PATH);
    }

    public Reporter(String outFile) {
        this.outFile = outFile;
    }

    public String getOutFile() {
        return outFile;
    }

    public void render(Report report) throws Exception{
        render(report, new File(getOutFile()));
    }

    public abstract void render(Report report, File outFile) throws Exception;
}
