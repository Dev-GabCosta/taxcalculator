package br.com.zup.cataliza.controllers;

import br.com.zup.cataliza.dtos.TaxRegister;
import br.com.zup.cataliza.dtos.TaxResponse;
import br.com.zup.cataliza.services.TaxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos")
public class TaxController {
	private final TaxService taxService;

	public TaxController(TaxService taxService) {
		this.taxService = taxService;
	}

	@PostMapping
	public ResponseEntity<TaxResponse> createTax(@RequestBody TaxRegister taxRegister) {
		TaxResponse response = taxService.createTax(taxRegister);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaxResponse> displayTax(@PathVariable Long id) {
		TaxResponse response = taxService.displayTax(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping
	public ResponseEntity<List<TaxResponse>> listTaxes() {
		List<TaxResponse> taxes = taxService.listTaxes();
		return ResponseEntity.status(HttpStatus.OK).body(taxes);
	}

	@DeleteMapping("/{id}")
	public  ResponseEntity deleteTax(@PathVariable Long id){
		taxService.deleteTax(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
