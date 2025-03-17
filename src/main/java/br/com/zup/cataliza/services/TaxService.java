package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.TaxRegister;
import br.com.zup.cataliza.dtos.TaxResponse;

import java.util.List;

public interface TaxService {
	TaxResponse createTax(TaxRegister taxRegister);

	List<TaxResponse> listTaxes();

	TaxResponse displayTax(Long id);

	void deleteTax(Long id);
}
