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

import org.jboss.pvt.harness.configuration.DefaultConfiguration;

import java.io.File;
import java.util.List;

/**
 * Created by rnc on 08/11/16.
 */
public class TestConfiguration extends DefaultConfiguration
{
    public void updateConfiguration( File distributionDirectory, File sourceDistributionDirectory, File mavenRepositoryDirectory, List<File> auxillaryDistributions )
    {
        this.distributionDirectory = distributionDirectory;
        this.sourceDistributionDirectory = sourceDistributionDirectory;
        this.mavenRepositoryDirectory = mavenRepositoryDirectory;
        this.auxillaryDistributions = auxillaryDistributions;
    }
}
