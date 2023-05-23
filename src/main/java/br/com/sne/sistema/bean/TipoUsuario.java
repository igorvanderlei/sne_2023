package br.com.sne.sistema.bean;

import java.util.HashSet;
import java.util.Set;

import br.com.sne.sistema.auth.AccessManager.permission;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;

@Entity
public class TipoUsuario implements Comparable<TipoUsuario>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	
	
	@ElementCollection(targetClass = permission.class, fetch = FetchType.EAGER)
	@JoinTable(name = "permissao_tipousuario", joinColumns = @JoinColumn(name = "idTipoUsuario"))
	@Column(name = "permissao", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Set<permission> permissao;
	
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
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


	public Set<permission> getPermissao() {
		return permissao;
	}

	public void setPermissao(Set<permission> permissao) {
		this.permissao = permissao;
	}

	public TipoUsuario(String nome) {
		this.nome = nome;
		this.permissao = new HashSet<permission>();
	}

	public TipoUsuario() {
		permissao = new HashSet<permission>();
	}
	
	public void adicionarPermissao(permission p) {
		permissao.add(p);
	}
	
	public void removerPermissao(permission p) {
		permissao.remove(p);
	}
	
	public String toString() {
		return this.nome;
	}

	public int compareTo(TipoUsuario arg0) {
		return this.nome.compareTo( ((TipoUsuario) arg0).getNome() );
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TipoUsuario))
			return false;
		TipoUsuario other = (TipoUsuario) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
 
