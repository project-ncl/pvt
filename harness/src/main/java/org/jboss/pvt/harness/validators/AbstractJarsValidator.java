package org.jboss.pvt.harness.validators;

import org.apache.commons.io.FileUtils;
import org.jboss.pvt.harness.exception.PVTException;
import org.jboss.pvt.harness.utils.DirUtils;
import org.jboss.pvt.harness.utils.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Abstract Validator which validate against jars in the distributed zips
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public abstract class AbstractJarsValidator implements Validator {

    protected final Logger logger = LoggerFactory.getLogger( getClass() );

    @Override
    public boolean validate(List<String> resources, List<String> filters, Map<String, String> params) throws Exception {
        boolean passed = true;
        for(String resource : resources) {
            File exploredDir = ResourceUtil.downloadZips(resource);
            Collection<File> jarFiles = DirUtils.listFilesRecursively( exploredDir, new FileFilter() {
                public boolean accept(File pathname) {
                    return  pathname.isFile() && pathname.getName().endsWith(".jar");
                }
            });

            List<File> filterJars = new ArrayList<>();
            for(File jarFile : jarFiles) {
                if (filter(jarFile, filters)) {
                    filterJars.add(jarFile);
                }
            }
            logger.warn("Filtered jars from  " + resource + ": " + Arrays.toString(filterJars.toArray()));

            List<File> passedJars = new ArrayList<>();
            List<File> notPassedJars = new ArrayList<>();
            for(File jarFile : jarFiles) {
                if (!filterJars.contains(jarFile)) {
                    boolean _passed = validate(jarFile, params);
                    if(!_passed) {
                        notPassedJars.add(jarFile);
                        passed = false;
                    }
                    else {
                        passedJars.add(jarFile);
                    }
                }
            }
            logger.warn("Not passed: " + Arrays.toString(notPassedJars.toArray()));
            logger.warn("Passed: " + Arrays.toString(passedJars.toArray()));
        }
        logger.info("VALIDATION RESULT: passed=" + passed);
        return passed;
    }

    public boolean filter(File jarFile,  List<String> filters){
        for(String filter : filters) {
            if(filter.trim().isEmpty()) {
                continue;
            }
            if(jarFile.getName().matches(filter)) {
                return true;
            }
        }
        return false;
    }

    protected abstract boolean validate(File jarFile,  Map<String, String> params) throws Exception;
}
