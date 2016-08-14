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
package org.harms.jpa.entitymanager;

import org.harms.hibernate5.example.Jpa21TestDao;
import org.harms.hibernate5.example.Jpa21TestEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.Random;

/**
 * System test for testing against JPA 2.1 (Hibernate 5.x.x) With Wildfly 10.
 *
 */
@RunWith(Arquillian.class)
public class Jpa21Wildfly10Hibernate5IT {

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "jpa2wildfly.war")
                .addClass(Jpa21TestDao.class)
                .addClass(Jpa21TestEntity.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/persistence.xml");
        System.out.println("Jar :"+webArchive.toString(true));
        return webArchive;
    }

    @EJB
    Jpa21TestDao dao;

    @Test
    public void testWithHibernate5() throws Exception {
        Random rand = new Random();

        Jpa21TestEntity entity = new Jpa21TestEntity();
        entity.setId((long) (rand.nextInt(50) + 1));
        Jpa21TestEntity saveEntity = dao.save(entity);
        Assert.assertEquals(entity.getId(),saveEntity.getId());
    }
    @Test
    public void testHibernateVersion() {
        Assert.assertEquals(System.getProperty("hibernateVersion").toUpperCase(),dao.getHibernateVersion().toUpperCase());
    }
}
