package com.sams.samsapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sams.samsapi.crud_utils.AssignedPapersUtil;
import com.sams.samsapi.crud_utils.NotificationUtil;
import com.sams.samsapi.crud_utils.PaperChoicesUtil;
import com.sams.samsapi.crud_utils.PaperDetailsUtil;
import com.sams.samsapi.crud_utils.PaperPoolUtil;
import com.sams.samsapi.crud_utils.ReviewQuestionnaireUtil;
import com.sams.samsapi.crud_utils.UserUtils;
import com.sams.samsapi.modelTemplates.User;
import com.sams.samsapi.modelTemplates.User.USER_TYPE;
import com.sams.samsapi.persistence.NotificationsInterface;
import com.sams.samsapi.persistence.NotificationsOps;
import com.sams.samsapi.persistence.UsersInterface;
import com.sams.samsapi.persistence.UsersOps;
import com.sams.samsapi.util.CodeSmellFixer;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/")
public class AuthenticationController {
    private static final Logger LOG = Logger.getLogger(AuthenticationController.class.getName());
    private UsersInterface usersInterface = new UsersOps();
    private NotificationsInterface notificationInterface = new NotificationsOps();

    public AuthenticationController(AssignedPapersUtil assignedPapersUtil, PaperChoicesUtil paperChoicesUtil,
            PaperDetailsUtil paperDetailsUtil,
            PaperPoolUtil papersUtil, ReviewQuestionnaireUtil reviewQuestionnaireUtil, UserUtils userUtils,
            NotificationUtil notificationUtil) {

    }

    @PostMapping("user/authenticate")
    public ResponseEntity<HashMap<String, Object>> authenticateUser(@RequestBody HashMap<String, String> credentials) {
        LOG.log(Level.INFO, "POST /user/authenticate {0}", new Object[] { credentials });
        HashMap<String, Object> response = new HashMap<>();
        try {
            if (!credentials.containsKey(CodeSmellFixer.LowerCase.USERNAME)
                    || !credentials.containsKey(CodeSmellFixer.LowerCase.PASSWORD)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            String userName = credentials.get(CodeSmellFixer.LowerCase.USERNAME);
            String password = credentials.get(CodeSmellFixer.LowerCase.PASSWORD);

            User user = usersInterface.authenticateUser(userName, password);

            if (user != null) {
                response.put(CodeSmellFixer.SnakeCase.USER_ID, user.getId());
                response.put(CodeSmellFixer.LowerCase.USERNAME, user.getName());
                response.put(CodeSmellFixer.LowerCase.PASSWORD, user.getPassword());
                response.put(CodeSmellFixer.LowerCase.TYPE, user.getType());
            }

            return user == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("user/register")
    public ResponseEntity<HashMap<String, Object>> registeruser(@RequestBody HashMap<String, String> credentials) {
        LOG.log(Level.INFO, "POST /user/register {0}", new Object[] { credentials });
        HashMap<String, Object> response = new HashMap<>();
        try {
            if (!credentials.containsKey(CodeSmellFixer.LowerCase.USERNAME)
                    || !credentials.containsKey(CodeSmellFixer.LowerCase.PASSWORD)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            String userName = credentials.get(CodeSmellFixer.LowerCase.USERNAME);
            String password = credentials.get(CodeSmellFixer.LowerCase.PASSWORD);
            String type = credentials.get(CodeSmellFixer.LowerCase.TYPE);
            User user = usersInterface.registerUser(userName, password, USER_TYPE.valueOf(type));
            if (user != null) {
                response.put(CodeSmellFixer.LowerCase.REGISTRATION, CodeSmellFixer.LowerCase.SUCCESS);
                response.put(CodeSmellFixer.LowerCase.USERNAME, user.getName());
                response.put(CodeSmellFixer.LowerCase.ID, user.getId());
                response.put(CodeSmellFixer.LowerCase.TYPE, user.getType());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("user/notification")
    public ResponseEntity<Object> getNotification(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCM) ||
                usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC) ||
                usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN) ||
                usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.SUBMITTER);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(notificationInterface.getNotificationForUser(Integer.parseInt(userId.toString())),
                HttpStatus.OK);
    }

    @PutMapping("user/notification")
    public ResponseEntity<Object> updateNotification(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCM) ||
                    usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC) ||
                    usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN) ||
                    usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.SUBMITTER);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if (!details.containsKey(CodeSmellFixer.LowerCase.IDS)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            ArrayList<Integer> notificationIds = new ArrayList<>();
            Object notificationIdsObj = details.get(CodeSmellFixer.LowerCase.IDS);
            if (!(notificationIdsObj instanceof ArrayList<?>)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            ArrayList<?> ids = (ArrayList<?>) notificationIdsObj;
            for (Object notificationId : ids) {
                notificationIds.add(Integer.parseInt(notificationId.toString()));
            }
            notificationInterface.updateNotificationForUser(Integer.parseInt(userId.toString()), notificationIds);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            if (ex.getMessage() != null) {
                LOG.log(Level.INFO, CodeSmellFixer.LoggerCase.EXCEPTION, ex.getMessage());
            }
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
