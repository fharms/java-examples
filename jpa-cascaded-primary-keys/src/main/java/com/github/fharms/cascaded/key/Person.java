package com.github.fharms.cascaded.key;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.SequenceGenerator;

/**
* reated by fharms on 02/05/2017.
 */
@Entity
@IdClass(PersonId.class)
public class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Id
    private String socialSecurityId;

    private String name;

    @OneToOne(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumns( {
        @JoinColumn(insertable=true, updatable=true)
    })
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

    public String getSocialSecurityId() {
        return socialSecurityId;
    }

    public void setSocialSecurityId(String socialSecurityId) {
        this.socialSecurityId = socialSecurityId;
    }
}
