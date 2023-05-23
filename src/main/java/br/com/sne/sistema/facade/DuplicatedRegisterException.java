package br.com.sne.sistema.facade;

public class DuplicatedRegisterException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public DuplicatedRegisterException() {
		super("Registro Duplicado");
	}
}
