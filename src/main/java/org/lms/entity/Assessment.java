package org.lms.entity;

public class Assessment {
    String ID;
    String type;
    public Assessment(String ID, String type) {
        this.ID = ID;
        this.type = type;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

}
