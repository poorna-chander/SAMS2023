package com.sams.samsapi.modelTemplates;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewQuestionnaire {
    public enum STATUS{
        IN_LIVE,
        NOT_LIVE
    }
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("field")
    private String field;
    @JsonProperty("status")
    private STATUS status;

    public ReviewQuestionnaire(@JsonProperty("id") Integer id,
                        @JsonProperty("field") String field,
                        @JsonProperty("status") STATUS status) {

        this.id = id;
        this.field = field;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
}
