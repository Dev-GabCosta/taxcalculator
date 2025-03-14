package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.CalculationRegister;
import br.com.zup.cataliza.dtos.CalculationResponse;
import br.com.zup.cataliza.models.Tax;
import br.com.zup.cataliza.repositories.TaxRepository;
import br.com.zup.cataliza.services.TaxCalculationService;
import br.com.zup.cataliza.services.calculation.CalculationTax;

import java.util.Optional;

public class TaxCalculationServiceImpl implements TaxCalculationService {
	private final TaxRepository taxRepository;
	private final CalculationTax calculationTax;

	public TaxCalculationServiceImpl(TaxRepository taxRepository, CalculationTax calculationTax) {
		this.taxRepository = taxRepository;
		this.calculationTax = calculationTax;
	}

	@Override
	public CalculationResponse calculateTax(CalculationRegister register) {
		Double taxValue = 0.0;

		Optional<Tax> optionalTax = taxRepository.findById(register.tipoImpostoId());

		if (optionalTax.isEmpty()) {
			throw new RuntimeException("Nenhum imposto com esse id foi encontrado");
		}

		Long id = optionalTax.get().getId();
		String taxType = optionalTax.get().getName();
		Double taxRate = optionalTax.get().getTaxRate();
		Double value = register.valorBase();

		if (id == 1) {
			taxValue = calculationTax.calculateIcms(value);
		}

		if (id == 2) {
			taxValue = calculationTax.calculateCofins(value);
		}

		if (id == 3) {
			taxValue = calculationTax.calculatePis(value);
		}

		return new CalculationResponse(
				taxType,
				value,
				taxRate,
				taxValue
		);
	}

}
