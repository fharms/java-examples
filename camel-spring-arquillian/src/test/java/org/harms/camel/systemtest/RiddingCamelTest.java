/**
 * The MIT License
 * Copyright (c) ${project.inceptionYear} Umbrew
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
package org.harms.camel.systemtest;

import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.spring.integration.test.annotation.SpringConfiguration;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * System test for testing against deployed and running camel route.
 *
 */
@RunWith(Arquillian.class)
@SpringConfiguration(value = {"META-INF/spring/camel-context.xml"})
public class RiddingCamelTest {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Autowired
    ModelCamelContext context;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class).addClass(RiddingCamel.class)
                .addAsLibraries(Maven.resolver().resolve("org.apache.camel:camel-spring:2.16.1"
                        , "org.apache.camel:camel-spring-javaconfig:2.16.1"
                        , "org.apache.camel:camel-core:2.16.1"
                        , "org.springframework:spring-web:4.1.8.RELEASE"
                        , "org.springframework:spring-core:4.1.8.RELEASE").withTransitivity().asFile())
                .addAsManifestResource("META-INF/spring/camel-context.xml", "spring/camel-context.xml");
    }

    @Before
    public void addAdviceWith() throws Exception {
        context.getRouteDefinition("ridding_the_camel").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("file://hello.camel.txt")
                        .skipSendToOriginalEndpoint()
                        .to("mock:result");

            }
        });

    }

    @Test
    public void testRiddingTheCamel() throws Exception {
        String expectedBody = "ridding the camel!";

        resultEndpoint.expectedBodiesReceived(expectedBody);
        resultEndpoint.setAssertPeriod(5000);

        resultEndpoint.assertIsSatisfied();
    }
}
