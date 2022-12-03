package com.sams.samsapi.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.scheduling.annotation.Scheduled;

import com.sams.samsapi.json_crud_utils.DeadlineUtil;
import com.sams.samsapi.json_crud_utils.NotificationUtil;
import com.sams.samsapi.model.Deadline;
import com.sams.samsapi.model.Notification;
import com.sams.samsapi.model.Notification.TYPE;
import com.sams.samsapi.model.ResearchPaper;
import com.sams.samsapi.persistence.PccOps;
import com.sams.samsapi.persistence.PcmOps;
import com.sams.samsapi.util.CodeSmellFixer;

public class ScheduleJob {

    private static final Logger LOG = Logger.getLogger(ScheduleJob.class.getName());
    @Scheduled(fixedRateString = "@daily")
    public void jobRunner(){
        // unassignedPccPapersNotification();
        // reviewDeadlineExpiredNotification();
        // paperDeadlineExpiredNotification();
        // pccPendingReviewsNotification();
    }

    public static void unassignedPccPapersNotification(){
        PccOps pccOps = new PccOps();
        ArrayList<ResearchPaper> papers = pccOps.getPaperPendingPCCAssignments();
        HashMap<Integer,Notification> notificationDtls = NotificationUtil.getAllUnNoticedNotificationsBasedOnType(TYPE.PCC_UNASSIGNED_PAPERS);
        Boolean isCreate = notificationDtls.isEmpty();
        Boolean isSuccess = true;
        if(Boolean.FALSE.equals(isCreate)){
            isSuccess = NotificationUtil.deleteNotificationData(notificationDtls.get(notificationDtls.keySet().iterator().next()).getId());
        }
        for(ResearchPaper paper : papers){
            HashMap<String,Object> data = new HashMap<>();
            data.put(CodeSmellFixer.LowerCase.ID, paper.getId());
            data.put(CodeSmellFixer.CamelCase.PAPER_ID, paper.getPaperId());
            data.put(CodeSmellFixer.LowerCase.TITLE, paper.getTitle());
            if(Boolean.TRUE.equals(isSuccess)){
                NotificationUtil.insertNotificationData(System.currentTimeMillis(), data, new ArrayList<>(), TYPE.PCC_UNASSIGNED_PAPERS);
            }
        }
        
        LOG.log(Level.INFO, CodeSmellFixer.LoggerCase.SUCCESS, isSuccess);
    }

    public static void reviewDeadlineExpiredNotification(){
        HashMap<Integer,Notification> notificationDtls = NotificationUtil.getAllUnNoticedNotificationsBasedOnType(TYPE.PCC_REVIEW_DEADLINE_EXPIRED);
        Boolean isNotificationExists = !notificationDtls.isEmpty();
        Boolean isSuccess = true;
        if(Boolean.TRUE.equals(isNotificationExists)){
            isSuccess =  NotificationUtil.deleteNotificationData(notificationDtls.get(notificationDtls.keySet().iterator().next()).getId());
        }
        if(System.currentTimeMillis() >= DeadlineUtil.getReviewSubmissionDeadline()){
            HashMap<String,Object> data = new HashMap<>();
            data.put(Deadline.TYPE.REVIEW_SUBMISSION_DEADLINE.name(), DeadlineUtil.getReviewSubmissionDeadline());
            isSuccess = NotificationUtil.insertNotificationData(Long.valueOf(System.currentTimeMillis()), data, new ArrayList<>(), TYPE.PCC_REVIEW_DEADLINE_EXPIRED);
        }

        LOG.log(Level.INFO, CodeSmellFixer.LoggerCase.SUCCESS, isSuccess);
    }

    public static void paperDeadlineExpiredNotification(){
        HashMap<Integer,Notification> notificationDtls = NotificationUtil.getAllUnNoticedNotificationsBasedOnType(TYPE.PCM_PAPER_SUBMISSION_DEADLINE_EXPIRED);
        Boolean isNotificationExists = !notificationDtls.isEmpty();
        Boolean isSuccess = true;
        if(Boolean.TRUE.equals(isNotificationExists)){
            isSuccess =  NotificationUtil.deleteNotificationData(notificationDtls.get(notificationDtls.keySet().iterator().next()).getId());
        }
        if(System.currentTimeMillis() >= DeadlineUtil.getPaperSubmissionDeadline()){
            HashMap<String,Object> data = new HashMap<>();
            data.put(Deadline.TYPE.PAPER_SUBMISSION_DEADLINE.name(), DeadlineUtil.getPaperSubmissionDeadline());
            isSuccess = NotificationUtil.insertNotificationData(Long.valueOf(System.currentTimeMillis()), data, new ArrayList<>(), TYPE.PCM_PAPER_SUBMISSION_DEADLINE_EXPIRED);
        }

        LOG.log(Level.INFO, CodeSmellFixer.LoggerCase.SUCCESS, isSuccess);
    }
    
    public static void pccPendingReviewsNotification(){
        List<Integer> paperIds = new PcmOps().getPaperIdsWithAllPCMCompletedTasks();

        HashMap<Integer,Notification> notificationDtls = NotificationUtil.getAllUnNoticedNotificationsBasedOnType(TYPE.PCC_REVIEWS_PCM_COMPLETED_RATING_PENDING);
        Boolean isNotificationExists = !notificationDtls.isEmpty();
        Boolean isSuccess = true;
        if(Boolean.TRUE.equals(isNotificationExists)){
            isSuccess =  NotificationUtil.deleteNotificationData(notificationDtls.get(notificationDtls.keySet().iterator().next()).getId());
        }
        if(!paperIds.isEmpty()){
            HashMap<String,Object> data = new HashMap<>();
            data.put(CodeSmellFixer.CamelCase.PAPER_IDS, paperIds);
            isSuccess = NotificationUtil.insertNotificationData(Long.valueOf(System.currentTimeMillis()), data, new ArrayList<>(), TYPE.PCC_REVIEWS_PCM_COMPLETED_RATING_PENDING);
        }

        LOG.log(Level.INFO, CodeSmellFixer.LoggerCase.SUCCESS, isSuccess);
    }
}

