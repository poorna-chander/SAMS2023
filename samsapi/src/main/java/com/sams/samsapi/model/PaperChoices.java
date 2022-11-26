package com.sams.samsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaperChoices {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("paperId")
    private Integer paperId;
    @JsonProperty("pcmId")
    private Integer pcmId;


    public PaperChoices(@JsonProperty("id") Integer id,
                        @JsonProperty("paperId") Integer paperId,
                        @JsonProperty("pcmId") Integer pcmId) {

        this.id = id;
        this.paperId = paperId;
        this.pcmId = pcmId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getPcmId() {
        return pcmId;
    }

    public void setPcmId(Integer pcmId) {
        this.pcmId = pcmId;
    }

}
