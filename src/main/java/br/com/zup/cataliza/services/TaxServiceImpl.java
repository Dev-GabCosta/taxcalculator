package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.TaxRegister;
import br.com.zup.cataliza.dtos.TaxResponse;
import br.com.zup.cataliza.models.Tax;
import br.com.zup.cataliza.repositories.TaxRepository;
import org.springframework.stereotype.Service;

@Service
public class TaxServiceImpl implements TaxService{
	private  final TaxRepository taxRepository;

	public TaxServiceImpl(TaxRepository taxRepository) {
		this.taxRepository = taxRepository;
	}

	@Override
	public TaxResponse createTax(TaxRegister taxRegister) {
		Tax tax = new Tax(taxRegister.nome(), taxRegister.descricao(), taxRegister.aliquota());
		taxRepository.save(tax);
		return new TaxResponse(
				tax.getId(),
				tax.getName(),
				tax.getDescription(),
				tax.getTaxRate()
		);
	}

}
