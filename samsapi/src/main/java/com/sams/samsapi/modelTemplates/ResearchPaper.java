package com.sams.samsapi.modelTemplates;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResearchPaper {
    @JsonProperty("id")
    private int id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("submitterId")
    private Integer submitterId;
    @JsonProperty("authors")
    private List<String> authors;
    @JsonProperty("contact")
    private String contact;
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("fileExtension")
    private String fileExtension;
    @JsonProperty("paperId")
    private Integer paperId;
    @JsonProperty("revisionNo")
    private Integer revisionNo;
    @JsonProperty("rating")
    private Integer rating;

    public ResearchPaper(@JsonProperty("id") Integer id,
            @JsonProperty("title") String title,
            @JsonProperty("submitterId") Integer submitterId,
            @JsonProperty("authors") List<String> authors,
            @JsonProperty("contact") String contact,
            @JsonProperty("fileName") String fileName,
            @JsonProperty("fileExtension") String fileExtension,
            @JsonProperty("paperId") Integer paperId,
            @JsonProperty("revisionNo") Integer revisionNo,
            @JsonProperty("rating") Integer rating) {

        this.id = id;
        this.title = title;
        this.submitterId = submitterId;
        this.authors = authors;
        this.contact = contact;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.paperId = paperId;
        this.revisionNo = revisionNo;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getSubmitterId() {
        return submitterId;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getContact() {
        return contact;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public Integer getRevisionNo() {
        return revisionNo;
    }

    public Integer getRating() {
        return rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubmitterId(Integer submitterId) {
        this.submitterId = submitterId;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public void setRevisionNo(Integer revisionNo) {
        this.revisionNo = revisionNo;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
