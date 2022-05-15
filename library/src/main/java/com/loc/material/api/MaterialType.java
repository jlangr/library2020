package com.loc.material.api;

public enum MaterialType {
    BOOK(21, 10),
    AUDIO_CASSETTE(14, 10),
    VINYL_RECORDING(14, 10),
    MICRO_FICHE(7, 200),
    AUDIO_CD(7, 100),
    SOFTWARE_CD(7, 500),
    DVD(3, 100),
    NEW_RELEASE_DVD(1, 200),
    BLU_RAY(3, 200),
    VIDEO_CASSETTE(7, 10);

    private final int checkoutPeriod;
    private final int dailyFine;

    MaterialType(int checkoutPeriod, int dailyFine) {
        this.checkoutPeriod = checkoutPeriod;
        this.dailyFine = dailyFine;
    }

    public int getCheckoutPeriod() {
        return checkoutPeriod;
    }

    public int getDailyFine() {
        return dailyFine;
    }
}
