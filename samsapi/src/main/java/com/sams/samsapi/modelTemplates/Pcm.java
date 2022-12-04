package com.sams.samsapi.modelTemplates;

public class Pcm extends User {

    public Pcm(String name, String password, int id) {
        super(id, name, password, USER_TYPE.PCM);
    }

}
