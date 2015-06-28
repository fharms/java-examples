/**
 * The MIT License
 * Copyright (c) ${project.inceptionYear} Flemming Harms
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
package com.fharms.marshalling.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

/**
 * POJO for Address
 * 
 * @author Flemming Harms (flemming.harms at gmail.com)
 *
 */
@Entity
@Proxy
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Long zipcode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String state;

    protected Address() {};
    
    public Address(String street, Long zipcode, String city, String state, String country) {
        this.uuid = UUID.randomUUID();
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public Long getZipcode() {
        return zipcode;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (street != null && !street.trim().isEmpty())
            result += "street: " + street;
        if (city != null && !city.trim().isEmpty())
            result += ", city: " + city;
        if (zipcode != null)
            result += ", zipcode: " + zipcode;
        if (country != null && !country.trim().isEmpty())
            result += ", country: " + country;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Address)) {
            return false;
        }
        Address other = (Address) obj;
        if (!uuid.equals(other.uuid)) {
            return false;
        }
        if (!street.equals(other.street)) {
            return false;
        }
        if (!city.equals(other.city)) {
            return false;
        }
        if (!zipcode.equals(other.zipcode)) {
            return false;
        }
        if (!country.equals(other.country)) {
            return false;
        }
        if (!state.equals(other.state)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }
}