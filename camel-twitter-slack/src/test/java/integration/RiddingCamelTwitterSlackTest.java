/**
 * The MIT License
 * Copyright (c) 2016 Umbrew
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
    package integration;

import org.apache.camel.cdi.ContextName;
import org.apache.camel.cdi.Uri;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.harms.camel.integration.RiddingCamelTwitterSlackBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.extension.camel.CamelAware;

import javax.inject.Inject;

/**
 * System test for testing against deployed and running camel route.
 */
@CamelAware
@RunWith(Arquillian.class)
public class RiddingCamelTwitterSlackTest {

    @Inject
    @Uri("mock:result")
    protected MockEndpoint resultEndpoint;

    @Inject
    @ContextName("camel-twitter-slack")
    ModelCamelContext context;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .addPackage(RiddingCamelTwitterSlackBuilder.class.getPackage().getName())
                .addAsManifestResource("camel-twitter-slack.properties")
                .addAsLibraries(Maven.resolver().resolve("org.apache.camel:camel-gson:2.17.0",
                        "org.apache.camel:camel-twitter:2.17.0",
                        "org.apache.camel:camel-xstream:2.17.0",
                        "org.apache.camel:camel-slack:2.17.0",
                        "org.twitter4j:twitter4j-core:4.0.4").withTransitivity().asFile())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    @Test
    public void testRiddingTheCamel() throws Exception {
       /* Uncomment this when the right keys are inserted into the camel-twitter-slack.properties

       context.getRouteDefinition("twitter_search").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint(RiddingCamelTwitterSlackBuilder.slackUri)
                        .to(resultEndpoint.getEndpointUri());

            }
        });

        resultEndpoint.setMinimumExpectedMessageCount(1);
        resultEndpoint.assertIsSatisfied(10000);*/
    }
}
