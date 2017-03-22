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

import org.jboss.pvt.harness.exception.PVTException;

import java.util.List;
import java.util.Map;

/**
 * Created by yyang on 7/27/16.
 */
public class RepositoryBomsValidator implements Validator
{
    @Override
    public Validation validate(List resources, List filters, Map params) throws Exception {
        return null;
    }
//TODO: https://jenkins.mw.lab.eng.bos.redhat.com/hudson/view/EAP7/view/EAP7-Prod/job/jboss-eap-7.0.x-handoff-check-eap-maven-repository-boms/
}
