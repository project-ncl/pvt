package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.configuration.ConfigurationLoader;
import org.jboss.pvt.harness.exception.PVTException;
import org.jboss.pvt.harness.utils.ClassVersion;
import org.jboss.pvt.harness.utils.ClassVersionInspector;
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
public class JDKCompatibleCheckValidator extends AbstractJarsValidator {

    private final Logger logger = LoggerFactory.getLogger( getClass() );

    private final static String PARAM_MIN_VERSION = "min-version";
    private final static String PARAM_MAX_VERSION = "max-version";


    @Override
    protected boolean validate(File jarFile, Map<String, String> params) throws Exception {
        if(!params.containsKey(PARAM_MIN_VERSION) && !params.containsKey(PARAM_MAX_VERSION)) {
            throw new IllegalArgumentException("Please set format param, ex: params: {min-version: '48', 'max-version':'52'}");
        }
        int minVersion = params.containsKey(PARAM_MIN_VERSION) ? Integer.parseInt( params.get(PARAM_MIN_VERSION)) : 0;
        int maxVersion = params.containsKey(PARAM_MAX_VERSION) ? Integer.parseInt( params.get(PARAM_MAX_VERSION)) : Integer.MAX_VALUE;

        if(!ClassVersionInspector.checkJarVersion(jarFile.toString(), ClassVersion.parseInt(minVersion), ClassVersion.parseInt(maxVersion))) {
            return false;
        }
        return true;
    }
}
