package br.com.zup.cataliza.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IcmsTaxStrategyTest {
    @Test
    public void testIcmsTax() {
        TaxCalculationStrategy icmsStrategy = new IcmsTaxStrategy();
        assertEquals(180, icmsStrategy.calculateTax(1000));
    }
}
