package com.sams.samsapi.persistence;

import com.sams.samsapi.model.User.USER_TYPE;

public interface UsersInterface {
    String getSubmitterName(Integer userId);
    Boolean authenticateUser(Integer userId, USER_TYPE type);
}
