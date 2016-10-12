/*
 * Copyright (C) 2016 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.pvt.harness.utils;

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
        if( rootDir == null || !rootDir.exists() || rootDir.isFile()) {
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
