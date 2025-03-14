package br.com.zup.cataliza.controllers;

import br.com.zup.cataliza.dtos.CalculationRegister;
import br.com.zup.cataliza.dtos.CalculationResponse;
import br.com.zup.cataliza.services.TaxCalculationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculo")
public class CalculationController {
	private final TaxCalculationService service;

	public CalculationController(TaxCalculationService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<CalculationResponse> calculateTax(@RequestBody CalculationRegister register) {
		CalculationResponse response = service.calculateTax(register);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
