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

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.ogm.infinispan7.jpa.example.model.RemoteEvent;

/**
 * DAO for RemoteEvent
 */
@Stateless
public class RemoteEventDao {
    @PersistenceContext(unitName = "RemoteEventQueue")
    private EntityManager em;

    public void create(RemoteEvent entity) {
        em.persist(entity);
    }

    public int deleteByClientId(String clientId) {
        List<RemoteEvent> remoteEvents = listAllByClientId(clientId);
        for (RemoteEvent remoteEvent : remoteEvents) {
            em.remove(remoteEvent);
        }
        return remoteEvents.size();
    }

    public List<RemoteEvent> listAllByClientId(String clientId) {
        Query query = em.createQuery("FROM RemoteEvent r where r.clientId = :clientId");
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }
}
