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

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * EAP PVT Test suite
 *
 * Created by yyang on 7/11/16.
 */

@RunWith(Categories.class)
@Categories.IncludeCategory(EAP7.class)
@Suite.SuiteClasses({
        ResourceTest.class,
        EAPJarDigestUnSignedTest.class,
        JarsPresentInRepoTest.class
})
public class EAP7TestSuite {

    public static EAPTestConfig getTestConfig() {
        return EAPTestConfig.getInstance();
    }


    /**
     * Created by yyang on 7/18/16.
     */
    public static class EAPTestConfig {

        public static final String EAP_VERSION_KEY = "EAP_VERSION";
        public static final String EAP_VERSION_LAST_KEY = "EAP_VERSION_LAST";
        public static final String EAP_TARGET_RELEASE_VERSION = "EAP_TARGET_RELEASE_VERSION";
        public static final String EAP_ZIP_URL_KEY = "EAP_ZIP_URL";
        public static final String EAP_ZIP_NAME_KEY = "EAP_ZIP_NAME";
        public static final String EAP_DIR_KEY = "EAP_DIR";
        public static final String EAP_SRC_ZIP_URL_KEY = "EAP_SRC_ZIP_URL";
        public static final String EAP_SRC_ZIP_NAME_KEY = "EAP_SRC_ZIP_NAME";
        public static final String EAP_SRC_DIR_KEY ="EAP_SRC_DIR";
        public static final String MEAD_REPO_URL_KEY = "MEAD_REPO_URL";
        public static final String REPO_ZIP_URL_KEY = "REPO_ZIP_URL";
        public static final String REPO_ZIP_NAME_KEY = "REPO_ZIP_NAME";
        public static final String REPO_DIR_KEY = "REPO_DIR";
        public static final String QUICKSTARTS_ZIP_URL_KEY = "QUICKSTARTS_ZIP_URL";
        public static final String QUICKSTARTS_ZIP_NAME_KEY = "QUICKSTARTS_ZIP_NAME";
        public static final String QUICKSTARTS_DIR_KEY = "QUICKSTARTS_DIR";

        private static EAPTestConfig INSTANCE = new EAPTestConfig();

        private EAPTestConfig() {

        }

        public static EAPTestConfig getInstance() {
            return INSTANCE;
        }

        public String getEapDir(){
            return System.getProperty(EAP_DIR_KEY);
        }

        public String getEapZipUrl() {
            return System.getProperty(EAP_ZIP_URL_KEY).replace("${EAP_VERSION}", getEapVersion());
        }

        public String getEapZipName(){
            return System.getProperty(EAP_ZIP_NAME_KEY, getEapZipUrl().substring(getEapZipUrl().lastIndexOf("/") + 1));
        }

        private String getEapVersion(){
            return System.getProperty(EAP_VERSION_KEY);
        }

        public String getRepoDir(){
            return System.getProperty(REPO_DIR_KEY);
        }

        public String getRepoZipUrl() {
            return System.getProperty(REPO_ZIP_URL_KEY).replace("${EAP_VERSION}", getEapVersion());
        }

        public String getRepoZipName(){
            return System.getProperty(REPO_ZIP_NAME_KEY, getRepoZipUrl().substring(getEapZipUrl().lastIndexOf("/") + 1));
        }


    }
}
