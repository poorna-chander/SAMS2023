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
    private static ObjectMapper objectMapper;
    private static String userFileName;
    private static User[] userDtls;
    private static HashMap<Integer,User> userIdVsUserDtls;
    private static int nextUserId = 1;
    
    public UserUtils(@Value("${user.file}") String userFileName, ObjectMapper objectMapper) throws Exception{
        initialize(userFileName, objectMapper);
    }

    private static void initialize( String localUserFileName, ObjectMapper localObjectMapper) throws Exception{
        userFileName = localUserFileName;
        objectMapper = localObjectMapper;
        userDtls = objectMapper.readValue(new File(userFileName), User[].class);
        initializeData();
    }

    private static void initializeData(){
        userIdVsUserDtls = new HashMap<>();

        for(User user : userDtls){
            userIdVsUserDtls.put(user.getId(), user);
            nextUserId = user.getId() + 1;
        }
    }

    private static Integer getNextUserId(){
        return nextUserId++;
    }

    private static void saveUser() throws Exception{
        List<User> userList = new ArrayList<>();
        for(Integer userId : userIdVsUserDtls.keySet()){
            userList.add(userIdVsUserDtls.get(userId));
        }
        userDtls = new User[userList.size()];
        userList.toArray(userDtls); 

        objectMapper.writeValue(new File(userFileName), userDtls);
    }

    private static Boolean isSaveUserSuccessful(){
        try{
            saveUser();
            return true;
        }catch(Exception ex){
            LOG.log(Level.SEVERE, "Exception is ::: {0}", ex.getMessage());
        }
        return false;
    }

    public static Boolean insertUserData(String name, String password, USER_TYPE type){
        Integer userId = getNextUserId();
        if(userId == null || name == null || type == null){
            return false;
        }
        User user = new User(userId, name, password , type);
        userIdVsUserDtls.put(userId, user);
        return isSaveUserSuccessful();
    }

    public static Boolean updateUserData(User user){
        Integer userId = user.getId();
        if(userId == null || user.getName() == null || user.getType() == null || !userIdVsUserDtls.containsKey(userId)){
            return false;
        }
        userIdVsUserDtls.put(userId, user);
        return isSaveUserSuccessful();
    }

    public static Boolean deleteUserData(User user){
        Integer userId = user.getId();
        if(userId == null || !userIdVsUserDtls.containsKey(userId)){
            return false;
        }
        userIdVsUserDtls.remove(userId);
        return isSaveUserSuccessful();
    }

    public static HashMap<Integer,User> getAllUserDetails(){
        return userIdVsUserDtls;
    }

    public static HashMap<Integer,User> getAllUserDetailsBasedOnType(USER_TYPE type){
        HashMap<Integer,User> userIdVsUserDtlsOfType = new HashMap<>();
        for(Integer userId : userIdVsUserDtls.keySet()){
            if(userIdVsUserDtls.get(userId).getType().equals(type)){
                userIdVsUserDtlsOfType.put(userId, userIdVsUserDtls.get(userId));
            }
        }
        return userIdVsUserDtlsOfType;
    }

    public static User getUserDetails(Integer userId){
        return userIdVsUserDtls.get(userId);
    }

    public static User getUserDetails(String userName, String password){
        for(Integer userId : userIdVsUserDtls.keySet()){
            User user = userIdVsUserDtls.get(userId);
            if(user.getName().equals(userName) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
    
}
