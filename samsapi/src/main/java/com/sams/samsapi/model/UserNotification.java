package com.sams.samsapi.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sams.samsapi.model.Notification.STATUS;
import com.sams.samsapi.model.Notification.TYPE;

public class UserNotification {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("timeStamp")
    private Long timeStamp;
    @JsonProperty("data")
    private ArrayList<HashMap<String, Object>> data;
    @JsonProperty("type")
    private TYPE type;
    @JsonProperty("status")
    private STATUS status;

    public UserNotification(@JsonProperty("id") Integer id,
            @JsonProperty("timeStamp") Long timeStamp,
            @JsonProperty("data") ArrayList<HashMap<String, Object>> data,
            @JsonProperty("type") TYPE type,
            @JsonProperty("status") STATUS status) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.data = data;
        this.type = type;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public ArrayList<HashMap<String, Object>> getData() {
        return data;
    }

    public TYPE getType() {
        return type;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setData(ArrayList<HashMap<String, Object>> data) {
        this.data = data;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
}
