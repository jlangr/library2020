package com.langrsoft.cucumber;

import java.util.List;

public class Billing {

    public double monthlyBillingAverage(List<Double> charges) {
        return charges.stream()
                .mapToDouble(Double::doubleValue)
                .average().getAsDouble();
    }
}
