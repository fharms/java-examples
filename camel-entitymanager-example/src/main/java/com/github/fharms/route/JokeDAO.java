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

import com.github.fharms.camel.entitymanager.CamelEntityManagerHandler;
import com.github.fharms.entity.Joke;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Make sure we forget nothing and our world is filled with fun and joy
 */
@Component
@Transactional
public class JokeDAO {

    @PersistenceContext
    EntityManager em;

    @SuppressWarnings("unused")
    public Joke persistFunnyJoke(@Body Joke joke){
        em.persist(joke);
        return joke;
    }

    @SuppressWarnings("unused")
    public void increaseDisplayCount(Exchange exchange){
        //let's just test for fun that the Camel EntityManager is the same as bean entity manager
        EntityManager camelEntityManager = exchange.getIn().getHeader(CamelEntityManagerHandler.CAMEL_ENTITY_MANAGER, EntityManager.class);
        Assert.state(em.equals(camelEntityManager));
        Joke joke = exchange.getIn().getBody(Joke.class);
        joke.setCount(joke.getCount()+1);
        exchange.getIn().setBody(joke);
    }

}
