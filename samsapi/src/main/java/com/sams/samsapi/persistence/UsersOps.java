package com.sams.samsapi.persistence;

import com.sams.samsapi.json_crud_utils.UserUtils;
import com.sams.samsapi.model.User.USER_TYPE;

public class UsersOps implements UsersInterface{

    @Override
    public String getSubmitterName(Integer userId) {
        return UserUtils.getUserDetails(userId).getName();
    }

    @Override
    public Boolean authenticateUser(Integer userId, USER_TYPE type){
        return UserUtils.getAllUserDetailsBasedOnType(type).containsKey(userId);
    }

}
