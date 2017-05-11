package com.github.fharms.cascaded.key;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.spi.api.JARArchive;

import javax.inject.Inject;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by fharms on 02/05/2017.
 */
@RunWith(Arquillian.class)
public class PersonTest {

    @Inject
    PersonDAO dao;

    @Deployment
    public static Archive createDeployment() {
        JARArchive deployment = ShrinkWrap.create( JARArchive.class );
        deployment.addPackage(Person.class.getPackage().getName())
            .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return deployment;
    }

    @CreateSwarm
    public static Swarm newContainer() throws Exception {
        Swarm swarm = new Swarm();
        swarm.fraction(new DatasourcesFraction()
            .dataSource("CascadeKeyDSCascadeKeyDS", (ds) -> {
                ds.driverName("h2");
                ds.connectionUrl("jdbc:h2:file://Users/fharms/wildflyswarm;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
                ds.userName("sa");
                ds.password("sa");
            })
        );
        return swarm;
    }

    @Test
    public void testPersistCascadedKeys() {
        PersonId personId = new PersonId();
        personId.setPersonId(1);
        personId.setSocialSecurityId(UUID.randomUUID().toString());

        Person person = new Person();
        person.setPersonId(personId);
        person.setName("Flemming Harms");

        Address address = new Address();
        address.setStreet("Baker street");
        person.setAddress(address);

        dao.savePerson(person);

        personId = new PersonId();
        personId.setPersonId(2);
        personId.setSocialSecurityId(UUID.randomUUID().toString());

        person = new Person();
        person.setName("Peter Pan");
        person.setPersonId(personId);
        address = new Address();
        address.setStreet("Pan Street");
        person.setAddress(address);

        dao.savePerson(person);

        assertTrue(true);
    }
}