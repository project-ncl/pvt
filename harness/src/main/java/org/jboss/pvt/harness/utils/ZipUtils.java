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

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyang on 7/18/16.
 */
public class ZipUtils {


    public static List<String> unzip(File tarFile) throws IOException {
        return unzip(tarFile, new File("."));
    }

    /**
     * Extract all files from Tar into the specified directory
     *
     * @param tarFile
     * @param directory
     * @return the list of extracted filenames
     * @throws IOException
     */
    public static List<String> unzip(File tarFile, File directory) throws IOException {
        List<String> result = new ArrayList<String>();
        InputStream inputStream = new FileInputStream(tarFile);
        ZipArchiveInputStream in = new ZipArchiveInputStream(inputStream);
        ZipArchiveEntry entry = in.getNextZipEntry();
        while (entry != null) {
            if (entry.isDirectory()) {
                entry = in.getNextZipEntry();
                continue;
            }
            File curfile = new File(directory, entry.getName());
            File parent = curfile.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            OutputStream out = new FileOutputStream(curfile);
            IOUtils.copy(in, out);
            out.close();
            result.add(entry.getName());
            entry = in.getNextZipEntry();
        }
        in.close();
        return result;
    }

    /**
     * unzip by unzip4j
     * @param zipFilename
     * @param outDir
     * @throws Exception
    public void unzip4j(String zipFilename, String outDir) throws Exception {
        ZipFile zipFile = new ZipFile(zipFilename);
        zipFile.extractAll(outDir);
    }
     */
}
