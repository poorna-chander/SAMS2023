package com.sams.samsapi.modelTemplates;

public class Admin extends User {

    public Admin(String name, String password, int id) {
        super(id, name, password, USER_TYPE.ADMIN);
    }

}
