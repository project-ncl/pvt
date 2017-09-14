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
    public static final String DEFAULT_OUTPUT_PATH = "../target/pvt_report_%{product}_%{version}.html";
    public static final String DEFAULT_OUTPUT_DIRECTORY = "../target";
    public static final String DEFAULT_OUTPUT_NAME = "/pvt_report_%{product}_%{version}.html";
    public static final String OUTPUT_ENDING = ".html";


    public static final String DEFAULT_HANDOVER_SUMMARY_TEMPLATE = "/handover-summary.adoc";
    public static final String DEFAULT_HANDOVER_SUMMARY_OUTPUT_PATH = "../target/pvt_handover_summary_%{product}_%{version}.adoc";
    public static final String DEFAULT_HANDOVER_SUMMARY_OUTPUT_DIRETORY = "../target";
    public static final String DEFAUL_HANDOVER_SUMMARY_OUTPUT_NAME = "/pvt_report_%{product}_%{version}.adoc";
    public static final String HANDOVER_SUMMARY_OUTPUT_ENDING = ".adoc";

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


    public void render(Report report, String location) throws Exception{
        
        String out_html = location.concat(OUTPUT_ENDING);
        logger.info("Generating report to " + out_html);
        render(report,DEFAULT_TEMPLATE, new File(out_html));
       
        String out_adoc = location.concat(HANDOVER_SUMMARY_OUTPUT_ENDING);
        logger.info("Generating handover summary doc to " + out_adoc);
        render(report, DEFAULT_HANDOVER_SUMMARY_TEMPLATE, new File(out_adoc));
        
    }

    public abstract void render(Report report, String templateFile,File outFile) throws Exception;
}
