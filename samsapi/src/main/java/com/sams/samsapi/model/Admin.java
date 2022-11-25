package com.sams.samsapi.model;

public class Admin extends User {

    private String type;

    public Admin(String Name, int Id) {
        super(Name, Id);
        this.type = "admin";
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
