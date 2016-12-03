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

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Map;

@ContextName(value = "camel-twitter-slack")
public class RiddingCamelTwitterSlackBuilder extends RouteBuilder {

    static public String slackUri = "slack:#twitter";
    static public String TWITTER_URI = "twitter://timeline/home?type=polling&delay=10&keywords=getsmarterday&consumerKey={{consumerKey}}&consumerSecret={{consumerSecret}}" +
            "&accessToken={{accessToken}}&accessTokenSecret={{accessTokenSecret}}";

    public void configure() throws Exception {
        from(TWITTER_URI)
                .routeId("twitter_search")
                .routeDescription("Watching twitter for hash tags")
                .log(LoggingLevel.INFO, "Ridding the camel")
                .marshal().json(JsonLibrary.Gson)
                .unmarshal().json(JsonLibrary.Gson, Map.class)
                .bean(MyBeanClass.class,"processTwitter")
                .to(slackUri);
    }
}