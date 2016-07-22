package org.jboss.pvt2.utils;

import net.lingala.zip4j.core.ZipFile;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;

import java.io.*;
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
     */
    public void unzip4j(String zipFilename, String outDir) throws Exception {
        ZipFile zipFile = new ZipFile(zipFilename);
        zipFile.extractAll(outDir);
    }
}
