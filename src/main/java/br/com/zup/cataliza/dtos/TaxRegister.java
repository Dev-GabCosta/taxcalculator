package br.com.zup.cataliza.dtos;

public record TaxRegister(
		String nome,
		String descricao,
		Double aliquota
) {
}
