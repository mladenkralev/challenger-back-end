package com.challenger.demo.challenges.convertors;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@Component
public class DoubleParser {
    @Bean
    public DecimalFormat doubleFormatter() {
        DecimalFormat df = new DecimalFormat("0.0");
        df.setRoundingMode(RoundingMode.UP);
        return df;
    }
}
