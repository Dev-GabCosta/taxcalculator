package br.com.zup.cataliza.services.calculation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculationTaxTest {
	private final CalculationTaxImpl taxCalculation = new CalculationTaxImpl();
	private final Double value = 1000.0;
	private final Double tax = 19.2;

	@Test
	void testTaxCalculation() {
		Double result = taxCalculation.calculate(value, tax);
		assertEquals(192, result);
	}

}
