package org.harms.jpa.orm;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Simpe test DAO to test if the <code>{@link MyTestEntity}</code> can be persisted.
 */
@Stateless
public class MyTestDAO {

    @PersistenceContext
    EntityManager em;

    public void save(Object entity) {
        em.persist(entity);
    }


    public <T> T find(String id, Class<T> clazz) {
        return em.find(clazz,id);
    }

}
