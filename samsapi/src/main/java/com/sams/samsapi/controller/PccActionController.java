package com.sams.samsapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.sams.samsapi.modelTemplates.ResearchPaper;
import com.sams.samsapi.modelTemplates.ReviewTemplate;
import com.sams.samsapi.modelTemplates.User.USER_TYPE;
import com.sams.samsapi.persistence.PccInterface;
import com.sams.samsapi.persistence.PccOps;
import com.sams.samsapi.persistence.UsersInterface;
import com.sams.samsapi.persistence.UsersOps;
import com.sams.samsapi.util.CodeSmellFixer;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/")
public class PccActionController {
    private static final Logger LOG = Logger.getLogger(PccActionController.class.getName());
    private UsersInterface usersInterface = new UsersOps();
    private PccInterface pccInterface = new PccOps();

    public PccActionController(AssignedPapersUtil assignedPapersUtil, PaperChoicesUtil paperChoicesUtil,
            PaperDetailsUtil paperDetailsUtil,
            PaperPoolUtil papersUtil, ReviewQuestionnaireUtil reviewQuestionnaireUtil, UserUtils userUtils,
            NotificationUtil notificationUtil) {

    }

    @GetMapping("/pcm")
    public ResponseEntity<Object> getAllPcm(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(pccInterface.getAvailablePCMs(), HttpStatus.OK);
    }

    @GetMapping("/papers/pcc/assignment/pending")
    public ResponseEntity<Object> getAllPapersPendingAssignment(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(pccInterface.getPaperPendingPCCAssignments(), HttpStatus.OK);
    }
    @GetMapping("/papers/pcc/rating/completed")
    public ResponseEntity<Object> getAllPapersCompletedAssignment(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(pccInterface.getRatingCompletedPaperDetails(), HttpStatus.OK);
    }
    @PutMapping("/papers/pcc/assignment/assign")
    public ResponseEntity<Object> updatePapersAssignment(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                    || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if (!details.containsKey(CodeSmellFixer.SnakeCase.PCM_ID)
                    || !details.containsKey(CodeSmellFixer.SnakeCase.PAPER_ID)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            Boolean status = pccInterface.assignPaperToPCM(
                    Integer.parseInt(details.get(CodeSmellFixer.SnakeCase.PAPER_ID).toString()),
                    Integer.parseInt(details.get(CodeSmellFixer.SnakeCase.PCM_ID).toString()));
            return Boolean.TRUE.equals(status) ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/papers/pcc/assignment/review/completed")
    public ResponseEntity<Object> getAllPaperAssignment(@RequestHeader Object userId)
            throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ArrayList<ResearchPaper> papers = pccInterface.getAllReviewCompletedPapersDetails();
        return new ResponseEntity<>(papers, HttpStatus.OK);
    }

    @GetMapping("/papers/pcc/assignment/view/{paperId}")
    public ResponseEntity<Object> getPaperAssignment(@RequestHeader Object userId, @PathVariable Integer paperId)
            throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        HashMap<Integer, ReviewTemplate> pcmReviews = pccInterface.viewPCMReviews(paperId);
        return pcmReviews.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(pcmReviews, HttpStatus.OK);
    }

    @GetMapping("/papers/pcc/assignment/choice")
    public ResponseEntity<Object> getPaperChoicesMade(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(pccInterface.getPCMChoices(), HttpStatus.OK);
    }

    @PutMapping("/papers/pcc/rate")
    public ResponseEntity<Object> updatePaperRating(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                    || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if (!details.containsKey(CodeSmellFixer.LowerCase.RATING)
                    || !details.containsKey(CodeSmellFixer.SnakeCase.PAPER_ID)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            pccInterface.ratePaper(Integer.parseInt(details.get(CodeSmellFixer.SnakeCase.PAPER_ID).toString()),
                    Integer.parseInt(details.get(CodeSmellFixer.LowerCase.RATING).toString()));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
