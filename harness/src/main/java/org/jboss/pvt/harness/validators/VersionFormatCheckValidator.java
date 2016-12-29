package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.configuration.ConfigurationLoader;
import org.jboss.pvt.harness.exception.PVTException;
import org.jboss.pvt.harness.utils.DirUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class VersionFormatCheckValidator extends AbstractJarsValidator {

    private static final String DEFAULT_FORMAT = ".*[\\.-]redhat-\\d+.*";

    public static final String PARAM_FORMAT = "format";

    @Override
    protected boolean validate(File jarFile, Map<String, String> params) throws Exception {
        String format = DEFAULT_FORMAT;
        if(params.containsKey(PARAM_FORMAT)){
            format = params.get(PARAM_FORMAT).trim();
        }
        return jarFile.getAbsolutePath().matches(format);
    }

}
