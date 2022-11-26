package com.sams.samsapi.model;

public class Submitter extends User {

    public Submitter(String name, int id) {
        super(name, id, USER_TYPE.SUBMITTER);
    }

}
