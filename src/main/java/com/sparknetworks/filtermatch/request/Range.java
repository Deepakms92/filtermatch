package com.sparknetworks.filtermatch.request;

/**
 * Parent Request Object class for Defining Range .
 *
 * @author Deepak Srinivas
 */

import lombok.Data;

@Data
public class Range {
    private Double min;
    private Double max;
}
