/**
 * The MIT License
 * Copyright (c) ${project.inceptionYear} Flemming Harms
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.fharms.services.it;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fharms.services.runner.EclipseIntegrationTestRunner;
/**
 * This show an example of using the {@link EclipseIntegrationTestRunner} to prevent
 * integration tests for running when you are executing unit test directly in Eclipse
 * 
 * The naming convention is important and should always follow the FailSafe naming rule
 * <pre>
 * https://maven.apache.org/surefire/maven-failsafe-plugin/integration-test-mojo.html#includes
 * </pre>
 * Otherwise when running from maven it will not recognize as integration test and will
 * run it as part of the test phase.
 * <p>
 * <p>
 * If you want to run the integration tests from Eclipse add the system property
 * "runEclipseITs=true"
 * 
 * @author fharms
 *
 */
@RunWith(EclipseIntegrationTestRunner.class)
public class MyIT {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
