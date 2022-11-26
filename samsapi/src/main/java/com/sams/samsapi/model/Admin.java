package com.sams.samsapi.model;

public class Admin extends User {

    public Admin(String name, int id) {
        super(name, id, USER_TYPE.ADMIN);
    }

}
