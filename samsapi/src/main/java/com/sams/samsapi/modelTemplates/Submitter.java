package com.sams.samsapi.modelTemplates;

public class Submitter extends User {

    public Submitter(String name, String password, int id) {
        super(id, name, password, USER_TYPE.SUBMITTER);
    }

}
