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
package com.github.fharms.route;

import com.github.fharms.config.CamelConfig;
import com.github.fharms.entity.Joke;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.apache.camel.test.spring.CamelTestContextBootstrapper;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

/**
 * Test how you can use {@link com.github.fharms.camel.entitymanager.CamelEntityManagerHandler} together
 * with {@link javax.persistence.PersistenceContext}.
 * <p>
 *     The test is simple, it read jokes from the internet chuck norris database service http://www.icndb.com/api/
 *     To fetch jokes and persist them for later retrieve and print them out.
 * </p>
 */
@RunWith(CamelSpringRunner.class)
@BootstrapWith(CamelTestContextBootstrapper.class)
@ContextConfiguration(classes = CamelConfig.CamelContextConfiguration.class, loader = CamelSpringDelegatingTestContextLoader.class)
@UseAdviceWith(value = true)
public class JokeRouteTest {

    @EndpointInject(uri = "mock:joke")
    MockEndpoint jokeEndpoint;

    @Produce
    private ProducerTemplate template;

    @Autowired
    ModelCamelContext context;

    @Before
    public void setupAdvisWith() throws Exception {
        context.getRouteDefinition(JokeRoute.GET_JOKES_ROUTE_ID).adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                intercept().when(body().isInstanceOf(Joke.class))
                .to(jokeEndpoint.getEndpointUri());
            }
        });
        context.start();
    }

    @Test
    public void testJokeRetrieveRoute() throws InterruptedException {
        jokeEndpoint.setMinimumExpectedMessageCount(1);
        jokeEndpoint.assertIsSatisfied();

        List<Exchange> exchanges = jokeEndpoint.getReceivedExchanges();
        exchanges
                .stream()
                .map(Exchange::getIn)
                .map(Message::getBody)
                .map(Joke.class::cast)
                .forEach(o -> {
                    Assert.assertNotNull(o.getId());
                    Assert.assertNotNull(o.getJokeText());
                });
    }

    @Test
    public void testJokePrintJokesRoute() throws InterruptedException {
        jokeEndpoint.reset();
        jokeEndpoint.setMinimumExpectedMessageCount(2);
        jokeEndpoint.assertIsSatisfied();

        NotifyBuilder notify = new NotifyBuilder(context)
                .fromRoute(JokeRoute.CONSUME_JOKES_ROUTE_ID)
                .whenCompleted(2).create();
        boolean done = notify.matches(10, TimeUnit.SECONDS);
        assertTrue("Should be done", done);
    }

}