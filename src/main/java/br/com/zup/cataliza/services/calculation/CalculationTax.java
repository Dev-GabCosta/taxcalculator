package br.com.zup.cataliza.services.calculation;

public interface CalculationTax {
	Double calculateIcms(Double value);
	Double calculateCofins(Double value);
	Double calculatePis(Double value);
}
