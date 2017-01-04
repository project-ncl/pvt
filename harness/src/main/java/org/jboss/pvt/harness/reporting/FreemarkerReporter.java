package org.jboss.pvt.harness.reporting;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class FreemarkerReporter extends Reporter{

    @Override
    public void render(Report report, File outFile) throws Exception {
        Configuration cfg = new Configuration();
        cfg.setTemplateLoader(new ClassTemplateLoader());
        Template temp = cfg.getTemplate(DEFAULT_TEMPLATE);
        Map root = new HashMap();
        root.put("report", report);
        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
        out.flush();
    }
}
