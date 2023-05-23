package br.com.sne.sistema.bean;
import java.math.BigDecimal;
import java.util.Date;

public class RecursoSolicitado {
	private long id;
	private Date dataInicio;
	private Date dataFim;
	private BigDecimal precoUnitario;
	private BigDecimal precoCusto;
	private int quantidade;
	private int diarias;
	private BigDecimal subTotal;
	private Recurso recurso;
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
		return (subTotal = precoUnitario.multiply(quantidadeDiarias));
	}
	
	private BigDecimal calcularSubtotalCustoComDiarias() {
		BigDecimal quantidadeDiarias = new BigDecimal(quantidade).multiply(new BigDecimal(diarias));
		return (getPrecoCusto().multiply(quantidadeDiarias));
	}
	
	//Preço por período para ser utilizado em logística e cenografia
	private BigDecimal calcularSubtotalSemDiarias() {
		BigDecimal qtd = new BigDecimal(quantidade);
		return (subTotal = precoUnitario.multiply(qtd));
	}
	
	private BigDecimal calcularSubtotalCustoSemDiarias() {
		if(!recurso.isCalcularDiarias()) {
			BigDecimal qtd = new BigDecimal(quantidade);
			return (getPrecoCusto().multiply(qtd));
		}
		
		BigDecimal quantidadeDiarias = new BigDecimal(quantidade).multiply(new BigDecimal(diarias));
		return (getPrecoCusto().multiply(quantidadeDiarias));
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

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public RecursoSolicitado(Date dataInicio, Date dataFim,
			BigDecimal precoUnitario,BigDecimal precoCusto, int quantidade, Recurso recurso) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.precoUnitario = precoUnitario;
		this.quantidade = quantidade;
		this.recurso = recurso;
		this.precoCusto = precoCusto;
		calcularDiarias();
	}

	public RecursoSolicitado(BigDecimal precoUnitario, BigDecimal precoCusto, int quantidade,
			Recurso recurso) {
		this.precoUnitario = precoUnitario;
		this.quantidade = quantidade;
		this.recurso = recurso;
		this.precoCusto = precoCusto;
	}

	public RecursoSolicitado() {
		precoUnitario = BigDecimal.ZERO;
	}
	
	public BigDecimal getSubTotal(boolean diarias) {
		//if(diarias)
		if(recurso.isCalcularDiarias())
			calcularSubtotalComDiarias();
		else
			calcularSubtotalSemDiarias();
		return subTotal;
	}
	
	public BigDecimal getSubTotalCusto(boolean diarias) {
		//if(diarias)
		if(recurso.isCalcularDiarias())
			return calcularSubtotalCustoComDiarias();
		else
			return calcularSubtotalCustoSemDiarias();
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPrecoCusto() {
		if(precoCusto == null)
			return precoCusto = recurso.getPrecoCusto();
		return precoCusto;
	}

	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

}
 
