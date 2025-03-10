package br.com.zup.cataliza.services.tax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CofinsTaxStrategyTest {

	@Test
	void testCofinsTax() {
		TaxCalculationStrategy cofins = new CofinsTaxStrategy();
		double result = cofins.calculateTax(1000);
		assertEquals(50, result);
	}

}
