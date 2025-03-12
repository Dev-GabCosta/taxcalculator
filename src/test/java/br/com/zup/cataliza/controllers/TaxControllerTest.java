package br.com.zup.cataliza.controllers;

import br.com.zup.cataliza.dtos.TaxRegister;
import br.com.zup.cataliza.dtos.TaxResponse;
import br.com.zup.cataliza.services.TaxService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.TaxController.class)
public class TaxControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockitoBean
	private TaxService taxService;

	@Test
	void testCreateTax() throws JsonProcessingException {
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
}
