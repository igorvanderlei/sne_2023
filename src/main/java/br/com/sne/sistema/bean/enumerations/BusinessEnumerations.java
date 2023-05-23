package br.com.sne.sistema.bean.enumerations;

public class BusinessEnumerations {
	public enum StatusEquipamento {
		DISPONIVEL,
		LOCADO,
		MANUTENCAO_PREVENTIVA,
		MANUTENCAO_CORRETIVA,
		INATIVO,
		COMODATO;
	}
	
	public enum StatusOS {
		PENDENTE,
		APROVADA,
		EM_REALIZACAO,
		CONCLUIDA,
		RECUSADA,
		ESTORNADA,
		OS_EMERGENCIAL,
		OS_EMERGENCIAL_INICIADA,
		OS_EMERGENCIAL_CONCLUIDA,
		OS_SEM_EQUIPAMENTO,
		OS_SEM_EQUIPAMENTO_CONCLUIDA;
	}
	
	public enum StatusComodato {
		PENDENTE,
		INICIADO,
		CONCLUIDO;
	}
	
	public enum StatusCliente {
		PENDENTE,
		ATIVO,
		RECUSADO,
		INATIVO
	}
	
	public enum TipoRecurso {
		EQUIPAMENTO,
		CENOGRAFIA,
		TERCEIRIZADO,
		EQUIPE_TECNICA,
		LOGISTICA
	}
	
	public enum StatusPauta {
		ABERTO,
		CANCELADO,
		CONFIRMADO,
		FOLLOW_UP
	}
	
	public enum TipoConta {
		RECEITA,
		DESPESA
	}
	
	public enum TipoContrato {
		CONTRATO,
		TERMO_ADITIVO
	}
	
	public enum OpcaoPagamento {
		CARTAO,
		BOLETO,
		DEPOSITO
	}
	
	public enum SituacaoOrcamento {
		CANCELADO,
		FECHADO,
		ABERTO
	}
}
