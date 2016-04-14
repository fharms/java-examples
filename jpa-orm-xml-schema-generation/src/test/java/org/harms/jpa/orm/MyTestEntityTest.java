/**
 * The MIT License
 * Copyright (c) 2016 Flemming Harms
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.harms.jpa.orm;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

/**
 * Test case for testing the orm.xml mapping and creation of the database schema
 */
@RunWith(Arquillian.class)
public class MyTestEntityTest {

    @EJB
    private MyTestDAO dao;


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap
                .create(JavaArchive.class)
                .addClass(MyTestEntity.class)
                .addClass(MyTestDAO.class)
                .addAsManifestResource("META-INF/persistence.xml",
                        "persistence.xml")
                .addAsManifestResource("META-INF/orm.xml",
                        "orm.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testSave() {
        Assert.assertNotNull(dao);
        MyTestEntity entity = new MyTestEntity();
        entity.setId("00001");
        entity.setFreeText("Life is like a game of cards. The hand you are dealt is determinism;" +
                " the way you play it is free will.");
        dao.save(entity);
        MyTestEntity newEntity = dao.find("00001");
        Assert.assertEquals(entity.getFreeText(),newEntity.getFreeText());
        Assert.assertEquals(entity.getId(),newEntity.getId());

    }
}
