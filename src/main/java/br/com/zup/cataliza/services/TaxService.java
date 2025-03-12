package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.TaxRegister;
import br.com.zup.cataliza.dtos.TaxResponse;

public interface TaxService {
	TaxResponse createTax(TaxRegister taxRegister);
}
