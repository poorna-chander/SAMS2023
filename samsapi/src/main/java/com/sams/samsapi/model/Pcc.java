package com.sams.samsapi.model;

public class Pcc extends User {

    private String type;

    public Pcc(String Name, int Id) {
        super(Name, Id);
        this.type = "pcc";
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
