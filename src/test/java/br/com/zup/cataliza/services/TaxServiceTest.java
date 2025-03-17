package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.TaxRegister;
import br.com.zup.cataliza.dtos.TaxResponse;
import br.com.zup.cataliza.models.Tax;
import br.com.zup.cataliza.repositories.TaxRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaxServiceTest {
	private final TaxRepository taxRepository = mock(TaxRepository.class);
	private final TaxServiceImpl taxService = new TaxServiceImpl(taxRepository);

	@Test
	void testCreateTax() {
		String taxName = "ICMS";
		String description = "Imposto sobre bens de consumo e serviços";
		Double taxRate = 0.192;

		TaxRegister taxRegister = new TaxRegister(taxName, description, taxRate);
		when(taxRepository.save(any(Tax.class))).thenAnswer(
				invocation -> {
					Tax tax = invocation.getArgument(0);
					Field idField = Tax.class.getDeclaredField("id");
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

	@Test
	void testListTaxes() throws NoSuchFieldException, IllegalAccessException {
		Tax tax1 = new Tax("ICMS", "Imposto sobre Circulação de Mercadorias e Prestação de Serviços", 0.192);
		Tax tax2 = new Tax("ISS", "Imposto sobre serviços", 0.05);
		Field idField = Tax.class.getDeclaredField("id");
		idField.setAccessible(true);
		idField.set(tax1, 1L);
		idField.set(tax2, 2L);
		List<Tax> taxes = Arrays.asList(tax1, tax2);

		when(taxRepository.findAll())
				.thenReturn(taxes);
		List<TaxResponse> listedTaxes = taxService.listTaxes();
		assertNotNull(listedTaxes);
		TaxResponse tax1FromTaxes = listedTaxes.get(0);
		TaxResponse tax2FromTaxes = listedTaxes.get(1);
		assertEquals(2, listedTaxes.size());
		assertEquals(1, tax1FromTaxes.id());
		assertEquals("ICMS", tax1FromTaxes.nome());
		assertEquals("Imposto sobre Circulação de Mercadorias e Prestação de Serviços", tax1FromTaxes.descricao());
		assertEquals(0.192, tax1FromTaxes.aliquota());
		assertEquals(2, tax2FromTaxes.id());
		assertEquals("ISS", tax2FromTaxes.nome());
		assertEquals("Imposto sobre serviços", tax2FromTaxes.descricao());
		assertEquals(0.05, tax2FromTaxes.aliquota());

		verify(taxRepository, times(1)).findAll();
	}

	@Test
	void testDisplayTax() throws NoSuchFieldException, IllegalAccessException {
		Long taxId = 1L;
		String taxName = "ICMS";
		String description = "Imposto sobre bens de consumo e serviços";
		Double taxRate = 0.192;

		Tax tax = new Tax(taxName, description, taxRate);
		Field idField = Tax.class.getDeclaredField("id");
		idField.setAccessible(true);
		idField.set(tax, 1L);
		when(taxRepository.findById(taxId))
				.thenReturn(Optional.of(tax));

		TaxResponse displaiedTax = taxService.displayTax(taxId);
		assertNotNull(displaiedTax);
		assertEquals(taxId, displaiedTax.id());
		assertEquals(taxName, displaiedTax.nome());
		assertEquals(description, displaiedTax.descricao());
		assertEquals(taxRate, displaiedTax.aliquota());

		verify(taxRepository, times(1))
				.findById(taxId);
	}

	@Test
	void testDeleteTax() {
		Long taxId = 1L;
		Tax tax = new Tax("ICMS", "Imposto sobre bens de consumo e serviços", 0.192);

		when(taxRepository.findById(taxId))
				.thenReturn(Optional.of(tax));
		doNothing()
				.when(taxRepository).deleteById(taxId);

		assertDoesNotThrow(()-> taxService.deleteTax(taxId));

		verify(taxRepository, times(1))
				.deleteById(taxId);
	}
}
