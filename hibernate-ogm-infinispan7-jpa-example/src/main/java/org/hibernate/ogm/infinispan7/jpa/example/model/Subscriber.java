package org.hibernate.ogm.infinispan7.jpa.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class Subscriber implements Serializable {

    private static final long serialVersionUID = -5552944209589173345L;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private String id;

    public Subscriber() {}
  
    public Subscriber(String clientId) {
      this.id = clientId;
    }
    
    
    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Subscriber)) {
            return false;
        }
        Subscriber other = (Subscriber) obj;
        if (id != null) {
            if (!id.equals(other.id)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}