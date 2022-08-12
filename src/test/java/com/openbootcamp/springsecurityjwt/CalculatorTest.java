package com.openbootcamp.springsecurityjwt;

import com.openbootcamp.springsecurityjwt.service.CalculatorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    @Test
    void name() {
        CalculatorService service = new CalculatorService();
        double result = service.sum(4, 3);
        assertEquals(7, result);
    }
}
