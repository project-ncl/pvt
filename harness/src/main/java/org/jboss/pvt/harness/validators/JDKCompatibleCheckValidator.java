package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.configuration.PVTConfiguration;
import org.jboss.pvt.harness.exception.PVTException;
import org.jboss.pvt.harness.utils.ClassVersion;
import org.jboss.pvt.harness.utils.ClassVersionInspector;
import org.jboss.pvt.harness.utils.DirUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class JDKCompatibleCheckValidator implements Validator {

    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Override
    public boolean validate(PVTConfiguration pvtConfiguration) throws PVTException {
        File eapDir = pvtConfiguration.getDistributionDirectory();

        Collection<File> jarFiles = DirUtils.listFilesRecursively( eapDir, new FileFilter() {
            public boolean accept(File pathname) {
                return  pathname.isFile() && pathname.getName().endsWith(".jar");
            }
        });

        logger.info("Jars: " + Arrays.toString(jarFiles.toArray()));


        List<File> failedJars = new ArrayList<>();

        for (File jarFile : jarFiles) {
            if (jarFile.isFile()) {

                if (isFilter(pvtConfiguration, jarFile)) {
                    logger.warn("Excluding Jar, ", jarFile);
                    continue;
                }

                if(!ClassVersionInspector.checkJarVersion(jarFile.toString(), getVersion(pvtConfiguration)[0], getVersion(pvtConfiguration)[1])) {
                    failedJars.add(jarFile);
                }
            }
        }
        if(!failedJars.isEmpty()) {
            logger.error("JDK compatible check failed: ", Arrays.toString(failedJars.toArray()));
            return false;
        }
        return true;
    }

    protected List<String> getFilters(PVTConfiguration pvtConfiguration){
        return pvtConfiguration.getTestCase(this.getClass().getName()).getFilters();
    }

    protected boolean isFilter(PVTConfiguration pvtConfiguration, File jarFile){
        List<String> filters = getFilters(pvtConfiguration);
        for(String filter : filters){
            if(Pattern.compile(filter).matcher(jarFile.getAbsoluteFile().getName()).matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * get expected class version scope
     * @param pvtConfiguration
     * @return
     */
    protected ClassVersion[] getVersion(PVTConfiguration pvtConfiguration){
        return new ClassVersion[]{ClassVersion.JAVA_0, ClassVersion.JAVA_18};
    }
}
