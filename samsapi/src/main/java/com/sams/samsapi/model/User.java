package com.sams.samsapi.model;

public class User {
    public enum USER_TYPE{
        ADMIN,
        PCC,
        PCM,
        SUBMITTER
    }
    private String name;
    private int id;
    private USER_TYPE type;

    public User(String name, int id, USER_TYPE type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public USER_TYPE getType() {
        return type;
    }
}
