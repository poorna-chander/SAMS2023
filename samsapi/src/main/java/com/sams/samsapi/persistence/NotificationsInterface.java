package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.sams.samsapi.modelTemplates.UserNotification;
import com.sams.samsapi.modelTemplates.Notification.TYPE;

public interface NotificationsInterface {
    ArrayList<UserNotification> getNotificationForUser(Integer userId);
    Boolean updateNotificationForUser(Integer userId, ArrayList<Integer> ids) throws Exception;
}
