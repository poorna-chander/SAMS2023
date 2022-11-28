package com.sams.samsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Deadline {
    public enum TYPE {
        PAPER_SUBMISSION_DEADLINE,
        REVIEW_SUBMISSION_DEADLINE
    }

    @JsonProperty("type")
    private TYPE type;
    @JsonProperty("timeStamp")
    private Long timeStamp;

    public Deadline(@JsonProperty("type") TYPE type,
            @JsonProperty("timeStamp") Long timeStamp) {
        this.type = type;
        this.timeStamp = timeStamp;
    }

    public TYPE getType() {
        return type;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
