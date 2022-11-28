package com.sams.samsapi.json_crud_utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sams.samsapi.model.Notification;
import com.sams.samsapi.model.Notification.STATUS;
import com.sams.samsapi.model.Notification.TYPE;

public class NotificationUtil {
    
    private static final Logger LOG = Logger.getLogger(NotificationUtil.class.getName());
    private static ObjectMapper objectMapper;
    private static String notificationFileName;
    private static Notification[] notificationDtls;
    private static HashMap<Integer,Notification> idVsNotification;
    private static int nextNotificationId = 1;
    
    public NotificationUtil(@Value("${notification.file}") String notificationFileName, ObjectMapper objectMapper) throws Exception{
        initialize(notificationFileName, objectMapper);
    }

    private static void initialize( String localNotificationFileName, ObjectMapper localObjectMapper) throws Exception{
        notificationFileName = localNotificationFileName;
        objectMapper = localObjectMapper;
        notificationDtls = objectMapper.readValue(new File(notificationFileName), Notification[].class);
        initializeData();
    }

    private static void initializeData(){
        idVsNotification = new HashMap<>();

        for(Integer id : idVsNotification.keySet()){
            idVsNotification.put(id, idVsNotification.get(id));
            nextNotificationId = id + 1;
        }
    }

    private static Integer getNextNotificationId(){
        return nextNotificationId++;
    }

    private static void saveNotification() throws Exception{
        List<Notification> notificationList = new ArrayList<>();
        for(Integer id : idVsNotification.keySet()){
            notificationList.add(idVsNotification.get(id));
        }
        notificationDtls = new Notification[notificationList.size()];
        notificationList.toArray(notificationDtls); 

        objectMapper.writeValue(new File(notificationFileName), notificationDtls);
    }

    private static Boolean isSaveNotificationSuccessful(){
        try{
            saveNotification();
            return true;
        }catch(Exception ex){
            LOG.log(Level.SEVERE, "Exception is ::: {0}", ex.getMessage());
        }
        return false;
    }

    public static Boolean insertNotificationData(Long timeStamp, ArrayList<HashMap<String,Object>> data, ArrayList<Integer> visitedIds, TYPE type){
        Integer notificationId = getNextNotificationId();
        if(notificationId == null || timeStamp == null ){
            return false;
        }
        if(data == null){
            data = new ArrayList<>();
        }
        if(visitedIds == null){
            visitedIds = new ArrayList<>();
        }
        Notification notification = new Notification(notificationId, timeStamp, data, type, visitedIds, STATUS.UN_NOTICED);
        idVsNotification.put(notificationId, notification);
        return isSaveNotificationSuccessful();
    }

    public static Boolean updateUserData(Notification notification){
        Integer notificationId = notification.getId();
        if(notificationId == null || notification.getData() == null || notification.getType() == null || notification.getStatus() == null || notification.getTimeStamp() == null || notification.getVisitedIds() == null || !idVsNotification.containsKey(notificationId)){
            return false;
        }
        idVsNotification.put(notificationId, notification);
        return isSaveNotificationSuccessful();
    }

    public static HashMap<Integer,Notification> getAllNotifications(){
        return idVsNotification;
    }

    public static HashMap<Integer,Notification> getAllNotificationsBasedOnType(TYPE type){
        HashMap<Integer,Notification> idVsNotification = new HashMap<>();
        for(Integer id : idVsNotification.keySet()){
            if(idVsNotification.get(id).getType().equals(type)){
                idVsNotification.put(id, idVsNotification.get(id));
            }
        }
        return idVsNotification;
    }

    public static Notification getNotificationDetails(Integer id){
        return idVsNotification.get(id);
    }
    
}
