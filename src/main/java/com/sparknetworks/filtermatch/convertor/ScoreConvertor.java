package com.sparknetworks.filtermatch.convertor;

/**
 * Convertor class to convert percentage into decimals.
 *
 * @author Deepak Srinivas
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScoreConvertor {

    public Double percentageToDecimalConvertor(Double percentage) {
        Double value = 0.0;
        if (percentage != null) {
            value = (percentage) / 100;
        } else {
            log.info("## the value of pecentage is null");
        }
        return value;
    }
}
