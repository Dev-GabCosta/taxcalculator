package br.com.zup.cataliza.services.tax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PisTaxStrategyTest {

	@Test
	void testPisTax() {
		TaxCalculationStrategy pis = new PisTaxStrategy();
		double result = pis.calculateTax(1000);
		assertEquals(10, result);
	}
}
