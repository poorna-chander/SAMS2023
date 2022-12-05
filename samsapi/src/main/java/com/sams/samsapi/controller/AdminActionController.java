package com.sams.samsapi.controller;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.sams.samsapi.modelTemplates.Deadline.TYPE;
import com.sams.samsapi.modelTemplates.User.USER_TYPE;
import com.sams.samsapi.persistence.AdminInterface;
import com.sams.samsapi.persistence.AdminOps;
import com.sams.samsapi.persistence.UsersInterface;
import com.sams.samsapi.persistence.UsersOps;
import com.sams.samsapi.util.CodeSmellFixer;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/")
public class AdminActionController {
    private static final Logger LOG = Logger.getLogger(AdminActionController.class.getName());
    private UsersInterface usersInterface = new UsersOps();
    private AdminInterface adminInterface = new AdminOps();

    public AdminActionController(AssignedPapersUtil assignedPapersUtil, PaperChoicesUtil paperChoicesUtil,
            PaperDetailsUtil paperDetailsUtil,
            PaperPoolUtil papersUtil, ReviewQuestionnaireUtil reviewQuestionnaireUtil, UserUtils userUtils,
            NotificationUtil notificationUtil) {

    }

    @PutMapping("/papers/deadline")
    public ResponseEntity<Object> updateDeadline(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            if (!details.containsKey(CodeSmellFixer.LowerCase.TYPE)
                    || !details.containsKey(CodeSmellFixer.LowerCase.DEADLINE)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            Boolean status = adminInterface.updateDeadlines(
                    TYPE.valueOf(details.get(CodeSmellFixer.LowerCase.TYPE).toString()),
                    Long.valueOf(details.get(CodeSmellFixer.LowerCase.DEADLINE).toString()));
            return Boolean.TRUE.equals(status) ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/papers/template")
    public ResponseEntity<Object> updateTemplate(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            if (!details.containsKey(CodeSmellFixer.LowerCase.REVIEW)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }

            HashMap<Integer, String> reviewData = new HashMap<>();
            Object reviewDataObj = details.get(CodeSmellFixer.LowerCase.REVIEW);
            if (!(reviewDataObj instanceof HashMap<?, ?>)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }

            HashMap<?, ?> ids = (HashMap<?, ?>) reviewDataObj;
            for (Object id : ids.keySet()) {
                reviewData.put(Integer.parseInt(id.toString()), ids.get(id).toString());
            }

            Boolean status = adminInterface.updateTemplate(reviewData);
            return Boolean.TRUE.equals(status) ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/papers/template")
    public ResponseEntity<Object> getTemplate(@RequestHeader Object userId) throws Exception {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN) || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCM);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(adminInterface.getTemplate(),HttpStatus.OK);
    }

    @GetMapping("/papers/deadline")
    public ResponseEntity<Object> getDeadlines(@RequestHeader Object userId) throws Exception {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN) || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCM);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(adminInterface.getDeadlines(),HttpStatus.OK);
    }
}
