package br.com.sne.sistema.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Funcionario implements Comparable<Funcionario>, Serializable{
	private static final long serialVersionUID = 1L;
	private long id;
	private String nome;
	private String celular;
	private Set<Funcao> funcao;
	private Unidade unidade;
	private Endereco endereco;
	private String observacoes;
	private String email;
	
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public Funcionario(String nome, String celular, Endereco endereco) {
		this.nome = nome;
		this.celular = celular;
		this.endereco = endereco;
		funcao = new HashSet<Funcao>();
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Funcionario(String nome, String celular, Usuario usuario, Unidade unidade, Endereco endereco) {
		this.nome = nome;
		this.celular = celular;
		this.unidade = unidade;
		this.endereco = endereco;
		funcao = new HashSet<Funcao>();
	}
	
	public Funcionario(String nome, String celular, Usuario usuario, Unidade unidade, Endereco endereco, String email) {
		this.nome = nome;
		this.celular = celular;
		this.unidade = unidade;
		this.endereco = endereco;
		this.email = email;
		funcao = new HashSet<Funcao>();
	}

	public Funcionario() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Set<Funcao> getFuncao() {
		return funcao;
	}

	public void setFuncao(Set<Funcao> funcao) {
		this.funcao = funcao;
	}
	
	public void adicionarFuncao(Funcao f) {
		funcao.add(f);
	}

	public int compareTo(Funcionario o) {
		if(o instanceof Funcionario) {
			return this.nome.compareTo( ((Funcionario) o).getNome() );
			
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof Funcionario) {
			Funcionario f = (Funcionario) arg0;
			return this.getId() == f.getId();
		}
		return false;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String toString() {
		return this.getNome();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
 
