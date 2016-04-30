package org.harms.jpa.orm;

/**
 * Simple sub class entity showing how to setup a mapping
 * with a sub class with the id
 */
public class MySubClass extends MySuperClass {

    private String id;

    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
