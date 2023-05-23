package br.com.sne.sistema.bean;

public class SalaLocal {
	private long id;
	private String nome;
	private float largura;
	private float comprimento;
	private float peDireito;
	private float areaTotal;
	private boolean deletado;
	private boolean pontoFixacaoAerea;
	private int pontosFixacaoAerea;
	
	public SalaLocal(String nome,float largura, float comprimento, float peDireito) {
		this.nome = nome;
		this.largura = largura;
		this.comprimento = comprimento;
		this.peDireito = peDireito;
		calcularAreaTotal();
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch( CloneNotSupportedException e ) {
			return null;
		}
	} 
	
	public SalaLocal() {
		
	}
	
	public void calcularAreaTotal() {
		this.areaTotal = largura * comprimento;
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
	public String toString() {
		return nome;
	}
	public float getLargura() {
		return largura;
	}
	public void setLargura(float largura) {
		this.largura = largura;
	}
	public float getComprimento() {
		return comprimento;
	}
	public void setComprimento(float comprimento) {
		this.comprimento = comprimento;
	}
	public float getAreaTotal() {
		return areaTotal;
	}
	public void setAreaTotal(float areaTotal) {
		this.areaTotal = areaTotal;
	}
	public float getPeDireito() {
		return peDireito;
	}
	public void setPeDireito(float peDireito) {
		this.peDireito = peDireito;
	}
	
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}

	public boolean isPontoFixacaoAerea() {
		return pontoFixacaoAerea;
	}

	public void setPontoFixacaoAerea(boolean pontoFixacaoAerea) {
		this.pontoFixacaoAerea = pontoFixacaoAerea;
	}
	
	public int getPontosFixacaoAerea() {
		return pontosFixacaoAerea;
	}

	public void setPontosFixacaoAerea(int pontosFixacaoAerea) {
		this.pontosFixacaoAerea = pontosFixacaoAerea;
	}
	
	
}
