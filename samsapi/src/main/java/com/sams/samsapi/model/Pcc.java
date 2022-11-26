package com.sams.samsapi.model;

public class Pcc extends User {
    
    public Pcc(String name, int id) {
        super(name, id, USER_TYPE.PCC);
    }

}
