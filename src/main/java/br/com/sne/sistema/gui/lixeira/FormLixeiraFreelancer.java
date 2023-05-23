package br.com.sne.sistema.gui.lixeira;

import java.util.ArrayList;
import java.util.List;

import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.funcionario.FuncionarioTableModel;
import br.com.sne.sistema.gui.util.form.DefaultGarbageForm;

public class FormLixeiraFreelancer extends DefaultGarbageForm<Funcionario, FuncionarioTableModel>{
	private static final long serialVersionUID = 1L;

	public FormLixeiraFreelancer () {
		super(new FuncionarioTableModel(), "/images/icon_trash_18.png", "Freelancers Excluídos");
	}

	public List<Funcionario> listAll() {
		List<Funcionario> lista= new ArrayList<Funcionario>();
		lista.addAll(Facade.getInstance().listarFreelancersExcluidos());
		return lista;
	}

	public boolean restore(Funcionario current) {
		Facade.getInstance().restaurarFuncionario(current);
		return true;
	}
	

}
