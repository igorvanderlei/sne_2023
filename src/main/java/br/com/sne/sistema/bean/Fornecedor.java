package br.com.sne.sistema.bean;


public class Fornecedor {
 
	private long id;
	 
	private String nome;
	private String fone;
	private String contato;
	private String codigo;
	private Endereco endereco;
	
	private String cnpj;
	private String ramal;
	private String email;
	private String observacoes;
	private String celular;
	
	private DadosBancarios dadosBancarios1;
	private DadosBancarios dadosBancarios2;
	
	private String tipo; //Físico ou Jurídico
	
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public String toString () {
		return nome;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Fornecedor() {
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
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean equals(Object obj) {
		if(obj instanceof Fornecedor) {
			return this.codigo.equals(((Fornecedor) obj).getCodigo());
		}
		return false;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public DadosBancarios getDadosBancarios1() {
		return dadosBancarios1;
	}

	public void setDadosBancarios1(DadosBancarios dadosBancarios1) {
		this.dadosBancarios1 = dadosBancarios1;
	}

	public DadosBancarios getDadosBancarios2() {
		return dadosBancarios2;
	}

	public void setDadosBancarios2(DadosBancarios dadosBancarios2) {
		this.dadosBancarios2 = dadosBancarios2;
	}
	
	 
}
 
