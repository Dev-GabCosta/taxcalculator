package br.com.zup.cataliza.service;

public class IcmsTaxStrategyTest {
    @Test
    public void testIcmsTax() {
        TaxCalculationStrategy icmsStrategy = new IcmsTaxStrategy();
        assertEquals(180, icmsStrategy.calculateTax(1000));
    }
}
