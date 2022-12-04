package com.sams.samsapi.modelTemplates;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    public enum USER_TYPE{
        ADMIN,
        PCC,
        PCM,
        SUBMITTER
    }

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("password")
    private String password;
    @JsonProperty("type")
    private USER_TYPE type;

    public User(@JsonProperty("id") int id, 
                @JsonProperty("name") String name, 
                @JsonProperty("password") String password, 
                @JsonProperty("type") USER_TYPE type) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}
