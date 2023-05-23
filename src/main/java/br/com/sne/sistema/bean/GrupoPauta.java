package br.com.sne.sistema.bean;

import java.util.Date;
import java.util.List;

public class GrupoPauta {
		private long id;
		private Funcionario funcionario;
		private List<Pauta> pautas;
		private String titulo;
		private Date dataCadastro;
		private boolean deletado;
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public Funcionario getFuncionario() {
			return funcionario;
		}
		public void setFuncionario(Funcionario funcionario) {
			this.funcionario = funcionario;
		}
		public List<Pauta> getPautas() {
			return pautas;
		}
		public void setPautas(List<Pauta> pautas) {
			this.pautas = pautas;
		}
		public String getTitulo() {
			return titulo;
		}
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		public Date getDataCadastro() {
			return dataCadastro;
		}
		public void setDataCadastro(Date dataCadastro) {
			this.dataCadastro = dataCadastro;
		}
		public GrupoPauta() {
		}
		
		public String toString() {
			return titulo;
		}
		public boolean isDeletado() {
			return deletado;
		}
		public void setDeletado(boolean deletado) {
			this.deletado = deletado;
		}
}
