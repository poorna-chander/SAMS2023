package com.sams.samsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaperDetails {
    @JsonProperty("Papers")
    private ResearchPaper[] papers;
    @JsonProperty("AssignedPapers")
    private ReviewTemplate[] assignedPapers;
    @JsonProperty("PaperChoices")
    private PaperChoices[] paperChoices;

    public PaperDetails(@JsonProperty("Papers") ResearchPaper[] papers,
            @JsonProperty("AssignedPapers") ReviewTemplate[] assignedPapers,
            @JsonProperty("PaperChoices") PaperChoices[] paperChoices) {

        this.papers = papers;
        this.assignedPapers = assignedPapers;
        this.paperChoices = paperChoices;
    }

    public ResearchPaper[] getPapers() {
        return papers;
    }

    public void setPapers(ResearchPaper[] papers) {
        this.papers = papers;
    }

    public ReviewTemplate[] getAssignedPapers() {
        return assignedPapers;
    }

    public void setAssignedPapers(ReviewTemplate[] assignedPapers) {
        this.assignedPapers = assignedPapers;
    }

    public PaperChoices[] getPaperChoices() {
        return paperChoices;
    }

    public void setPaperChoices(PaperChoices[] paperChoices) {
        this.paperChoices = paperChoices;
    }
}
