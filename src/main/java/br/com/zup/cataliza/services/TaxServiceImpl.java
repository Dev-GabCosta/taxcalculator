package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.TaxRegister;
import br.com.zup.cataliza.dtos.TaxResponse;
import br.com.zup.cataliza.models.Tax;
import br.com.zup.cataliza.repositories.TaxRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaxServiceImpl implements TaxService {
	private final TaxRepository taxRepository;

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

	@Override
	public List<TaxResponse> listTaxes() {
		return taxRepository.findAll()
				       .stream()
				       .map(
						       tax -> new TaxResponse(
								       tax.getId(),
								       tax.getName(),
								       tax.getDescription(),
								       tax.getTaxRate()
						       )
				       )
				       .collect(Collectors.toList());
	}

	@Override
	public TaxResponse displayTax(Long id) {
		Optional<Tax> optionalTax = taxRepository.findById(id);

		if (optionalTax.isEmpty()) {
			throw new RuntimeException("Não foi possível encontrar nenhum imposto com o id" + id);
		}

		Tax tax = optionalTax.get();

		return new TaxResponse(
				tax.getId(),
				tax.getName(),
				tax.getDescription(),
				tax.getTaxRate()
		);
	}

	@Override
	public void deleteTax(Long id) {
		Optional<Tax> optionalTax = taxRepository.findById(id);

		if (optionalTax.isEmpty()) {
			throw new RuntimeException("Não foi possível encontrar nenhum imposto com o id" + id);
		}

		taxRepository.deleteById(id);
	}

}
