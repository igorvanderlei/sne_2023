package br.com.sne.sistema.bean;

import java.util.Date;

import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusPauta;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Pauta{
	//Empresa, evento, telefone, celular, 
	//email, contato, data (dia/mes/ano), local, observações;
	
	private long id;
	
	@Enumerated(EnumType.ORDINAL)
	private StatusPauta status; 
	
	private String nomeEvento;
	private String fone;
	private String email;
	private String contato;
	private String empresa;
	private String data;
	private boolean deletado;
	private String observacoes;

	private Funcionario funcionario;
	private Date dataCadastro;
	private Date proxContato;
	private String localEvento;
	
	public Pauta() {
		
	}
	
	public Pauta(String empresa,String nomeEvento,String fone,
			String email,String contato,String data,
			String observacoes,StatusPauta status) {
		this.contato = contato;
		this.data = data;
		this.email = email;
		this.setEmpresa(empresa);
		this.fone = fone;
		this.nomeEvento = nomeEvento;
		this.observacoes = observacoes;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}


	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public StatusPauta getStatus() {
		return status;
	}

	public void setStatus(StatusPauta status) {
		this.status = status;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pauta))
			return false;
		Pauta other = (Pauta) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public String toString() {
		return id+"";
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getLocalEvento() {
		return localEvento;
	}

	public void setLocalEvento(String localEvento) {
		this.localEvento = localEvento;
	}

	public Date getProxContato() {
		return proxContato;
	}

	public void setProxContato(Date proxContato) {
		this.proxContato = proxContato;
	}

}
