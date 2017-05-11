package com.github.fharms.cascaded.key;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 */
@Embeddable
public class PersonId implements Serializable {

    private int personId;
    private String socialSecurityId;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getSocialSecurityId() {
        return socialSecurityId;
    }

    public void setSocialSecurityId(String socialSecurityId) {
        this.socialSecurityId = socialSecurityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonId personId1 = (PersonId) o;
        return personId == personId1.personId &&
            Objects.equals(socialSecurityId, personId1.socialSecurityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, socialSecurityId);
    }
}
