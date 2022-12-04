package com.sams.samsapi.persistence;

import com.sams.samsapi.crud_utils.UserUtils;
import com.sams.samsapi.modelTemplates.User;
import com.sams.samsapi.modelTemplates.User.USER_TYPE;

public class UsersOps implements UsersInterface{

    @Override
    public String getSubmitterName(Integer userId) {
        User user = UserUtils.getUserDetails(userId);
        return (user != null) ? user.getName() : null;
    }

    @Override
    public Boolean authenticateUser(Integer userId, USER_TYPE type){
        return UserUtils.getAllUserDetailsBasedOnType(type).containsKey(userId);
    }

    @Override
    public User authenticateUser(String userName, String password) {
        return UserUtils.getUserDetails(userName, password);
    }

    @Override
    public USER_TYPE getUserType(Integer userId) {
        User user = UserUtils.getUserDetails(userId);
        return (user != null) ? user.getType() : null;
    }

    @Override
    public User registerUser(String userName, String password, USER_TYPE type) {
        if(authenticateUser(userName, password) != null){
            return null;
        }
        UserUtils.insertUserData(userName, password, type);
        User user = UserUtils.getUserDetails(userName, password);
        new NotificationsOps().updateNotificationOnUserRegister(user.getId());
        return user;
    }

}
