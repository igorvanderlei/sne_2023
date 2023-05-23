package br.com.sne.sistema.bean;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
import br.com.sne.sistema.auth.MD5;

public class Usuario {
	private long id;
	private String login;
	private String password;
	private TipoUsuario tipoUsuario;
	private Funcionario funcionario;
	private String observacoes;
	private Unidade unidade;
	private String tokenGoogle;
	 
	private boolean deletado;
	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public boolean verificarPassword(String p) {
		return this.password.equalsIgnoreCase(MD5.encode(p));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void alterarPassword(String password) {
		this.password = MD5.encode(password);
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}


	public Usuario(String login, String password, TipoUsuario tipoUsuario, Funcionario funcionario) {
		this.login = login;
		this.password = MD5.encode(password);
		this.tipoUsuario = tipoUsuario;
		this.funcionario = funcionario;
	}

	public Usuario() {
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	public String toString() {
		return login;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public String getTokenGoogle() {
		return tokenGoogle;
	}

	public void setTokenGoogle(String tokenGoogle) {
		this.tokenGoogle = tokenGoogle;
	}
	
	public static String converterTokenString(File arquivo) {
        try {
        	FileInputStream fin = new FileInputStream(arquivo);
        	byte buffer[];
            byte conteudo[] = new byte[(int)arquivo.length()];

            fin.read(conteudo);
            fin.close();
            
            buffer = conteudo;
    		String objetoStr = Base64.getEncoder().encodeToString(buffer);
    		return objetoStr;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

	}
	
	public static byte[] converterStringToken(String data) {
		return Base64.getDecoder().decode(data);
	}
	
	public void alterarToken(File arquivo) {
		this.tokenGoogle = converterTokenString(arquivo);
	}
	/*
	public static void salvarToken(String token) {
		if(token != null) {
        	try {   
	   	     	File arquivo = new File("tokens/StoredCredential");
	   	     	arquivo.getParentFile().mkdir();
	        	FileOutputStream fw = new FileOutputStream(arquivo);
	            fw.write(converterStringToken(token));
	            fw.close();
   	     	
        	} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
*/
	
}
 
