package br.com.zup.cataliza.controllers;

import br.com.zup.cataliza.dtos.TaxRegister;
import br.com.zup.cataliza.dtos.TaxResponse;
import br.com.zup.cataliza.services.TaxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipos")
public class TaxController {
	private  final TaxService taxService;

	public TaxController(TaxService taxService) {
		this.taxService = taxService;
	}

	@PostMapping
	public ResponseEntity<TaxResponse> createTax(@RequestBody TaxRegister taxRegister){
		TaxResponse response = taxService.createTax(taxRegister);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
