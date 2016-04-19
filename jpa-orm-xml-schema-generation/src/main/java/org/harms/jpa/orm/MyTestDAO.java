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

    public void save(MyTestEntity entity) {
        em.persist(entity);
    }

    public MyTestEntity find(String id) {
        return em.find(MyTestEntity.class,id);
    }

}
