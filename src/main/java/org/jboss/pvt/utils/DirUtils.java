package org.jboss.pvt.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by yyang on 7/28/16.
 */
public class DirUtils {

    /**
     * list accepted files in rootDir and its sub-dirs
     * @param rootDir
     * @param fileFilter
     * @return
     */
    public static Collection<File> listFilesRecursively(File rootDir, final FileFilter fileFilter){
        if(!rootDir.exists() || rootDir.isFile()) {
            return Collections.emptyList();
        }
        Collection<File> files = FileUtils.listFilesAndDirs(rootDir, new AbstractFileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && fileFilter.accept(file);
            }

        }, TrueFileFilter.INSTANCE);

        // remove dirs
        for(Iterator<File> it= files.iterator(); it.hasNext(); ) {
            File file = it.next();
            if(file.isDirectory()) {
                it.remove();
            }
        }
        return files;
    }
}
