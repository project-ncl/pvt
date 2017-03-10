package org.jboss.pvt.harness.utils;

import java.io.File;
import java.util.List;

/**
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class ValidatorUtils {

    public static boolean filter(File file, List<String> filters){
        for(String filter : filters) {
            if(filter.trim().isEmpty()) {
                continue;
            }
            if(file.getAbsolutePath().matches(filter)) {
                return true;
            }
        }
        return false;
    }
}
