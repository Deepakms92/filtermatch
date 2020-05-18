package com.sparknetworks.filtermatch.request;

import lombok.Data;

@Data
public class FilterRequest {
    private String hasPhoto;
    private String inContact;
    private String isFavorite;
    private Range age;
    private Range compatibilityScore;
    private Range height;
    private DistanceRange distance;
}
