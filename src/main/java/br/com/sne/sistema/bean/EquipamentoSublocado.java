package br.com.sne.sistema.bean;
import java.util.Date;

public class EquipamentoSublocado extends Equipamento {
	private static final long serialVersionUID = 1L;
	private long id;	 
	private Date dataInicio;
	private Date dataFim;
	
	private Date dataDevolucao;
	private boolean devolvido;
	
	private Fornecedor fornecedor;

	public EquipamentoSublocado(String numeroSerie,
			DescricaoEquipamento descricaoEquipamento, Date dataInicio,
			Date dataFim, Fornecedor fornecedor, Unidade unidade) {
		super(numeroSerie, descricaoEquipamento, unidade);
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.fornecedor = fornecedor;
	}

	public EquipamentoSublocado(String numeroSerie,
			DescricaoEquipamento descricaoEquipamento, Unidade unidade) {
		super(numeroSerie, descricaoEquipamento, unidade);
	}
	
	public EquipamentoSublocado(){
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public boolean isDevolvido() {
		return devolvido;
	}

	public void setDevolvido(boolean devolvido) {
		this.devolvido = devolvido;
	}
	
}
 
