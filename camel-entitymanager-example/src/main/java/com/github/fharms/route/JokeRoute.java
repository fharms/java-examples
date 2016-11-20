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

import com.github.fharms.entity.Joke;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

/**
 * This route is responsible for make you smile every 60 seconds :-)
 */
@Component
public class JokeRoute extends RouteBuilder {

    public static final String CONSUME_JOKES_ROUTE_ID = "consumeJokes";
    public static final String GET_JOKES_ROUTE_ID = "getJokes";

    @Override
    public void configure() throws Exception {
        from("timer:getJoke?period=5s")
                .routeId(GET_JOKES_ROUTE_ID)
                .to("http4:api.icndb.com:80/jokes/random")
                .unmarshal().json(JsonLibrary.Gson)
                .bean(JokeDAO.class,"persistFunnyJoke")
                .log("Response : ${body}");

        fromF("jpa:%s?consumer.query=select j FROM %s j where j.count < 10 ORDER BY j.count DESC&consumeDelete=false",Joke.class.getName(),Joke.class.getName())
                .routeId(CONSUME_JOKES_ROUTE_ID)
                .transacted()
                .bean(JokeDAO.class,"increaseDisplayCount")
                .log("${body}");
    }
}
