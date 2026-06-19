package com.aquispe.techretail.application.util;

import java.time.LocalDate;

public class LifeExpectancyCalculator {

    private static final int AVERAGE_LIFE_EXPECTANCY_YEARS = 80;

    public LocalDate calculateExpectedDeathDate(LocalDate birthDate) {
        if (birthDate == null) {
            return null;
        }
        return birthDate.plusYears(AVERAGE_LIFE_EXPECTANCY_YEARS);
    }
}
