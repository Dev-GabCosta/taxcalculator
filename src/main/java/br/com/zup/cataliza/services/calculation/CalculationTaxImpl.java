package br.com.zup.cataliza.services.calculation;

import org.springframework.stereotype.Service;

@Service
public class CalculationTaxImpl implements CalculationTax {

	@Override
	public Double calculate(Double value, Double tax) {
		return value * (tax * 0.01);
	}

}
