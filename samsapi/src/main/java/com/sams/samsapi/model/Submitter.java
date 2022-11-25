package com.sams.samsapi.model;

public class Submitter extends User {

    private String type;

    public Submitter(String Name, int Id) {
        super(Name, Id);
        this.type = "submitter";
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
