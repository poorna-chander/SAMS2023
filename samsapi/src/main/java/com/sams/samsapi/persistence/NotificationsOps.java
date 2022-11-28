package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.sams.samsapi.json_crud_utils.NotificationUtil;
import com.sams.samsapi.json_crud_utils.PapersUtil;
import com.sams.samsapi.json_crud_utils.UserUtils;
import com.sams.samsapi.model.Notification;
import com.sams.samsapi.model.Notification.STATUS;
import com.sams.samsapi.model.Notification.TYPE;
import com.sams.samsapi.model.User;
import com.sams.samsapi.model.UserNotification;
import com.sams.samsapi.util.CodeSmellFixer;

public class NotificationsOps implements NotificationsInterface {
    public Boolean insertPaperSubmissionNotification(Integer id, Integer revisionNo, String title, Integer paperId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(CodeSmellFixer.LowerCase.ID, id);
        data.put(CodeSmellFixer.CamelCase.REVISION_NO, revisionNo);
        data.put(CodeSmellFixer.LowerCase.TITLE, title);
        data.put(CodeSmellFixer.CamelCase.PAPER_ID, paperId);

        return NotificationUtil.insertNotificationData(Long.valueOf(System.currentTimeMillis()), data,
                new ArrayList<>(), TYPE.PAPER_SUBMISSION);
    }

    public Boolean insertPcmAssignmentNotification(Integer id, Integer paperId, Integer pcmId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(CodeSmellFixer.LowerCase.ID, id);
        data.put(CodeSmellFixer.CamelCase.PAPER_ID, paperId);
        data.put(CodeSmellFixer.CamelCase.PCM_ID, pcmId);

        return NotificationUtil.insertNotificationData(Long.valueOf(System.currentTimeMillis()), data,
                new ArrayList<>(), TYPE.PCM_ASSIGNMENT);
    }

    public Boolean insertFinalRatingNotification(Integer paperId, Integer submitterId) {
        HashMap<String, Object> data = new HashMap<>();
        ArrayList<Integer> paperIds = new ArrayList<>();
        paperIds.add(paperId);
        data.put(CodeSmellFixer.CamelCase.PAPER_ID, paperIds);

        ArrayList<Integer> submitterIds = new ArrayList<>();
        submitterIds.add(submitterId);

        return NotificationUtil.insertNotificationData(Long.valueOf(System.currentTimeMillis()), data, submitterIds,
                TYPE.SUBMITTED_FINAL_RATING);
    }

    @Override
    public HashMap<TYPE, ArrayList<UserNotification>> getNotificationForUser(Integer userId) {
        User user = UserUtils.getUserDetails(userId);
        switch (user.getType()) {
            case SUBMITTER:
                return getSubmitterNotifications(user);
            case PCC:
                return getPccNotifications(user);
            case PCM:
                return getPcmNotifications(user);
            default:
                return new HashMap<TYPE, ArrayList<UserNotification>>();
        }
    }

    @Override
    public Boolean updateNotificationForUser(Integer userId, ArrayList<Integer> ids) throws Exception {
        User user = UserUtils.getUserDetails(userId);
        for (Integer id : ids) {
            Notification notification = NotificationUtil.getNotificationDetails(id);
            if (notification == null) {
                throw new Exception();
            }
            switch (user.getType()) {
                case SUBMITTER:
                switch(notification.getType()){
                    case SUBMITTED_FINAL_RATING:
                    notification.setVisitedIds(user.getId());
                    notification.setStatus(STATUS.NOTICED);
                    NotificationUtil.updateNotificationData(notification);
                    default:
                    throw new Exception();
                }
                case PCC:
                    switch(notification.getType()){
                        case PAPER_SUBMISSION:
                        
                        case PCC_UNASSIGNED_PAPERS:

                        case PCC_REVIEW_DEADLINE_EXPIRED:

                        case PCC_REVIEWS_PCM_COMPLETED_RATING_PENDING:

                        default:
                        throw new Exception();
                    }
                case PCM:
                    if (!notification.getType().equals(TYPE.PCM_ASSIGNMENT) ||
                            !notification.getType().equals(TYPE.PCM_PAPER_SUBMISSION_DEADLINE_EXPIRED)) {
                        throw new Exception();
                    }
                    break;
                default:

            }
        }
        return null;
    }

