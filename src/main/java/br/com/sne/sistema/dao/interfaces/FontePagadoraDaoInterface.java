package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.FontePagadora;

public interface FontePagadoraDaoInterface {

	public void atualizarFontePagadora(FontePagadora transientObject);
	
	public FontePagadora carregarFontePagadora(long id);

	public List<FontePagadora> listarFontePagadoras(int campo, String texto);
	
	public List<FontePagadora> listarFontePagadoras();
	
	public void removerFontePagadora(FontePagadora persistentObject);

	public void salvarFontePagadora(FontePagadora newInstance);

	public abstract void restaurarFontePagadora(FontePagadora persistentObject);

	public abstract List<FontePagadora> listarFontePagadorasExcluidas();


}