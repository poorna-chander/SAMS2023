package com.sams.samsapi.crud_utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sams.samsapi.modelTemplates.Notification;
import com.sams.samsapi.modelTemplates.Notification.STATUS;
import com.sams.samsapi.modelTemplates.Notification.TYPE;

@Component
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

        for(Notification notification : notificationDtls){
            idVsNotification.put(notification.getId(), notification);
            nextNotificationId = notification.getId() + 1;
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

    public static Boolean insertNotificationData(Long timeStamp, HashMap<String, Object> data, ArrayList<Integer> visitedIds, TYPE type){
        Integer notificationId = getNextNotificationId();
        if(notificationId == null || timeStamp == null ){
            return false;
        }
        if(data == null){
            data = new HashMap<>();
        }
        if(visitedIds == null){
            visitedIds = new ArrayList<>();
        }
        Notification notification = new Notification(notificationId, timeStamp, data, type, visitedIds, STATUS.UN_NOTICED);
        idVsNotification.put(notificationId, notification);
        return isSaveNotificationSuccessful();
    }

    public static Boolean updateNotificationData(Notification notification){
        Integer notificationId = notification.getId();
        if(notificationId == null || notification.getData() == null || notification.getType() == null || notification.getStatus() == null || notification.getTimeStamp() == null || notification.getVisitedIds() == null || !idVsNotification.containsKey(notificationId)){
            return false;
        }
        idVsNotification.put(notificationId, notification);
        return isSaveNotificationSuccessful();
    }

    public static Boolean deleteNotificationData(Integer notificationId){
        if(notificationId == null || !idVsNotification.containsKey(notificationId)){
            return false;
        }
        idVsNotification.remove(notificationId);
        return isSaveNotificationSuccessful();
    }

    public static HashMap<Integer,Notification> getAllNotifications(){
        return idVsNotification;
    }

    public static HashMap<Integer,Notification> getAllNotificationsBasedOnType(TYPE type){
        HashMap<Integer,Notification> idVsNotificationData = new HashMap<>();
        for(Integer id : idVsNotification.keySet()){
            if(idVsNotification.get(id).getType().equals(type)){
                idVsNotificationData.put(id, idVsNotification.get(id));
            }
        }
        return idVsNotificationData;
    }

    public static HashMap<Integer,Notification> getAllUnNoticedNotificationsBasedOnType(TYPE type){
        HashMap<Integer,Notification> idVsNotificationData = new HashMap<>();
        for(Integer id : idVsNotification.keySet()){
            if(idVsNotification.get(id).getType().equals(type) && idVsNotification.get(id).getStatus().equals(STATUS.UN_NOTICED)){
                idVsNotificationData.put(id, idVsNotification.get(id));
            }
        }
        return idVsNotificationData;
    }

    public static Notification getNotificationDetails(Integer id){
        return idVsNotification.get(id);
    }
    
}
