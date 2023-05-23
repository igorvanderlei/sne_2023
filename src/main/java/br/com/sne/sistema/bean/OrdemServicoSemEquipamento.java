package br.com.sne.sistema.bean;

public class OrdemServicoSemEquipamento extends OrdemServico{
	private static final long serialVersionUID = 1L;
	private OrdemServico ordemServicoEmergencial;

	public OrdemServico getOrdemServicoEmergencial() {
		return ordemServicoEmergencial;
	}

	public void setOrdemServicoEmergencial(OrdemServico ordemServicoEmergencial) {
		this.ordemServicoEmergencial = ordemServicoEmergencial;
	}
	
	public String toString() {
		return ordemServicoEmergencial.getId() + " - " + super.toString();
	}
	
}
