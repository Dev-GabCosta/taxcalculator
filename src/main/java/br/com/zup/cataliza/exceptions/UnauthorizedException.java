package br.com.zup.cataliza.exceptions;

public class UnauthorizedException extends  RuntimeException{
	public UnauthorizedException(String message) {
		super(message);
	}
}
