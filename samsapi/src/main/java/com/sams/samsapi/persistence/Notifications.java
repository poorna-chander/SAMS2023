package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.sams.samsapi.json_crud_utils.NotificationUtil;
import com.sams.samsapi.model.Notification.TYPE;
import com.sams.samsapi.model.UserNotification;
import com.sams.samsapi.util.CodeSmellFixer;

public class Notifications implements NotificationsInterface {
    public Boolean insertPaperSubmissionNotification(Integer id, Integer revisionNo, String title, Integer paperId){
        ArrayList<HashMap<String,Object>> newData = new ArrayList<>();
        HashMap<String,Object> data = new HashMap<>();
        data.put(CodeSmellFixer.LowerCase.ID, id);
        data.put(CodeSmellFixer.CamelCase.REVISION_NO, revisionNo);
        data.put(CodeSmellFixer.LowerCase.TITLE, title);
        data.put(CodeSmellFixer.CamelCase.PAPER_ID, paperId);
        newData.add(data);

        return NotificationUtil.insertNotificationData(Long.valueOf(System.currentTimeMillis()), newData, new ArrayList<>(), TYPE.PAPER_SUBMISSION);
    }

    public Boolean insertPcmAssignmentNotification(Integer id, Integer paperId, Integer pcmId){
        ArrayList<HashMap<String,Object>> newData = new ArrayList<>();
        HashMap<String,Object> data = new HashMap<>();
        data.put(CodeSmellFixer.LowerCase.ID, id);
        data.put(CodeSmellFixer.CamelCase.PAPER_ID, paperId);
        data.put(CodeSmellFixer.CamelCase.PCM_ID, pcmId);
        newData.add(data);

        return NotificationUtil.insertNotificationData(Long.valueOf(System.currentTimeMillis()), newData, new ArrayList<>(), TYPE.PCM_ASSIGNMENT);
    }

    public Boolean insertFinalRatingNotification(Integer paperId, Integer submitterId){
        ArrayList<HashMap<String,Object>> newData = new ArrayList<>();
        HashMap<String,Object> data = new HashMap<>();
        ArrayList<Integer> paperIds = new ArrayList<>();
        paperIds.add(paperId);
        data.put(CodeSmellFixer.CamelCase.PAPER_ID, paperIds);
        newData.add(data);

        ArrayList<Integer> submitterIds = new ArrayList<>();
        submitterIds.add(submitterId);

        return NotificationUtil.insertNotificationData(Long.valueOf(System.currentTimeMillis()), newData, submitterIds, TYPE.SUBMITTED_FINAL_RATING);
    }

    @Override
    public HashMap<TYPE, ArrayList<UserNotification>> getNotificationForUser(Integer userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean updateNotificationForUser(Integer userId, ArrayList<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }
}
