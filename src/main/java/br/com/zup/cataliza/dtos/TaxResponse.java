package br.com.zup.cataliza.dtos;

public record TaxResponse (
		Long id,
		String nome,
		String descricao,
		Double aliquota
){
}
