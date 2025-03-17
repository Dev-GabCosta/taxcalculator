package br.com.zup.cataliza.controllers;

import br.com.zup.cataliza.dtos.TaxRegister;
import br.com.zup.cataliza.dtos.TaxResponse;
import br.com.zup.cataliza.services.TaxService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaxController.class)
public class TaxControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockitoBean
	private TaxService taxService;

	@Test
	void testCreateTax() throws Exception {
		String taxName = "ICMS";
		String description = "Imposto sobre bens de consumo e serviços";
		Double taxRate = 0.192;

		TaxRegister taxRegister = new TaxRegister(taxName, description, taxRate);
		TaxResponse taxResponse = new TaxResponse(1L, taxName, description, taxRate);

		when(taxService.createTax(any(TaxRegister.class))).thenReturn(taxResponse);
		mockMvc.perform(
						post("/tipos")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(taxRegister))
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome")
						           .value(taxName))
				.andExpect(jsonPath("$.descricao")
						           .value(description))
				.andExpect(jsonPath("$.aliquota")
						           .value(taxRate));

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
		mockMvc.perform(
						get("/tipos")
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id")
						           .value(taxId)
				)
				.andExpect(jsonPath("$[0].nome")
						           .value(taxName))
				.andExpect(jsonPath("$[0].descricao")
						           .value(description))
				.andExpect(jsonPath("$[0].aliquota")
						           .value(taxRate));

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

		mockMvc.perform(
						get("/tipos/" + taxId)
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status()
						           .isOk()
				)
				.andExpect(jsonPath("$.id")
						           .value(taxId)
				)
				.andExpect(jsonPath("$.nome")
						           .value(taxName))
				.andExpect(jsonPath("$.descricao")
						           .value(description))
				.andExpect(jsonPath("$.aliquota")
						           .value(taxRate));

		verify(taxService, times(1))
				.displayTax(taxId);
	}

	@Test
	void testDeleteTax() throws Exception {
		Long taxId = 1L;

		doNothing()
				.when(taxService)
				.deleteTax(taxId);

		mockMvc.perform(
						delete("/tipos/" + taxId)
				)
				.andExpect(
						status()
								.isNoContent());

		verify(taxService, times(1))
				.deleteTax(taxId);
	}

}
