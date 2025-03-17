package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.CalculationRegister;
import br.com.zup.cataliza.dtos.CalculationResponse;
import br.com.zup.cataliza.models.Tax;
import br.com.zup.cataliza.repositories.TaxRepository;
import br.com.zup.cataliza.services.calculation.CalculationTax;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class TaxCalculationServiceTest {

	@Test
	void testCalculateTax() throws NoSuchFieldException, IllegalAccessException {
		Long taxId = 1L;
		Double value = 1000.0;

		TaxRepository repository = mock(TaxRepository.class);
		CalculationTax calculationTax = mock(CalculationTax.class);
		TaxCalculationServiceImpl service = new TaxCalculationServiceImpl(repository, calculationTax);
		CalculationRegister calculationRegister = new CalculationRegister(taxId, value);
		Tax tax = new Tax("ICMS", "Imposto sobre bens de consumo e serviços", 19.2);
		Field idField = Tax.class.getDeclaredField("id");
		idField.setAccessible(true);
		idField.set(tax, taxId);
		when(repository.findById(taxId))
				.thenReturn(Optional.of(tax));
		when(calculationTax.calculate(value, tax.getTaxRate())).thenReturn(192.0);
		CalculationResponse response = service.calculateTax(calculationRegister);
		Assertions.assertNotNull(response);
		Assertions.assertEquals("ICMS", response.tipoImposto());
		Assertions.assertEquals(value, response.valorBase());
		Assertions.assertEquals(19.2, response.aliquota());
		Assertions.assertEquals(192.0, response.valorImposto());
		verify(repository, times(1))
				.findById(taxId);
		verify(calculationTax, times(1))
				.calculate(value, tax.getTaxRate());
	}
}
