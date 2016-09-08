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

import java.io.File;
import java.net.URL;

/**
 * Created by yyang on 7/18/16.
 */
public class HttpUtils {

    public static void httpDownload(String httpUrl) throws Exception {
        String filename = httpUrl.substring(httpUrl.lastIndexOf("/") + 1);
        httpDownload(httpUrl, filename);
    }

    public static void httpDownload(String httpUrl,String saveFile) throws Exception {
        FileUtils.copyURLToFile(new URL(httpUrl), new File(saveFile));
    }
}
