package com.sams.samsapi.json_crud_utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sams.samsapi.model.User;
import com.sams.samsapi.model.User.USER_TYPE;

@Component
public class UserUtils {
    private static final Logger LOG = Logger.getLogger(UserUtils.class.getName());
    private ObjectMapper objectMapper;
    private String userFileName;
    private User[] userDtls;
    private HashMap<Integer,User> userIdVsUserDtls;
    private int nextUserId = 1;
    
    public UserUtils(@Value("${user.file}") String userFileName, ObjectMapper objectMapper) throws Exception{
        this.objectMapper = objectMapper;
        this.userFileName = userFileName;
        userDtls = objectMapper.readValue(new File(userFileName), User[].class);
        initializeData();
    }

    public void initializeData(){
        userIdVsUserDtls = new HashMap<>();

        for(User user : userDtls){
            userIdVsUserDtls.put(user.getId(), user);
            nextUserId = user.getId() + 1;
        }
    }

    private Integer getNextUserId(){
        return nextUserId++;
    }

    private void saveUser() throws Exception{
        List<User> userList = new ArrayList<>();
        for(Integer userId : userIdVsUserDtls.keySet()){
            userList.add(userIdVsUserDtls.get(userId));
        }
        userDtls = new User[userList.size()];
        userList.toArray(userDtls); 

        objectMapper.writeValue(new File(userFileName), userDtls);
    }

    private Boolean isSaveUserSuccessful(){
        try{
            saveUser();
            return true;
        }catch(Exception ex){
            LOG.log(Level.SEVERE, "Exception is ::: {0}", ex.getMessage());
        }
        return false;
    }

    public Boolean insertUserData(String name, USER_TYPE type){
        Integer userId = getNextUserId();
        if(userId == null || name == null || type == null){
            return false;
        }
        User user = new User(name, userId, type);
        userIdVsUserDtls.put(userId, user);
        return isSaveUserSuccessful();
    }

    public Boolean updateUserData(User user){
        Integer userId = user.getId();
        if(userId == null || user.getName() == null || user.getType() == null || !userIdVsUserDtls.containsKey(userId)){
            return false;
        }
        userIdVsUserDtls.put(userId, user);
        return isSaveUserSuccessful();
    }

    public Boolean deleteUserData(User user){
        Integer userId = user.getId();
        if(userId == null || !userIdVsUserDtls.containsKey(userId)){
            return false;
        }
        userIdVsUserDtls.remove(userId);
        return isSaveUserSuccessful();
    }

    public HashMap<Integer,User> getAllUserDetails(){
        return userIdVsUserDtls;
    }

    public User getUserDetails(Integer userId){
        return userIdVsUserDtls.get(userId);
    }
}
