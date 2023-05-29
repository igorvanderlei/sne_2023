package br.com.sne.sistema.bean;

import java.io.Serializable;

public class DescricaoEquipamento extends Recurso implements Serializable{
	private static final long serialVersionUID = 1L;
		public DescricaoEquipamento() {
	}
	
	public int compareTo(DescricaoEquipamento grp) {
		return this.getNome().compareToIgnoreCase(grp.getNome());
	}
	public boolean equals(Object obj) {
		if(obj instanceof DescricaoEquipamento)
			return this.getId() == ((DescricaoEquipamento) obj).getId();
		return false;
	}
 
}
 
