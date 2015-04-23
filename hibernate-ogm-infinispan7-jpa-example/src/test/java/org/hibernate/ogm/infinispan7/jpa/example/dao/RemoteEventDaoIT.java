/**
 * The MIT License
 * Copyright (c) 2014 Flemming Harms
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
package org.hibernate.ogm.infinispan7.jpa.example.dao;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.hibernate.ogm.infinispan7.jpa.example.model.EventType;
import org.hibernate.ogm.infinispan7.jpa.example.model.EventVO;
import org.hibernate.ogm.infinispan7.jpa.example.model.RemoteEvent;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RemoteEventDaoIT {

    @Inject
    private RemoteEventDao remoteEventDAO;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap
                .create(WebArchive.class)
                .addClass(RemoteEventDao.class)
                .addClass(RemoteEvent.class)
                .addClass(EventVO.class)
                .addClass(EventType.class)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .setManifest(
                        new StringAsset(
                                "Dependencies: org.hibernate:ogm services, org.hibernate.ogm.infinispan services, org.hibernate.search.orm:5.1 services"))
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return webArchive;
    }

    @Test
    public void createRemoveEvent() throws IOException {
        RemoteEvent entity = createTestEvent("My first event", EventType.ADD, UUID.randomUUID().toString());
        remoteEventDAO.create(entity);
        Assert.assertNotNull(entity.getId());
    }

    @Test
    public void createDeleteByClientId() throws IOException {
        String clientId = UUID.randomUUID().toString();
        for (int i = 0; i < 5; i++) {
            RemoteEvent entity = createTestEvent(String.format("My #%s event", i), EventType.ADD, clientId);
            remoteEventDAO.create(entity);
        }
        int deleteByClientId = remoteEventDAO.deleteByClientId(clientId);
        Assert.assertEquals(deleteByClientId, 5);
    }

    @Test
    public void testlistAllByClientId() throws Exception {
        String clientId = UUID.randomUUID().toString();
        for (int i = 0; i < 5; i++) {
            RemoteEvent entity = createTestEvent(String.format("My #%s event", i), EventType.DELETE, clientId);
            remoteEventDAO.create(entity);
        }
        createTestEvent(String.format("My #%s event", 99), EventType.UPDATE, UUID.randomUUID().toString());
        List<RemoteEvent> events = remoteEventDAO.listAllByClientId(clientId);
        Assert.assertEquals(events.size(), 5);
        for (RemoteEvent remoteEvent : events) {
            Assert.assertEquals(EventType.DELETE, remoteEvent.getEvent().getEventType());
            Assert.assertEquals(clientId, remoteEvent.getClientId());
        }
    }

    private RemoteEvent createTestEvent(String eventText, EventType type, String clientId) throws IOException {
        EventVO eventVO = new EventVO();
        eventVO.setEventType(type);
        eventVO.setEventObj(new String(eventText));

        RemoteEvent entity = new RemoteEvent();
        entity.setEvent(eventVO);
        entity.setClientId(clientId);
        return entity;
    }

}
