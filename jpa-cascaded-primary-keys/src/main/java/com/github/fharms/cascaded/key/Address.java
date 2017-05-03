package com.github.fharms.cascaded.key;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by fharms on 02/05/2017.
 */
@Entity
public class Address implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return id == address.id &&
            Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street);
    }

    private String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setId(long id) {
        this.id = id;
    }
}
