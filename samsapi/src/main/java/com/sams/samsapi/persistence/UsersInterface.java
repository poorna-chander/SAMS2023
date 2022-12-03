package com.sams.samsapi.persistence;

import com.sams.samsapi.modelTemplates.User;
import com.sams.samsapi.modelTemplates.User.USER_TYPE;

public interface UsersInterface {
    String getSubmitterName(Integer userId);
    Boolean authenticateUser(Integer userId, USER_TYPE type);
    User authenticateUser(String userName, String password);
    User registerUser(String userName, String password, USER_TYPE type);
    USER_TYPE getUserType(Integer userId);
}
