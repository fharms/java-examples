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
package com.github.fharms.converter;

import com.github.fharms.entity.Joke;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;

import java.util.Map;

/**
 * Convert Json data map to {@link Joke}
 */
@Converter
public class JokeTypeConverter implements TypeConverters {

    @Converter
    public Joke toJoke(Map jsonMap) {
        LinkedTreeMap values = (LinkedTreeMap) jsonMap.get("value");
        Joke joke = new Joke();
        joke.setId(Double.toString((Double) values.get("id")));
        joke.setJokeText((String) values.get("joke"));
        return joke;
    }

}
