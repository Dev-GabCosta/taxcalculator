package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.TaxRegister;
import br.com.zup.cataliza.dtos.TaxResponse;
import br.com.zup.cataliza.models.Tax;
import br.com.zup.cataliza.models.User;
import br.com.zup.cataliza.repositories.TaxRepository;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TaxServiceTest {

	@Test
	void testCreateTax() {
		String taxName = "ICMS";
		String description = "Imposto sobre bens de consumo e serviços";
		Double taxRate = 0.192;

		TaxRepository taxRepository = mock(TaxRepository.class);
		TaxServiceImpl taxService = new TaxServiceImpl(taxRepository);
		TaxRegister taxRegister = new TaxRegister(taxName, description, taxRate);
		when(taxRepository.save(any(Tax.class))).thenAnswer(
				invocation -> {
					Tax tax = invocation.getArgument(0);
					Field idField = User.class.getDeclaredField("id");
					idField.setAccessible(true);
					idField.set(tax, 1L);
					return tax;
				}
		);
		TaxResponse createdTax = taxService.createTax(taxRegister);
		assertNotNull(createdTax);
		assertEquals(1L, createdTax.id());
		assertEquals(taxName, createdTax.nome());
		assertEquals(description, createdTax.descricao());
		verify(taxRepository, times(1)).save(any(Tax.class));
	}

}
