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
import com.sams.samsapi.modelTemplates.ReviewTemplate;
import com.sams.samsapi.modelTemplates.ReviewTemplate.Reviews;
import com.sams.samsapi.modelTemplates.User.USER_TYPE;
import com.sams.samsapi.persistence.PcmInterface;
import com.sams.samsapi.persistence.PcmOps;
import com.sams.samsapi.persistence.UsersInterface;
import com.sams.samsapi.persistence.UsersOps;
import com.sams.samsapi.util.CodeSmellFixer;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/")
public class PcmActionController {
    private static final Logger LOG = Logger.getLogger(PcmActionController.class.getName());
    private UsersInterface usersInterface = new UsersOps();
    private PcmInterface pcmInterface = new PcmOps();

    public PcmActionController(AssignedPapersUtil assignedPapersUtil, PaperChoicesUtil paperChoicesUtil,
            PaperDetailsUtil paperDetailsUtil,
            PaperPoolUtil papersUtil, ReviewQuestionnaireUtil reviewQuestionnaireUtil, UserUtils userUtils,
            NotificationUtil notificationUtil) {

    }

    @CrossOrigin
    @PostMapping("/papers/pcm/choose")
    public ResponseEntity<Object> createPaperChoices(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCM);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if (!details.containsKey(CodeSmellFixer.SnakeCase.PAPER_IDS)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            ArrayList<Integer> paperIds = new ArrayList<>();
            Object paperIdsObj = details.get(CodeSmellFixer.SnakeCase.PAPER_IDS);
            if (!(paperIdsObj instanceof ArrayList<?>)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            ArrayList<?> ids = (ArrayList<?>) paperIdsObj;
            for (Object paperId : ids) {
                paperIds.add(Integer.parseInt(paperId.toString()));
            }
            Boolean status = pcmInterface.submitPaperChoices(paperIds, Integer.parseInt(userId.toString()));
            return Boolean.TRUE.equals(status) ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/papers/pcm/meta")
    public ResponseEntity<Object> getPaperMetaData(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCM);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(pcmInterface.getMetaAvailablePaperDetails(Integer.parseInt(userId.toString())),
                HttpStatus.OK);
    }

    @GetMapping("/papers/pcm")
    public ResponseEntity<Object> getAssignedPaperDetails(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCM);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(pcmInterface.getAssignedPaperDetails(Integer.parseInt(userId.toString())),
                HttpStatus.OK);
    }

    @PostMapping("/papers/pcm/review")
    public ResponseEntity<Object> createPaperReview(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCM);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            if (!details.containsKey(CodeSmellFixer.SnakeCase.PAPER_ID)
                    || !details.containsKey(CodeSmellFixer.LowerCase.REVIEWS)
                    || !details.containsKey(CodeSmellFixer.LowerCase.RATING)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }

            HashMap<Integer, String> reviewData = new HashMap<>();
            Object reviewDataObj = details.get(CodeSmellFixer.LowerCase.REVIEWS);
            if (!(reviewDataObj instanceof HashMap<?, ?>)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }

            HashMap<?, ?> ids = (HashMap<?, ?>) reviewDataObj;
            for (Object id : ids.keySet()) {
                reviewData.put(Integer.parseInt(id.toString()), ids.get(id).toString());
            }

            Reviews[] review = pcmInterface.getReviewData(reviewData);
            if (review == null) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }

            ReviewTemplate template = pcmInterface.getReviewTemplate(Integer.parseInt(userId.toString()),
                    Integer.parseInt(details.get(CodeSmellFixer.SnakeCase.PAPER_ID).toString()));

            template.setRating(Integer.parseInt(details.get(CodeSmellFixer.LowerCase.RATING).toString()));
            template.setReviews(review);

            Boolean status = pcmInterface.submitReview(
                    Integer.parseInt(details.get(CodeSmellFixer.SnakeCase.PAPER_ID).toString()), template);

            return Boolean.TRUE.equals(status) ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
