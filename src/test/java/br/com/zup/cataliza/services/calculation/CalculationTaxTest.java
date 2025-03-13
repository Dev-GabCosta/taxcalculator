package br.com.zup.cataliza.services.calculation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculationTaxTest {
	private final CalculationTaxImpl tax = new CalculationTaxImpl();
	private final Double value = 1000.0;

	@Test
	void testIcmsCalculation() {
		Double result = tax.calculateIcms(value);
		assertEquals(192, result);
	}

	@Test
	void testCofinsCalculation() {
		Double result = tax.calculateCofins(value);
		assertEquals(50, result);
	}

	@Test
	void testCalculatePis() {
		Double result = tax.calculatePis(value);
		assertEquals(10, result);
	}

}
