/**
 * The MIT License
 * Copyright (c) 2016 Flemming Harms
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
package org.harms.camel.integration;

import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.component.slack.SlackComponent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.IOException;
import java.util.Properties;

/**
 * Boot class responsible for register properties and slack component bean
 * at start up
 */
public class CamelTwitterSlackBoot {

    @Produces
    @ApplicationScoped
    @Named("properties")
    PropertiesComponent propertiesComponent() {
        PropertiesComponent component = new PropertiesComponent();
        component.setLocation("classpath:META-INF/camel-twitter-slack.properties");
        return component;
    }

    @Produces
    @ApplicationScoped
    @Named("slack")
    SlackComponent slackComponent() throws IOException {
        Properties properties = new Properties();
        properties.load(CamelTwitterSlackBoot.class.getResourceAsStream("/META-INF/camel-twitter-slack.properties"));
        String slackWebHook = properties.getProperty("slackWebHook");
        SlackComponent slackComponent = new SlackComponent();
        slackComponent.setWebhookUrl(slackWebHook);

        return slackComponent;
    }
}