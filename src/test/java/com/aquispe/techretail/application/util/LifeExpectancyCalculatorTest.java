package com.aquispe.techretail.application.util;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class LifeExpectancyCalculatorTest {

    private final LifeExpectancyCalculator calculator = new LifeExpectancyCalculator();

    @Test
    void shouldCalculateExpectedDeathDateCorrectly() {
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        LocalDate expectedDeathDate = LocalDate.of(2070, 5, 15);
        
        LocalDate actual = calculator.calculateExpectedDeathDate(birthDate);
        
        assertEquals(expectedDeathDate, actual);
    }

    @Test
    void shouldReturnNullWhenBirthDateIsNull() {
        assertNull(calculator.calculateExpectedDeathDate(null));
    }
}
