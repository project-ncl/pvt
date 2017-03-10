package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.utils.DirUtils;
import org.jboss.pvt.harness.utils.ResourceUtils;
import org.jboss.pvt.harness.utils.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

/**
 * Abstract Validator which validate against jars in the distributed zips
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public abstract class AbstractJarsValidator extends AbstractValidator<JarsValidation> {

    protected final Logger logger = LoggerFactory.getLogger( getClass() );

    @Override
    public JarsValidation validate(List<String> resources, List<String> filters, Map<String, String> params) throws Exception {
        boolean passed = true;
        long startTime = System.currentTimeMillis();
        List<File> passedJars = new ArrayList<>();
        List<File> notPassedJars = new ArrayList<>();
        List<File> filterJars = new ArrayList<>();
        for(String resource : resources) {
            File exploredDir = ResourceUtils.downloadZipExplored(resource);
            Collection<File> jarFiles = DirUtils.listFilesRecursively( exploredDir, new FileFilter() {
                public boolean accept(File pathname) {
                    return  pathname.isFile() && pathname.getName().endsWith(".jar");
                }
            });

            for(File jarFile : jarFiles) {
                if (ValidatorUtils.filter(jarFile, filters)) {
                    filterJars.add(jarFile);
                }
            }

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
        }
        logger.warn("Filtered jars: " + Arrays.toString(filterJars.toArray()));
        logger.warn("Not passed jars: " + Arrays.toString(notPassedJars.toArray()));
        logger.debug("Passed jars: " + Arrays.toString(passedJars.toArray()));
        logger.info("VALIDATION RESULT: passed=" + passed);
        return passed ?
                JarsValidation.pass(System.currentTimeMillis()-startTime, (filterJars), (passedJars))
                :
                JarsValidation.notPass(System.currentTimeMillis()-startTime, (filterJars), (passedJars), (notPassedJars));
    }

    private static List<String> toStringList(List<File> files){
        List<String> list = new ArrayList<>(files.size());
        for(File file : files){
            list.add(file.getPath());
        }
        return list;
    }

    protected abstract boolean validate(File jarFile,  Map<String, String> params) throws Exception;
}
