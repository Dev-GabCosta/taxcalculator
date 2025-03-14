package br.com.zup.cataliza.controllers;

import br.com.zup.cataliza.dtos.CalculationRegister;
import br.com.zup.cataliza.dtos.CalculationResponse;
import br.com.zup.cataliza.services.TaxCalculationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CalculationController.class)
public class CalculationControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockitoBean
	private TaxCalculationService taxCalculationService;

	@Test
	void testCalculateTax() throws Exception {
		Long taxType = 1L;
		Double value = 1000.0;
		String taxName = "ICMS";
		Double taxRate = 19.2;
		Double taxValue = 192.0;

		CalculationRegister calculationRegister = new CalculationRegister(taxType, value);
		CalculationResponse calculationResponse = new CalculationResponse(taxName, value, taxRate, taxValue);

		when(taxCalculationService.calculateTax(any(CalculationRegister.class)))
				.thenReturn(calculationResponse);

		mockMvc.perform(
						post("/calculo")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(calculationRegister))
				)
				.andExpect(status().isOk())
				.andExpect(
						jsonPath("$.tipoImposto")
								.value(taxName))
				.andExpect(
						jsonPath("$.valorBase")
								.value(value))
				.andExpect(
						jsonPath("$.aliquota")
								.value(taxRate)
				)
				.andExpect(
						jsonPath("$.valorImposto")
								.value(taxValue)
				);

		verify(taxCalculationService, times(1))
				.calculateTax(any(CalculationRegister.class));
	}
}
