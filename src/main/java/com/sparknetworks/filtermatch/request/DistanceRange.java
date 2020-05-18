package com.sparknetworks.filtermatch.request;
/**
 * Request Object class for Distance Ranges .
 *
 * @author Deepak Srinivas
 */

import lombok.Data;

@Data
public class DistanceRange extends Range{

    private Double latitude;
    private Double longitude;

}
