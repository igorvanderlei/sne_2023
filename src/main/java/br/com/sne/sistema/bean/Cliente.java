package br.com.sne.sistema.bean;

import java.io.Serializable;
import java.util.Date;

import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusCliente;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Cliente implements Comparable<Cliente>, Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String nome;
	
	@Enumerated(EnumType.ORDINAL)
	private StatusCliente status;
	
	private String cnpj;
	private String fone;
	private String fax;
	private String inscricaoEstadual;
	private String contato;
	private String ramal;
	private String email;

	private String observacoes;
	private String celular;
	private Endereco endereco;
	private Funcionario funcionario; 
	private Unidade unidade;
	private Date dataUltimaAlteracao;
	private Date dataCadastro;
	
	private DadosBancarios dadosBancarios1;
	private DadosBancarios dadosBancarios2;
	
	private String tipo; // F-Pessoa Física; J-Pessoa Jurídica
	
	private boolean deletado;	 

	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public Cliente(String nome, String cnpj, String fone, String celular, String contato, String ramal, String observacoes, Endereco endereco, Funcionario funcionario) {
		super();
		this.nome = nome;
		this.cnpj = cnpj;
		this.fone = fone;
		this.celular = celular;
		this.contato = contato;
		this.ramal = ramal;
		this.observacoes = observacoes;
		this.endereco = endereco;
		this.funcionario = funcionario;
		this.status = StatusCliente.PENDENTE;
		this.dataUltimaAlteracao = new Date();
	}

	@Override
	
	public String toString() {
		return nome.toString();
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

	public Cliente() {
		tipo = "J";
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
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Cliente(long id, String nome, Endereco endereco) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
	}

	public Cliente(String nome, String cnpj, String fone, String contato, String ramal, Endereco endereco) {
		this.nome = nome;
		this.cnpj = cnpj;
		this.fone = fone;
		this.contato = contato;
		this.ramal = ramal;
		this.endereco = endereco;
	}

	public int compareTo(Cliente outro){
    	if(this.getNome().compareToIgnoreCase(outro.getNome()) < 0){
    		return -1;
    	}
    	
    	if(this.getNome().compareToIgnoreCase(outro.getNome()) > 0){
    		return 1;
    	}
    	
    	return 0;
    	
    }

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public StatusCliente getStatus() {
		return status;
	}

	public void setStatus(StatusCliente status) {
		this.status = status;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Cliente))
			return false;
		Cliente other = (Cliente) obj;
		if (id != other.id)
			return false;
		return true;
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
 
