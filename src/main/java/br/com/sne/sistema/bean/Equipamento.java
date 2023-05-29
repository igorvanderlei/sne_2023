package br.com.sne.sistema.bean;

import java.io.Serializable;
import java.util.Date;

import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Equipamento implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	
	@Enumerated(EnumType.ORDINAL)
	private StatusEquipamento status;
	private String numeroSerie;
	
	private String marca;
	private String modelo;
	private Grupo grupo;
	
	private String lojaFornecedora;
	private String garantia;
	private String observacoes;
	private DescricaoEquipamento descricaoEquipamento;
	private Unidade unidade;
	private Date data;
	private String serialEquipamento;
	private String patrimonio;
	private boolean deletado;		
	
 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public Grupo getGrupo() {
		return grupo;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Equipamento)
			return ((Equipamento) obj).getId() == this.getId();
		return false;
	}



	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Equipamento(String numeroSerie, String marca, String modelo, DescricaoEquipamento descricaoEquipamento, String patrimonio) {
		this.numeroSerie = numeroSerie;
		this.marca = marca;
		this.modelo = modelo;
		this.descricaoEquipamento = descricaoEquipamento;
		this.status = StatusEquipamento.DISPONIVEL;
		this.patrimonio = patrimonio;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String toString(){
		return this.getNumeroSerie();
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public DescricaoEquipamento getDescricaoEquipamento() {
		return descricaoEquipamento;
	}

	public void setDescricaoEquipamento(DescricaoEquipamento descricaoEquipamento) {
		this.descricaoEquipamento = descricaoEquipamento;
	}

	public Equipamento(String numeroSerie,
			DescricaoEquipamento descricaoEquipamento,
			Unidade unidade) {
		this.numeroSerie = numeroSerie;
		this.descricaoEquipamento = descricaoEquipamento;
		this.unidade = unidade;
	}

	public Equipamento() {
	}

	public StatusEquipamento getStatus() {
		return status;
	}

	public void setStatus(StatusEquipamento status) {
		this.status = status;
	}

	public String getLojaFornecedora() {
		return lojaFornecedora;
	}

	public void setLojaFornecedora(String lojaFornecedora) {
		this.lojaFornecedora = lojaFornecedora;
	}

	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getSerialEquipamento() {
		return serialEquipamento;
	}

	public void setSerialEquipamento(String serialEquipamento) {
		this.serialEquipamento = serialEquipamento;
	}
	
	public String getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}
	
	
}
 
