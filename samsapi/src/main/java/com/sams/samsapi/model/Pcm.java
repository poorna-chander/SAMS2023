package com.sams.samsapi.model;

public class Pcm extends User {

    public Pcm(String name, String password, int id) {
        super(id, name, password, USER_TYPE.PCM);
    }

}
