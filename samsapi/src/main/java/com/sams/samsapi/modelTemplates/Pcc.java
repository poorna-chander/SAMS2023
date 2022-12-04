package com.sams.samsapi.modelTemplates;

public class Pcc extends User {
    
    public Pcc(String name, String password, int id) {
        super(id, name, password, USER_TYPE.PCC);
    }

}
