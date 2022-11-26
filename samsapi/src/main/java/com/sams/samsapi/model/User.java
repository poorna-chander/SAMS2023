package com.sams.samsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    public enum USER_TYPE{
        ADMIN,
        PCC,
        PCM,
        SUBMITTER
    }
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private int id;
    @JsonProperty("type")
    private USER_TYPE type;

    public User(@JsonProperty("name") String name, 
                @JsonProperty("id") int id, 
                @JsonProperty("type") USER_TYPE type) {
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
