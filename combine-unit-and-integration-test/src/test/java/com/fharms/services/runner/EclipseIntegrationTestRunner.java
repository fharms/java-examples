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
package com.fharms.services.runner;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * <pre>
 * This EclipseIntegrationRunner runner prevent execution of a test case 
 * in case the system property "runEclipseITs=true" is not set.
 * </pre>
 * <p>
 * <pre>
 * The purpose of this runner is that you annotated all integration tests
 * with <b>@RunWith(EclipseIntegrationRunner.class)</b> to prevent them for
 * be executed when normal unit tests run in Eclipse.
 *</pre> 
 * @author fharms
 *
 */
public class EclipseIntegrationTestRunner extends BlockJUnit4ClassRunner {

	private static final String IGNORE_MESSAGE = "Ignoring Integration tests, set \"runEclipseITs=true\" for running the tests";
	private final boolean runEclipseIntegrationTests;

	public EclipseIntegrationTestRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
		runEclipseIntegrationTests = Boolean.getBoolean("runEclipseIntegrationTests");
	}

	@Override
	public Description getDescription() {
		if (!runEclipseIntegrationTests) {
			Description description = Description.createSuiteDescription(getTestClass().getJavaClass());
			description.addChild(Description.createTestDescription(getTestClass().getJavaClass(),IGNORE_MESSAGE));
			return description;
		}
		return super.getDescription();
	}
	
	@Override
	public void run(RunNotifier notifier) {
		if (!runEclipseIntegrationTests) {
			Description description = Description.createSuiteDescription(getTestClass().getJavaClass());
			notifier.fireTestIgnored(description);
			return;
		} 
		super.run(notifier);
	}
}
