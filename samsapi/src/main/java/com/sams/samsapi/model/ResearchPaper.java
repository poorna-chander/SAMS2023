package com.sams.samsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResearchPaper {
    @JsonProperty("id")
    private int id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("SubmiterId")
    private int SubmiterId;
    @JsonProperty("authors")
    private String[] authors;
    @JsonProperty("contact")
    private String contact;
    @JsonProperty("revisionNumber")
    private int revisionNumber;
    @JsonProperty("fileLocation")
    private String fileLocation;
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("fileExtension")
    private String fileExtension;
    @JsonProperty("PCMrating1")
    private ReviewTemplate PCMrating1;
    @JsonProperty("PCMrating2")
    private ReviewTemplate PCMrating2;
    @JsonProperty("PCMrating3")
    private ReviewTemplate PCMrating3;
    @JsonProperty("PCCrating")
    private ReviewTemplate PCCrating;
    @JsonProperty("reviewComplete")
    private boolean reviewComplete;

    public ResearchPaper(int id,
            String title,
            int SubmiterId,
            String[] authors,
            String contact,
            int revisionNumber,
            String fileLocation,
            String fileName,
            String fileExtension,
            ReviewTemplate PCMrating1,
            ReviewTemplate PCMrating2,
            ReviewTemplate PCMrating3,
            ReviewTemplate PCCrating,
            boolean reviewComplete) {

        this.id = id;
        this.title = title;
        this.SubmiterId = SubmiterId;
        this.authors = authors;
        this.contact = contact;
        this.revisionNumber = revisionNumber;
        this.reviewComplete = reviewComplete;
        this.fileExtension = fileExtension;
        this.fileLocation = fileLocation;
        this.fileName = fileName;
        this.PCMrating1 = PCMrating1;
        this.PCMrating2 = PCMrating2;
        this.PCMrating3 = PCMrating3;
        this.PCCrating = PCCrating;

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSubmiterId() {
        return this.SubmiterId;
    }

    public void setSubmiterId(int SubmiterId) {
        this.SubmiterId = SubmiterId;
    }

    public String[] getAuthors() {
        return this.authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getRevisionNumber() {
        return this.revisionNumber;
    }

    public void setRevisionNumber(int revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public String getFileLocation() {
        return this.fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public ReviewTemplate getPCMrating1() {
        return this.PCMrating1;
    }

    public void setPCMrating1(ReviewTemplate PCMrating1) {
        this.PCMrating1 = PCMrating1;
    }

    public ReviewTemplate getPCMrating2() {
        return this.PCMrating2;
    }

    public void setPCMrating2(ReviewTemplate PCMrating2) {
        this.PCMrating2 = PCMrating2;
    }

    public ReviewTemplate getPCMrating3() {
        return this.PCMrating3;
    }

    public void setPCMrating3(ReviewTemplate PCMrating3) {
        this.PCMrating3 = PCMrating3;
    }

    public ReviewTemplate getPCCrating() {
        return this.PCCrating;
    }

    public void setPCCrating(ReviewTemplate PCCrating) {
        this.PCCrating = PCCrating;
    }

    public boolean isReviewComplete() {
        return this.reviewComplete;
    }

    public boolean getReviewComplete() {
        return this.reviewComplete;
    }

    public void setReviewComplete(boolean reviewComplete) {
        this.reviewComplete = reviewComplete;
    }
}
