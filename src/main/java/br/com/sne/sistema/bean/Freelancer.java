package br.com.sne.sistema.bean;

import java.math.BigDecimal;

public class Freelancer extends Funcionario {
	private static final long serialVersionUID = 1L;
	private BigDecimal diaria;
	
	public Freelancer(String nome, String celular, Usuario usuario,
			Unidade unidade, Endereco endereco) {
		super(nome, celular, usuario, unidade, endereco);
	}
	public Freelancer() {
		super();
	}
	public BigDecimal getDiaria() {
		return diaria;
	}
	public void setDiaria(BigDecimal diaria) {
		this.diaria = diaria;
	}
	 
	
}
 
