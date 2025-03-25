package br.com.zup.cataliza.exceptions;

public class ForbiddenException extends  RuntimeException{
	public ForbiddenException(String message) {
		super(message);
	}
}
