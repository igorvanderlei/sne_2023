package br.com.sne.sistema.bean;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.ImageIcon;

import java.util.Base64;

public class Unidade {
	private long id;
	private String nome;
	private Endereco endereco;

	private String razaoSocial;
	private String cnpj;
	private String inscricaoEstadual;
	private String codigo;
	
	private String imagemFormulario;
	private String imagemTela;

	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public String toString() {
		return nome.toString();
	}

	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
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

	public boolean equals(Object o)  {
		if(o instanceof Unidade) {
			Unidade uo = (Unidade) o;
			return uo.getNome().equalsIgnoreCase(this.getNome());
		}
		return false;
	}

	public String getImagemFormulario() {
		return imagemFormulario;
	}

	public void setImagemFormulario(String imagemFormulario) {
		this.imagemFormulario = imagemFormulario;
	}

	public String getImagemTela() {
		return imagemTela;
	}

	public void setImagemTela(String imagemTela) {
		this.imagemTela = imagemTela;
	}

	public void alterarImagemFormulario(String path) {
		this.imagemFormulario = converterImagemString(path);
	}

	public void alterarImagemTela(String path){
		this.imagemTela = converterImagemString(path);
	}
	
	public ImageIcon getImagemTelaImage() {
		if(imagemTela != null)
			return new ImageIcon(converterStringImageData(imagemTela));
		return null;
	}
	
	public ImageIcon getImagemFormularioImage() {
		if(imagemFormulario != null)
			return new ImageIcon(converterStringImageData(imagemFormulario));
		return null;
	}
	
	public byte[] converterStringImageData(String data) {
		return Base64.getDecoder().decode(data);
	}


	private String converterImagemString(String path) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			FileInputStream fis = new FileInputStream(path);

			byte[] buf = new byte[1024];
			int len;

			while ((len = fis.read(buf)) > 0){
				baos.write(buf, 0, len);
			}

			byte[] objeto = baos.toByteArray();
			String objetoStr = Base64.getEncoder().encodeToString(objeto);
			fis.close();
			baos.close();
			return objetoStr;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;


	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
}

