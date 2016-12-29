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

package org.jboss.pvt.harness.validators;

import org.apache.commons.io.IOUtils;
import org.jboss.pvt.harness.configuration.ConfigurationLoader;
import org.jboss.pvt.harness.exception.PVTException;
import org.jboss.pvt.harness.exception.PVTSystemException;
import org.jboss.pvt.harness.utils.DirUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipFile;

/**
 * Check jars are not signed
 *
 * Created by yyang on 7/11/16.
 */

public class NOSignatureCheckValidator extends AbstractJarsValidator{

    @Override
    protected boolean validate(File jarFile, Map<String, String> params) throws Exception {
        ZipFile zipFile = new ZipFile(jarFile);
        StringWriter sw = new StringWriter();
        IOUtils.copy(new InputStreamReader(zipFile.getInputStream(zipFile.getEntry("META-INF/MANIFEST.MF"))), sw);
        return !sw.getBuffer().toString().contains("Digest:");
    }
}
