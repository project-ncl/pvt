package org.jboss.pvt2.utils;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yyang on 7/18/16.
 */
public class HttpUtils {

    public static void httpDownload(String httpUrl) throws Exception {
        String filename = httpUrl.substring(httpUrl.lastIndexOf("/") + 1);
        InputStream in = new URL( httpUrl).openStream();
        try {
            IOUtils.copy(in, new FileOutputStream(filename));
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public static void httpDownload(String httpUrl,String saveFile) throws Exception {
        InputStream in = new URL( httpUrl).openStream();
        try {
            IOUtils.copy(in, new FileOutputStream(saveFile));
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
