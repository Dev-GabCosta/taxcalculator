package br.com.zup.cataliza.services.tax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IcmsTaxStrategyTest {
	@Test
	public void testIcmsTax() {
		TaxCalculationStrategy icms = new IcmsTaxStrategy();
		double result = icms.calculateTax(1000);
		assertEquals(192, result);
	}
}
