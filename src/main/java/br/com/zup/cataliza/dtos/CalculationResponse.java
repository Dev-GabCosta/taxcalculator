package br.com.zup.cataliza.dtos;

public record CalculationResponse(
		String tipoImposto,
		Double valorBase,
		Double aliquota,
		Double valorImposto
) {
}
