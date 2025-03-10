package br.com.zup.cataliza.services.tax;

public class CofinsTaxStrategy implements TaxCalculationStrategy {

	@Override
	public double calculateTax(double value) {
		return 0.05 * value;
	}
}
