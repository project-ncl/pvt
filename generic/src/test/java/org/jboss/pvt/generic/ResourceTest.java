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

package org.jboss.pvt.generic;

import org.jboss.pvt.harness.configuration.ConfigurationLoader;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

import static org.apache.commons.lang.StringUtils.isNotEmpty;
import static org.junit.Assert.assertTrue;

/**
 * ResourceTest should be on the first one of the test suite, to test the sources of EAP are accessable
 * And then, make the sources ready to used by the next tests.
 * Created by yyang on 7/11/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResourceTest
{
    private ConfigurationLoader defaultConfiguration;

    @Before
    public void setupBefore()
    {
//        defaultConfiguration = PVTTestSuite.yamlConfigLoader;

        // Don't bother running this test if the environment has not been set correctly.
        Assume.assumeTrue ( isNotEmpty ( System.getProperty( "PVTCFG" ) ) );
    }

    @Test
    public void testConfiguration()
    {
//        Assert.assertNotNull( defaultConfiguration.getDistributionDirectory() );
    }

    @Test
    public void testDownload1() throws Exception{
//        assertTrue( defaultConfiguration.getDistributionDirectory().exists() );

/*
        logger.info("Download EAP zip: " + EAP7TestSuite.EAPTestConfig.getInstance().getEapZipUrl());
        HttpUtils.httpDownload(EAP7TestSuite.EAPTestConfig.getInstance().getEapZipUrl());
        Assert.assertTrue(new File(EAP7TestSuite.EAPTestConfig.getInstance().getEapZipName()).exists());

        logger.info("Download Repo zip: " + EAP7TestSuite.EAPTestConfig.getInstance().getRepoZipUrl());
        HttpUtils.httpDownload(EAP7TestSuite.EAPTestConfig.getInstance().getRepoZipUrl());
        Assert.assertTrue(new File(EAP7TestSuite.EAPTestConfig.getInstance().getRepoZipName()).exists());
        */
    }

    @Test
    public void testDownload2() throws Exception{
        // We want to ensure it doesn't download again.
//        assertTrue( defaultConfiguration.getDistributionDirectory().exists() );
        /*
        logger.info("Download EAP zip: " + EAP7TestSuite.EAPTestConfig.getInstance().getEapZipUrl());
        HttpUtils.httpDownload(EAP7TestSuite.EAPTestConfig.getInstance().getEapZipUrl());
        Assert.assertTrue(new File(EAP7TestSuite.EAPTestConfig.getInstance().getEapZipName()).exists());

        logger.info("Download Repo zip: " + EAP7TestSuite.EAPTestConfig.getInstance().getRepoZipUrl());
        HttpUtils.httpDownload(EAP7TestSuite.EAPTestConfig.getInstance().getRepoZipUrl());
        Assert.assertTrue(new File(EAP7TestSuite.EAPTestConfig.getInstance().getRepoZipName()).exists());
        */
    }

    @Test
    public void testExtract() throws Exception{
/*
        logger.info("Extract: " + EAP7TestSuite.EAPTestConfig.getInstance().getEapZipName());
        File zipFile = new File( EAP7TestSuite.EAPTestConfig.getInstance().getEapZipName());
        Assert.assertTrue(zipFile.exists());
        ZipUtils.unzip(zipFile);

        logger.info("Extract: " + EAP7TestSuite.EAPTestConfig.getInstance().getRepoZipName());
        File repoFile = new File( EAP7TestSuite.EAPTestConfig.getInstance().getRepoZipName());
        */
//        File repoFile = defaultConfiguration.getDistributionDirectory();
//        assertTrue(repoFile.exists());
//        ZipUtil.explode( repoFile );
    }
}
