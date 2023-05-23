package br.com.sne.sistema.bean;

public class AlertaOSEmergencial {
	private long id;
	private OrdemServico osEmergencial;
	private OrdemServico osSemEquipamento;
	
	private boolean status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public OrdemServico getOsEmergencial() {
		return osEmergencial;
	}
	public void setOsEmergencial(OrdemServico osEmergencial) {
		this.osEmergencial = osEmergencial;
	}
	public OrdemServico getOsSemEquipamento() {
		return osSemEquipamento;
	}
	public void setOsSemEquipamento(OrdemServico osSemEquipamento) {
		this.osSemEquipamento = osSemEquipamento;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public AlertaOSEmergencial() {
	}
	
	

}
