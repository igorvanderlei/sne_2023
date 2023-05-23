package br.com.sne.sistema.bean;
import java.math.BigDecimal;
import java.util.Date;

public class RecursoTerceirizadoSolicitado {
	private long id;
	private Date dataInicio;
	private Date dataFim;
	private BigDecimal precoEmpresa;
	private BigDecimal precoFornecedor;
	private BigDecimal precoUnitario;
	private int diarias;
	private int quantidade;
	private BigDecimal subTotal;
	private RecursoTerceirizado recurso;
	private AmbienteEvento ambiente;
	private String descricao;
	
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}

	private void calcularDiarias() {
		if(dataInicio != null && dataFim != null) {
			long m1 = dataInicio.getTime()  / 3600000;
			long m2 = dataFim.getTime() / 3600000;
			long valor = (m2 - m1)/ (24);
			diarias = ((int)valor) + 1;
		} else 
			diarias = 0;
		calcularSubtotal();
	}
	
	private void calcularSubtotal() {
		if(recurso != null) {
			if(!recurso.isCalcularDiarias()) {
				calcularSubtotalSemDiarias();
			}
			else {
				calcularSubtotalComDiarias();
			}
		}
	}
	
	private BigDecimal calcularSubtotalComDiarias() {
		BigDecimal quantidadeDiarias = new BigDecimal(quantidade).multiply(new BigDecimal(diarias));
		return (subTotal = getPrecoEmpresa().multiply(quantidadeDiarias));
		
	}
	
	
	private BigDecimal calcularSubtotalFornecedorComDiarias() {
		BigDecimal quantidadeDiarias = new BigDecimal(quantidade).multiply(new BigDecimal(diarias));

		return (subTotal = getPrecoFornecedor().multiply(quantidadeDiarias));
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
		calcularDiarias();
	}

	@Override
	public String toString() {
		return ""+id;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
		calcularDiarias();
	}

	public RecursoTerceirizado getRecurso() {
		return recurso;
	}

	public void setRecurso(RecursoTerceirizado recurso) {
		this.recurso = recurso;
	}

	public RecursoTerceirizadoSolicitado(Date dataInicio, Date dataFim, BigDecimal precoUnitario,
			BigDecimal precoEmpresa, BigDecimal precoFornecedor,
			int quantidade,RecursoTerceirizado recurso) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.setPrecoUnitario(precoUnitario);
		this.setPrecoEmpresa(precoEmpresa);
		this.setPrecoFornecedor(precoFornecedor);
		this.recurso = recurso;
		this.quantidade = quantidade;
		calcularDiarias();
	}

	public RecursoTerceirizadoSolicitado(BigDecimal precoUnitario,BigDecimal precoEmpresa, BigDecimal precoFornecedor,
			int quantidade,RecursoTerceirizado recurso) {
		this.setPrecoUnitario(precoUnitario);
		this.setPrecoEmpresa(precoEmpresa);
		this.setPrecoFornecedor(precoFornecedor);
		this.recurso = recurso;
		this.setQuantidade(quantidade);
	}

	public RecursoTerceirizadoSolicitado() {
		setPrecoEmpresa(BigDecimal.ZERO);
		setPrecoFornecedor(BigDecimal.ZERO);
		setPrecoUnitario(BigDecimal.ZERO);
	}
	
	public BigDecimal getSubTotal(boolean diarias) {
		if(recurso.isCalcularDiarias())
			calcularSubtotalComDiarias();
		else
			calcularSubtotalSemDiarias();
		return subTotal;
	}
	
	public BigDecimal getSubTotalFornecedor(boolean diarias) {
		if(recurso.isCalcularDiarias())
			calcularSubtotalFornecedorComDiarias();
		else
			calcularSubtotalFornecedorSemDiarias();
		return subTotal;
	}
	
	//Preço por período para ser utilizado em logística e cenografia
	private BigDecimal calcularSubtotalSemDiarias() {
		if(!recurso.isCalcularDiarias()) {
			BigDecimal qtd = new BigDecimal(quantidade);
			return (subTotal = getPrecoEmpresa().multiply(qtd));
		}
		BigDecimal qtd = new BigDecimal(quantidade);
		return (subTotal = getPrecoEmpresa().multiply(qtd));
	}
	
	private BigDecimal calcularSubtotalFornecedorSemDiarias() {
		BigDecimal qtd = new BigDecimal(quantidade);
		return (subTotal = getPrecoFornecedor().multiply(qtd));
	}
	
	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public int getDiarias() {
		return diarias;
	}

	protected void setDiarias(int diarias) {
		this.diarias = diarias;
	}

	public AmbienteEvento getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(AmbienteEvento ambiente) {
		this.ambiente = ambiente;
	}

	public BigDecimal getPrecoFornecedor() {
		if(precoFornecedor == null)
			return precoFornecedor = recurso.getPrecoFornecedor();
		return precoFornecedor;
	}

	public void setPrecoFornecedor(BigDecimal precoFornecedor) {
		this.precoFornecedor = precoFornecedor;
	}

	public BigDecimal getPrecoEmpresa() {
		return precoEmpresa;
	}

	public void setPrecoEmpresa(BigDecimal precoEmpresa) {
		this.precoEmpresa = precoEmpresa;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	
}
 
