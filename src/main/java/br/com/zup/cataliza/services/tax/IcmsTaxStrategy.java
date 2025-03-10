package br.com.zup.cataliza.services.tax;

public class IcmsTaxStrategy implements TaxCalculationStrategy {
	@Override
	public double calculateTax(double value) {
		return 0.192 * value;
	}
}
