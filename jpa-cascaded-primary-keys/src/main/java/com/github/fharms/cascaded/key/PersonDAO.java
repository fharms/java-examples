package com.github.fharms.cascaded.key;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 */
public class PersonDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void savePerson(Person person){
        em.persist(person);
    }

}
