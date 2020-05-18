package com.sparknetworks.filtermatch.request;

import lombok.Data;

@Data
public class DistanceRange {

    private Double latitude;
    private Double longitude;
    private Double min;
    private Double max;
}
