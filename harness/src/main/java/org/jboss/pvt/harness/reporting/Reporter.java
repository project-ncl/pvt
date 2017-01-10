package org.jboss.pvt.harness.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Report Writer
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public abstract class Reporter {

    public static final String DEFAULT_TEMPLATE = "/report.template.html";
    public static final String DEFAULT_OUTPUT_PATH = "pvt_report_%{product}_%{version}_%{date}.html";

    private String outFile;

    private final static Reporter freemarkerReport = new FreemarkerReporter();

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public static Reporter getFreemarkerReporter(){
        return freemarkerReport;
    }

    public Reporter() {
        this(DEFAULT_OUTPUT_PATH);
    }

    public Reporter(String outFile) {
        this.outFile = outFile;
    }

    public String getOutFile(Report report) {
        String out = outFile.replace("%{product}", report.getConfiguration().getProduct())
                .replace("%{version}",report.getConfiguration().getVersion())
                .replace("%{date}", new SimpleDateFormat("yyMMdd").format(new Date()));
        return out;
    }

    public void render(Report report) throws Exception{
        String file = getOutFile(report);
        logger.info("Generating report to " + file);
        render(report, new File(file));
    }

    public abstract void render(Report report, File outFile) throws Exception;
}