    public static HashMap<TYPE, ArrayList<UserNotification>> getSubmitterNotifications(User user) {
        HashMap<TYPE, ArrayList<UserNotification>> returnNotification = new HashMap<>();
        ArrayList<UserNotification> userNotificationList = new ArrayList<>();

        HashMap<Integer, Notification> notificationMap = NotificationUtil
                .getAllNotificationsBasedOnType(TYPE.SUBMITTED_FINAL_RATING);
        for (Integer id : notificationMap.keySet()) {
            Notification notification = notificationMap.get(id);
            ArrayList<Integer> paperIds = new ArrayList<>();

            if (notification.getVisitedIds().get(0).equals(user.getId())) {
                HashMap<String, Object> data = notification.getData();
                if (data.containsKey(CodeSmellFixer.CamelCase.PAPER_IDS)) {
                    Object paperIdsObj = data.get(CodeSmellFixer.SnakeCase.PAPER_IDS);
                    if (paperIdsObj instanceof ArrayList<?>) {
                        ArrayList<?> ids = (ArrayList<?>) paperIdsObj;

                        for (Object paperId : ids) {
                            paperIds.add(Integer.parseInt(paperId.toString()));
                        }
                    }
                }

                HashMap<String, Object> newDataMap = new HashMap<>();
                newDataMap.put(CodeSmellFixer.SnakeCase.PAPER_IDS, paperIds);

                UserNotification userNotification = new UserNotification(notification.getId(),
                        notification.getTimeStamp(), newDataMap, notification.getType(),
                        notification.getStatus().equals(STATUS.UN_NOTICED) ? STATUS.UN_NOTICED
                                : STATUS.NOTICED);
                userNotificationList.add(userNotification);
            }
        }

        returnNotification.put(TYPE.SUBMITTED_FINAL_RATING, userNotificationList);
        return returnNotification;
    }

    @SuppressWarnings("unchecked")
    public static HashMap<TYPE, ArrayList<UserNotification>> getPccNotifications(User user) {
        HashMap<TYPE, ArrayList<UserNotification>> returnNotification = new HashMap<>();
        ArrayList<UserNotification> userNotificationList = new ArrayList<>();

        HashMap<Integer, Notification> notificationMap = NotificationUtil
                .getAllNotificationsBasedOnType(TYPE.PAPER_SUBMISSION);
        for (Integer id : notificationMap.keySet()) {
            Notification notification = notificationMap.get(id);

            UserNotification userNotification = new UserNotification(notification.getId(),
                    notification.getTimeStamp(), notification.getData(), notification.getType(),
                    notification.getStatus().equals(STATUS.UN_NOTICED) ? STATUS.UN_NOTICED
                            : STATUS.NOTICED);
            userNotificationList.add(userNotification);
        }

        returnNotification.put(TYPE.PAPER_SUBMISSION, userNotificationList);

        userNotificationList = new ArrayList<>();

        notificationMap = NotificationUtil
                .getAllNotificationsBasedOnType(TYPE.PCC_UNASSIGNED_PAPERS);
        for (Integer id : notificationMap.keySet()) {
            Notification notification = notificationMap.get(id);

            UserNotification userNotification = new UserNotification(notification.getId(),
                    notification.getTimeStamp(), notification.getData(), notification.getType(),
                    notification.getStatus().equals(STATUS.UN_NOTICED) ? STATUS.UN_NOTICED
                            : STATUS.NOTICED);
            userNotificationList.add(userNotification);
        }

        returnNotification.put(TYPE.PCC_UNASSIGNED_PAPERS, userNotificationList);

        userNotificationList = new ArrayList<>();

        notificationMap = NotificationUtil
                .getAllNotificationsBasedOnType(TYPE.PCC_REVIEW_DEADLINE_EXPIRED);
        for (Integer id : notificationMap.keySet()) {
            Notification notification = notificationMap.get(id);

            UserNotification userNotification = new UserNotification(notification.getId(),
                    notification.getTimeStamp(), notification.getData(), notification.getType(),
                    notification.getStatus().equals(STATUS.UN_NOTICED) ? STATUS.UN_NOTICED
                            : STATUS.NOTICED);
            userNotificationList.add(userNotification);
        }

        returnNotification.put(TYPE.PCC_REVIEW_DEADLINE_EXPIRED, userNotificationList);

        userNotificationList = new ArrayList<>();

        notificationMap = NotificationUtil
                .getAllNotificationsBasedOnType(TYPE.PCC_REVIEWS_PCM_COMPLETED_RATING_PENDING);
        for (Integer id : notificationMap.keySet()) {
            Notification notification = notificationMap.get(id);
            HashMap<String, Object> data = notification.getData();
            UserNotification userNotification = null;
            if (notification.getStatus().equals(STATUS.UN_NOTICED)) {
                ArrayList<Integer> paperIds = (ArrayList<Integer>) data.get(CodeSmellFixer.CamelCase.PAPER_IDS);
                Boolean notCompleted = false;
                for (Integer paperId : paperIds) {
                    if (PapersUtil.getLatestRevisedPaperDetailsBasedOnPaperId(paperId).getRating() == null) {
                        notCompleted = true;
                        break;
                    }
                }
                if (Boolean.TRUE.equals(notCompleted)) {
                    userNotification = new UserNotification(notification.getId(),
                            notification.getTimeStamp(), notification.getData(), notification.getType(),
                            STATUS.UN_NOTICED);

                } else {
                    userNotification = new UserNotification(notification.getId(),
                            notification.getTimeStamp(), notification.getData(), notification.getType(),
                            STATUS.NOTICED);
                }
            }

            userNotificationList.add(userNotification);
        }

        returnNotification.put(TYPE.PCC_REVIEWS_PCM_COMPLETED_RATING_PENDING, userNotificationList);

        return returnNotification;
    }

