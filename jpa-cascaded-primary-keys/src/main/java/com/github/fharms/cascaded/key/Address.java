package com.github.fharms.cascaded.key;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 */
@Entity
public class Address implements Serializable{

    @Id
    private int adrId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return adrId == address.adrId &&
            Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adrId, street);
    }

    private String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setAdrId(int adrId) {
        this.adrId = adrId;
    }
}
