package com.sams.samsapi.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewTemplate {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("paperId")
    private Integer paperId;
    @JsonProperty("pcmId")
    private Integer pcmId;
    @JsonProperty("reviews")
    private Reviews[] reviews;
    @JsonProperty("rating")
    private Integer rating;

    public ReviewTemplate(@JsonProperty("id") Integer id,
                        @JsonProperty("paperId") Integer paperId,
                        @JsonProperty("pcmId") Integer pcmId,
                        @JsonProperty("reviews") Reviews[] reviews,
                        @JsonProperty("rating") Integer rating) {

        this.id = id;
        this.paperId = paperId;
        this.pcmId = pcmId;
        this.reviews = reviews;
        this.rating = rating;
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

    public Reviews[] getReviews() {
        return reviews;
    }

    public void setReviews(Reviews[] reviews) {
        this.reviews = reviews;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public static class Reviews{
        @JsonProperty("reviewId") private Integer reviewId;
        @JsonProperty("value") private String value;

        public Reviews(@JsonProperty("reviewId") Integer reviewId,
                    @JsonProperty("value") String value){
            this.reviewId = reviewId;
            this.value = value;
        }

        public Integer getReviewId() {
            return reviewId;
        }

        public void setReviewId(Integer reviewId) {
            this.reviewId = reviewId;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
