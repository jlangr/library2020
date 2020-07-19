package util;

import java.util.List;

public class Billing {

    public double monthlyBillingAverage(List<Double> charges) {
        return charges.stream()
                .reduce(0.0, (total, charge) -> total + charge)
                .doubleValue() / 12;
    }
}
