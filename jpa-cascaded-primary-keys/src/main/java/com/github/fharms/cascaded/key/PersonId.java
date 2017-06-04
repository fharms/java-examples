package com.github.fharms.cascaded.key;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 */
@Embeddable
public class PersonId implements Serializable {

    private String id;
    private String socialSecurityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
            this.id = id;
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
        return id == personId1.id &&
            Objects.equals(socialSecurityId, personId1.socialSecurityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, socialSecurityId);
    }
}
