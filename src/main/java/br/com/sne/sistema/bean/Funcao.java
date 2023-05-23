package br.com.sne.sistema.bean;

import java.math.BigDecimal;

public class Funcao extends Recurso {
	
	private static final long serialVersionUID = 1L;

	public Funcao(String nome, String detalhe, BigDecimal preco) {
		super(nome, detalhe, preco);

	}
	public Funcao() {
	}

	@Override
	public String toString() {
		return this.getNome();
	}
	
	public int compareTo(Funcao f) {
		return this.getNome().compareToIgnoreCase(f.getNome());
	}
	 
	public boolean equals(Object obj) {
		if(obj instanceof Funcao)
			return this.getId() == ((Funcao) obj).getId();
		return false;
	}
}
 
