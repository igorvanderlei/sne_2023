package br.com.sne.sistema.auth;


public class AccessManager {
	public enum permission {
		AGENDAR_RECOLHIMENTO("Define o funcionários responsável pelo recolhimento dos recursos"),
		ENVIAR_EQUIPAMENTO ("Alocar Recursos para Ordem de Serviço"),
		ALTERAR_OS_ANDAMENTO("Permite que o usuário adicione ou remova recursos solicitados de uma OS em andamento"),
		ALTERAR_VENDEDOR ("Alterar o Vendedor que atende a um Cliente"),
		ALTERAR_PERMISSOES_USUARIO ("Alterar Permissões dos Usuários"),
		ALTERAR_STATUS_CLIENTE ("Alterar o Status de um Cliente"),
		
		CADASTRAR_CENTRO_CUSTO("Cadastrar Centro de Custo"),
		CADASTRAR_CLIENTE("Cadastrar Cliente"),
		CADASTRAR_DESPESA("Cadastrar Despesa"),
		CADASTRAR_EQUIPAMENTO("Cadastrar Equipamento"),
		CADASTRAR_FORNECEDORES("Cadastrar Fornecedores"),
		CADASTRAR_FREELANCER("Cadastrar Freelancer"),
		CADASTRAR_FUNCIONARIO ("Cadastrar Funcionários"),
		CADASTRAR_GRUPO ("Cadastrar Grupos"),
		CADASTRAR_LOCAL("Cadastrar Locais de Evento"),
		CADASTRAR_ORCAMENTO("Cadastrar Orçaamento"),
		CADASTRAR_OS ("Cadastrar Ordem de Serviço"),
		CADASTRAR_OS_DESCONTO ("Cadastrar Ordem de Serviço contendo desconto"),
		CADASTRAR_OS_DESCONTO_EQUIPAMENTO ("Cadastrar Ordem de Serviço contendo recursos abaixo do valor mínimo"),
		CADASTRAR_OS_PASSAGEM("Cadastrar OS de Passagem"),
		CADASTRAR_RECEITA("Cadastrar Receita"),
		CADASTRAR_RECEITA_DESCONTO("Cadastrar Receita com desconto"),
		CADASTRAR_SUBGRUPO("Cadastrar Recurso"),
		CADASTRAR_TIPO_USUARIO ("Cadastrar Tipos de Usuário"),
		CADASTRAR_UNIDADE("Cadastrar Unidade"),
		CADASTRAR_USUARIO ("Cadastrar Usuários do Sistema"),
		CANCELAR_PAGAMENTO_DESPESA("Permite realizar o cancelamento do pagamento de uma despesa"),
		CANCELAR_PAGAMENTO_RECEITA("Permite realizar o cancelamento do pagamento de uma receita"),
		CONTAGEM_ESTOQUE("Realiza a contagem do estoque"),
		CONTAS_A_RECEBER("Permite a visualização das contas a receber e o registro dos pagamentos"),
		COPIAR_CONTEUDO_TABELA("Permite copiar o conteúdo das tabelas para o excell"),
	
		
		DESCARTAR_EQUIPAMENTO("Descartar Equipamentos"),
		DEVOLVER_RECURSOS ("Registra a Devolução dos Recursos da Ordem de Serviço"),
		DEVOLVER_SUBLOCADOS ("Registra a Devolução dos Equipamentos Sublocados"),
		
		HISTORICO_ESTORNO("Consulta o histórico de ordens de serviço estornada"),
		HISTORICO_CANCELAMENTO("Consulta o de pagamentos cancelados"),
		
		IMPRIMIR_CLIENTE("Imprimir Cliente"),
		
		LISTAR_CLIENTES_VENDEDOR ("Listar os Clientes do Vendedor"),
		LISTAR_TODAS_OS("Listar todas as Ordens de Serviço"),
		LISTAR_TODOS_CLIENTES("Listar todos os Clientes"),
		LISTAR_TODOS_ORCAMENTOS("Listar os Orçamentos Cadastrados por Qualquer Funcionário"),
		LIXEIRA("Lixeira"),
		MANUTENCAO_PREVENTIVA("Manutenção Preventiva"),
		MANUTENCAO_CORRETIVA("Manutenção Corretiva"),
		RASTREAR_EQUIPAMENTOS("Rastrear Equipamentos"),
		RELATORIOS_ETIQUETAS("Imprimir as etiquetas dos equipamentos da empresa"),
		RELATORIOS_ETIQUETAS_SUBLOCADOS("Imprimir as etiquetas dos equipamentos sublocados"),
		RELATORIOS_GERENCIAIS("Relatórios gerenciais"),
		OS_EMERGENCIAL("Cadastro de OS Emergencial"),
		RELATORIOS_FINANCEIROS("Relatórios financeiros"),
		ALTERAR_CADASTRO_EQUIPAMENTO ("Permite alterar os dados dos equipamentos cadastrados"),
		ALTERAR_DATA_OS("Permite alterar as datas de uma Ordem de Serviço aprovada"),
		FINALIZAR_OS_EMERGENCIAL("Permite alterar o status da Ordem de Serviço Emergencial para FINALIZADA, excluindo assim o alerta do financeiro"),
		RETIRAR_OS("Permite realizar a rotina de retirada das Ordens de Serviço concluídas"),
		CADASTRAR_COMODATO("Permite cadastrar um registro de Comodato"),
		CADASTRAR_PAUTA("Permite cadastrar um registro de Pauta"),
		CADASTRAR_FONTE_PAGADORA("Permite cadastrar um registro de Fonte Pagadora"),
		RELATORIOS_VENDEDORES("Permite listar relatórios de todos os vendedores"),
		CADASTRAR_FORNECEDORES_TERCEIRIZADOS("Cadastrar Fornecedores de Recursos Terceirizados"),
		CADASTRAR_RECURSO_TERCEIRIZADO("Cadastrar Recursos Terceirizados"),
		RELATORIOS_VENDAS("Relatórios vendas"),
		REGISTRO_SUBLOCADOS("Relatórios de equipamentos sublocados"),
		LISTAR_TODAS_PAUTAS("Permite listar todas as pautas"),
		//ADICIONAR_EVENTOS("Permite adicionar eventos ao Google Calendar"),
		GERAR_CONTRATOS("Permite gerar os contratos das OS"),
		LISTAR_TODOS_CONTRATOS("Permite listar todos os contratos de OS") ;
		
		private String descricao;
		
		permission(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
}
