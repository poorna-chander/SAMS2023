package com.sams.samsapi.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {
    public enum TYPE {
        PAPER_SUBMISSION,
        PCM_ASSIGNMENT,
        PCC_UNASSIGNED_PAPERS,
        PCC_REVIEW_DEADLINE_EXPIRED,
        PCM_PAPER_SUBMISSION_DEADLINE_EXPIRED,
        PCC_REVIEWS_PCM_COMPLETED_RATING_PENDING,
        SUBMITTED_FINAL_RATING
    }

    public enum STATUS {
        UN_NOTICED,
        NOTICED,
        PARTIAL_PCM_NOTICED,
        ALL_PCM_NOTICED,
        PARTIAL_PCC_NOTICED,
        ALL_PCC_NOTICED,
        PARTIAL_SUBMITTER_NOTICED,
        ALL_SUBMITTER_NOTICED,
        PARTIAL_ADMIN_NOTICED,
        ALL_ADMIN_NOTICED,
        ALL_NOTICED,
        PARTIAL_NOTICED
    }

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("timeStamp")
    private Long timeStamp;
    @JsonProperty("data")
    private HashMap<String, Object> data;
    @JsonProperty("type")
    private TYPE type;
    @JsonProperty("visitedIds")
    private ArrayList<Integer> visitedIds;
    @JsonProperty("status")
    private STATUS status;

    public Notification(@JsonProperty("id") Integer id,
            @JsonProperty("timeStamp") Long timeStamp,
            @JsonProperty("data") HashMap<String, Object> data,
            @JsonProperty("type") TYPE type,
            @JsonProperty("visitedIds") ArrayList<Integer> visitedIds,
            @JsonProperty("status") STATUS status) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.data = data;
        this.type = type;
        this.visitedIds = visitedIds;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public TYPE getType() {
        return type;
    }

    public ArrayList<Integer> getVisitedIds() {
        return visitedIds;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public void setVisitedIds(ArrayList<Integer> visitedIds) {
        this.visitedIds = visitedIds;
    }

    public void setVisitedIds(Integer visitedId) {
        
        if(this.visitedIds == null){
            this.visitedIds = new ArrayList();
        }

        this.visitedIds.add(visitedId);
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

}
