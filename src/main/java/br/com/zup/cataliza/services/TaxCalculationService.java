package br.com.zup.cataliza.services;


import br.com.zup.cataliza.dtos.CalculationRegister;
import br.com.zup.cataliza.dtos.CalculationResponse;

public interface TaxCalculationService {
	CalculationResponse calculateTax(CalculationRegister calculationRegister);

}