    public static HashMap<TYPE, ArrayList<UserNotification>> getPcmNotifications(User user) {
        HashMap<TYPE, ArrayList<UserNotification>> returnNotification = new HashMap<>();
        ArrayList<UserNotification> userNotificationList = new ArrayList<>();

        HashMap<Integer, Notification> notificationMap = NotificationUtil
                .getAllNotificationsBasedOnType(TYPE.PCM_ASSIGNMENT);
        for (Integer id : notificationMap.keySet()) {
            Notification notification = notificationMap.get(id);
            HashMap<String, Object> data = notification.getData();
            if (data.containsKey(CodeSmellFixer.CamelCase.PCM_ID)) {
                Integer pcmId = Integer.valueOf(data.get(CodeSmellFixer.CamelCase.PCM_ID).toString());
                if (pcmId == user.getId()) {
                    UserNotification userNotification = new UserNotification(notification.getId(),
                            notification.getTimeStamp(), notification.getData(), notification.getType(),
                            notification.getStatus().equals(STATUS.UN_NOTICED) ? STATUS.UN_NOTICED
                                    : STATUS.NOTICED);
                    userNotificationList.add(userNotification);
                }
            }
        }

        returnNotification.put(TYPE.PCM_ASSIGNMENT, userNotificationList);

        userNotificationList = new ArrayList<>();

        notificationMap = NotificationUtil
                .getAllNotificationsBasedOnType(TYPE.PCM_PAPER_SUBMISSION_DEADLINE_EXPIRED);
        for (Integer id : notificationMap.keySet()) {
            Notification notification = notificationMap.get(id);

            UserNotification userNotification = new UserNotification(notification.getId(),
                    notification.getTimeStamp(), notification.getData(), notification.getType(),
                    notification.getStatus().equals(STATUS.UN_NOTICED) ? STATUS.UN_NOTICED
                            : STATUS.NOTICED);
            userNotificationList.add(userNotification);
        }

        returnNotification.put(TYPE.PCM_PAPER_SUBMISSION_DEADLINE_EXPIRED, userNotificationList);

        return returnNotification;
    }
}
