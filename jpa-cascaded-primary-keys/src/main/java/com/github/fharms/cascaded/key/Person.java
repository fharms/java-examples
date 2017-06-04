package com.github.fharms.cascaded.key;


import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 */
@Entity
public class Person implements Serializable {

    @EmbeddedId
    private PersonId id;

    private String name;

    @MapsId("id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPersonId(PersonId personId) {
        this.id = personId;
    }
}
