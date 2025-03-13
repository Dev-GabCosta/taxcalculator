package br.com.zup.cataliza.services.calculation;

public class CalculationTaxImpl implements CalculationTax {

	@Override
	public Double calculateIcms(Double value) {
		return 0.192 * value;
	}

	@Override
	public Double calculateCofins(Double value) {
		return 0.05 * value;
	}

	@Override
	public Double calculatePis(Double value) {
		return 0.01 * value;
	}

}
