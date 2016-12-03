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
package com.github.fharms.mapping.model;

import com.github.fharms.mapping.JpaCommand;
import com.github.fharms.mapping.service.JpaCommandService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class PersonTest {

    @Inject
    JpaCommandService ps;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .addPackage(Person.class.getPackage().getName())
                .addPackage(JpaCommandService.class.getPackage().getName())
                .addPackage(JpaCommand.class.getPackage().getName())
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("log4j.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    @Test
    public void testPersistPerson() {


        //Assert.assertEquals(person.getContacts(),person2.getContacts());
        ps.runJpaCommand(new JpaCommand() {
            @Override
            public void run(EntityManager em) {
                ContactType emailContactType = new ContactType();
                emailContactType.setType("EMAIL");

                ContactType twitterContactType = new ContactType();
                twitterContactType.setType("TWITTER");

                List<ContactType> contactTypes = Arrays.asList(emailContactType, twitterContactType);

                Person.ContactInformation contactInformation = new Person.ContactInformation();
                contactInformation.setContactType(contactTypes);

                Person person = new Person();
                person.setContacts(Arrays.asList(contactInformation));
                em.persist(person);


                Person person2 = em.find(Person.class,person.getId());
                assertEquals(person,person2);
                assertTrue(person2.getContacts().size() > 0);
                assertEquals(person.getContacts(),person2.getContacts());

            }
        });

    }


}