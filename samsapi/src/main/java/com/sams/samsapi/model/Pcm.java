package com.sams.samsapi.model;

public class Pcm extends User {

    private String type;

    public Pcm(String Name, int Id) {
        super(Name, Id);
        this.type = "pcm";
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
