package br.com.zup.cataliza.controllers;

import br.com.zup.cataliza.dtos.TaxRegister;
import br.com.zup.cataliza.dtos.TaxResponse;
import br.com.zup.cataliza.services.TaxService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaxControllerTest {
	@Mock
	private TaxService taxService;

	@InjectMocks
	private TaxController taxController;

	@Test
	void testCreateTax() throws Exception {
		String taxName = "ICMS";
		String description = "Imposto sobre bens de consumo e serviços";
		Double taxRate = 0.192;

		TaxRegister taxRegister = new TaxRegister(taxName, description, taxRate);
		TaxResponse taxResponse = new TaxResponse(1L, taxName, description, taxRate);

		when(taxService.createTax(any(TaxRegister.class))).thenReturn(taxResponse);

		taxController.createTax(taxRegister);

		verify(taxService, times(1))
				.createTax(any(TaxRegister.class));
	}

	@Test
	void testListTaxes() throws Exception {
		Long taxId = 1L;
		String taxName = "ICMS";
		String description = "Imposto sobre bens de consumo e serviços";
		Double taxRate = 0.192;

		TaxResponse taxResponse = new TaxResponse(1L, taxName, description, taxRate);
		List<TaxResponse> taxResponseList = List.of(taxResponse);

		when(taxService.listTaxes()).thenReturn(taxResponseList);

		taxController.listTaxes();

		verify(taxService, times(1))
				.listTaxes();
	}

	@Test
	void testDisplayTax() throws Exception {
		Long taxId = 1L;
		String taxName = "ICMS";
		String description = "Imposto sobre bens de consumo e serviços";
		Double taxRate = 0.192;

		TaxResponse taxResponse = new TaxResponse(taxId, taxName, description, taxRate);

		when(taxService.displayTax(taxId))
				.thenReturn(taxResponse);

        taxController.displayTax(taxId);

		verify(taxService, times(1))
				.displayTax(taxId);
	}

	@Test
	void testDeleteTax() throws Exception {
		Long taxId = 1L;

		doNothing()
				.when(taxService)
				.deleteTax(taxId);

	    taxController.deleteTax(taxId);

		verify(taxService, times(1))
				.deleteTax(taxId);
	}

}
