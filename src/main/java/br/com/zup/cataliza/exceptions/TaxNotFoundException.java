package br.com.zup.cataliza.exceptions;

public class TaxNotFoundException extends  RuntimeException{
	public TaxNotFoundException(String message) {
		super(message);
	}
}
