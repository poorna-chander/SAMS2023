package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.sams.samsapi.model.UserNotification;
import com.sams.samsapi.model.Notification.TYPE;

public interface NotificationsInterface {
    HashMap<TYPE, ArrayList<UserNotification>> getNotificationForUser(Integer userId);
    Boolean updateNotificationForUser(Integer userId, ArrayList<Integer> ids) throws Exception;
}
