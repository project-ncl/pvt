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

import org.jboss.pvt.harness.validators.NOSignatureCheckValidator;
import org.jboss.pvt.harness.validators.Validator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * This job checks for absence of sha1 digests in manifest, that cause performance degradation in certain JVMs
 * From version 7.0.0.ER7 job fails because of org/bouncycastle artifacts are digitally signed - this is correct. Other fails are real failures
 * Created by yyang on 7/11/16.
 */
public class NoSignatureCheckTest extends PVTSuperTestCase{

//    @Rule
//    TemporaryFolder tf = new TemporaryFolder();

    @Test
    public void testSign() throws Exception{
        assertTrue(test());
    }

    @Override
    protected Class<? extends Validator> getValidatorClass() {
        return NOSignatureCheckValidator.class;
    }
}
