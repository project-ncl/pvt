package org.jboss.pvt.harness.reporting;

import java.io.File;

/**
 * Report Writer
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class Reporter {


    public static final String DEFAULT_OUTPUT_PATH = "./reports/pvt_report_%{product}_%{version}_%{date}.html";

    private String outFile;

    public Reporter() {
        this(DEFAULT_OUTPUT_PATH);
    }

    public Reporter(String outFile) {
        this.outFile = outFile;
    }

    public String getOutFile() {
        return outFile;
    }

    public void render(Report report) {
        render(report, new File(getOutFile()));
    }

    public static void render(Report report, File outFile) {

    }
}
