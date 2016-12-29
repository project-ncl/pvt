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
import org.jboss.pvt.harness.exception.PVTSystemException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;

import static org.jboss.pvt.harness.utils.ResourceUtil.downloadZips;
import static org.junit.Assert.*;

/**
 * Created by rnc on 02/10/16.
 */
public class ResourceUtilTest
{
    @Rule
    public TemporaryFolder tf = new TemporaryFolder(  );

    @Test(expected = PVTSystemException.class)
    public void invalidPathTest ()
    {
        downloadZips ("/foo/bar.zip");
    }

    @Test(expected = PVTSystemException.class)
    public void invalidFile () throws IOException
    {
        File tmpFile = tf.newFile();
        downloadZips (tmpFile.toString());
    }

    @Test
    public void validFile () throws IOException
    {
        File tmpFile = tf.newFile();
        File newFolder = tf.newFolder();
        (new File (newFolder, "xxx")).createNewFile();

        ZipUtil.pack( newFolder, tmpFile );
        downloadZips (tmpFile.toString());
    }

    @Test(expected = PVTSystemException.class)
    public void invalidURLTest () throws IOException
    {
        downloadZips ("http://www.foo.bar/my.zip");
    }

    @Test
    public void validURL () throws IOException
    {
        File located = downloadZips ("https://github.com/release-engineering/pom-manipulation-ext/archive/pom-manipulation-parent-2.4.zip");
        assertTrue( located.exists() );
        assertTrue( FileUtils.listFiles (located, null, true).size() > 0 );
    }

}