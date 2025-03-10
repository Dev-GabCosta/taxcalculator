package br.com.zup.cataliza.services.tax;

public class PisTaxStrategy implements TaxCalculationStrategy {

	@Override
	public double calculateTax(double value) {
		return 0.01 * value;
	}

}
