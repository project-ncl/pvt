package org.jboss.pvt.harness.reporting;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class FreemarkerReporter extends Reporter{

    @Override
    public void render(Report report, String templateFile, File outFile) throws Exception {
        Configuration cfg = new Configuration();
        cfg.setTemplateLoader(new ClassTemplateLoader());
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        Template temp = cfg.getTemplate(templateFile);
        Map root = new HashMap();
        root.put("report", report);
        Writer out = new FileWriter(outFile);
        temp.process(root, out);
        out.flush();
    }
}
