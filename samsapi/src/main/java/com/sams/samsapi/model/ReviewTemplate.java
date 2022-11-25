package com.sams.samsapi.model;

import java.util.HashMap;

public class ReviewTemplate {
    private int paperID;
    private int reviewerUserID;
    private HashMap<String, String> details;
    private int rating;

    public ReviewTemplate(int paperID, int reviewerUserID, HashMap<String, String> details, int rating) {
        this.paperID = paperID;
        this.reviewerUserID = reviewerUserID;
        this.details = details;
        this.rating = rating;
    }

    public int getPaperID() {
        return this.paperID;
    }

    public void setPaperID(int paperID) {
        this.paperID = paperID;
    }

    public int getReviewerUserID() {
        return this.reviewerUserID;
    }

    public void setReviewerUserID(int reviewerUserID) {
        this.reviewerUserID = reviewerUserID;
    }

    public HashMap<String, String> getDetails() {
        return this.details;
    }

    public void setDetails(HashMap<String, String> details) {
        this.details = details;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
