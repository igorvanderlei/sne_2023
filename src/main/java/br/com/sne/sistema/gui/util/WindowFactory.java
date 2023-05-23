package br.com.sne.sistema.gui.util;

import java.awt.Container;
import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.agendarrecolhimento.FormAgendarRecolhimento;
import br.com.sne.sistema.gui.centrocusto.FormCentroCusto;
import br.com.sne.sistema.gui.cliente.FormCliente;
import br.com.sne.sistema.gui.comodato.FormComodato;
import br.com.sne.sistema.gui.contagem.FormContagemEquipamento;
import br.com.sne.sistema.gui.contareceber.FormReceita;
import br.com.sne.sistema.gui.contrato.FormContrato;
import br.com.sne.sistema.gui.descarteequipamento.FormDescarteEquipamento;
import br.com.sne.sistema.gui.despesa.FormDespesa;
import br.com.sne.sistema.gui.devolucaoSublocado.FormDevolucaoSublocados;
import br.com.sne.sistema.gui.devolucaoequipamento.FormDevolverEquipamento;
import br.com.sne.sistema.gui.equipamento.FormEquipamento;
import br.com.sne.sistema.gui.equipamentoenviado.FormEnviarEquipamento;
import br.com.sne.sistema.gui.equipamentosublocado.FormEquipamentoSublocado;
import br.com.sne.sistema.gui.fontepagadora.FormFontePagadora;
import br.com.sne.sistema.gui.fornecedor.FormFornecedor;
import br.com.sne.sistema.gui.fornecedorterceirizado.FormFornecedorTerceirizado;
import br.com.sne.sistema.gui.freelancer.FormFreelancer;
import br.com.sne.sistema.gui.funcionario.FormFuncionario;
import br.com.sne.sistema.gui.grupo.FormGrupo;
import br.com.sne.sistema.gui.historico.FormHistoricoCancelamento;
import br.com.sne.sistema.gui.historico.FormHistoricoOSEstornada;
import br.com.sne.sistema.gui.lixeira.FormLixeiraCentroCusto;
import br.com.sne.sistema.gui.lixeira.FormLixeiraCliente;
import br.com.sne.sistema.gui.lixeira.FormLixeiraEquipamento;
import br.com.sne.sistema.gui.lixeira.FormLixeiraFornecedor;
import br.com.sne.sistema.gui.lixeira.FormLixeiraFornecedorTerceirizado;
import br.com.sne.sistema.gui.lixeira.FormLixeiraFreelancer;
import br.com.sne.sistema.gui.lixeira.FormLixeiraFuncionario;
import br.com.sne.sistema.gui.lixeira.FormLixeiraGrupo;
import br.com.sne.sistema.gui.lixeira.FormLixeiraOS;
import br.com.sne.sistema.gui.lixeira.FormLixeiraOrcamento;
import br.com.sne.sistema.gui.lixeira.FormLixeiraSubgrupo;
import br.com.sne.sistema.gui.local.FormLocal;
import br.com.sne.sistema.gui.main.InterfaceTelaPrincipal;
import br.com.sne.sistema.gui.main.TelaLogin;
import br.com.sne.sistema.gui.manutencaoCorretiva.FormManutencaoCorretiva;
import br.com.sne.sistema.gui.manutencaoPreventiva.FormManutencaoPreventiva;
import br.com.sne.sistema.gui.orcamento.FormOrcamento;
import br.com.sne.sistema.gui.os.FormOrdemServico;
import br.com.sne.sistema.gui.os.FormOrdemServicoEmergencia;
import br.com.sne.sistema.gui.os.FormOrdemServicoExtra;
import br.com.sne.sistema.gui.os.FormOrdemServicoSemEquipamento;
import br.com.sne.sistema.gui.osdepassagem.FormOsPassagem;
import br.com.sne.sistema.gui.pauta.FormPauta;
import br.com.sne.sistema.gui.rastreamento.FormRastreamento;
import br.com.sne.sistema.gui.receita.FormPagamentoOS;
import br.com.sne.sistema.gui.recurso.FormRecurso;
import br.com.sne.sistema.gui.recurso.FormRecursoTerceirizado;
import br.com.sne.sistema.gui.tipousuario.FormTipoUsuario;
import br.com.sne.sistema.gui.unidade.FormUnidade;
import br.com.sne.sistema.gui.usuario.FormUsuario;

public class WindowFactory {
	private static JInternalFrame telaCliente;
	private static JInternalFrame telaFuncionario;
	private static JInternalFrame telaFreelancer;
	private static JInternalFrame telaOS;
	private static JInternalFrame telaOSExtra;
	private static JInternalFrame telaOSSemEquipamento;
	private static JInternalFrame telaOSPassagem;
	private static JInternalFrame telaEquipamento;
	private static TelaLogin telaLogin;
	private static JInternalFrame telaDescricaoEquipamento;
	private static JInternalFrame telaRecursoTerceirizado;
	private static JInternalFrame telaEquipamentoEnviado;
	private static JInternalFrame telaUsuario;
	private static JInternalFrame telaPauta;
	private static JInternalFrame telaContrato;
	private static JInternalFrame telaGrupo;
	private static JInternalFrame telaTipoUsuario;
	private static JInternalFrame telaFornecedor;
	private static JInternalFrame telaFornecedorTerceirizado;
	private static JInternalFrame telaHistoricoCancelamento;
	private static JInternalFrame telaHistoricoEstorno;
	private static JInternalFrame telaOrcamento;
	private static JInternalFrame telaLocal;
	private static JInternalFrame telaDevolucao;
	private static JInternalFrame telaUnidade;
	private static JInternalFrame telaContagem;
	private static JInternalFrame telaRastreamento;
	private static JInternalFrame telaEquipamentoSublocado;
	private static JInternalFrame telaDevolucaoSublocado;
	private static JInternalFrame telaDescarteEquipamento;
	private static JInternalFrame telaManutencaoPreventiva;
	private static JInternalFrame telaManutencaoCorretiva;
	private static JInternalFrame telaCentroCusto;
	private static JInternalFrame telaCadastroDespesa;
	private static JInternalFrame telaCadastroFontePagadora;
	private static JInternalFrame telaCadastroReceita;
	private static JInternalFrame telaContaReceber;
	private static JInternalFrame telaOrdemServicoEmergencial;
	private static JInternalFrame telaAgendarRecolhimento;
	private static JInternalFrame telaAgendarRecolhimento2;
	
	private static JInternalFrame telaLixeiraCliente;
	private static JInternalFrame telaLixeiraCentroCusto;
	private static JInternalFrame telaLixeiraGrupo;
	private static JInternalFrame telaLixeiraSubgrupo;
	private static JInternalFrame telaLixeiraEquipamento;
	private static JInternalFrame telaLixeiraOS;
	private static JInternalFrame telaLixeiraOrcamento;
	private static JInternalFrame telaLixeiraFuncionario;
	private static JInternalFrame telaLixeiraFornecedor;
	private static JInternalFrame telaLixeiraFornecedorTerceirizado;
	private static JInternalFrame telaLixeiraFreelancer;
	private static JInternalFrame telaComodato;
	
	private static List<JInternalFrame> janelasAbertas = new ArrayList<JInternalFrame>();

	
	public static JInternalFrame createTelaCentroCusto(Container location) {
		if(telaCentroCusto == null || telaCentroCusto.isClosed()) {
			telaCentroCusto = new FormCentroCusto();
			janelasAbertas.add(telaCentroCusto);
			location.add(telaCentroCusto);
		}
		maximiza(telaCentroCusto);
		return telaCentroCusto;
	}
	
	public static JInternalFrame createTelaCadastroDespesa(Container location) {
		if(telaCadastroDespesa == null || telaCadastroDespesa.isClosed()) {
			telaCadastroDespesa = new FormDespesa();
			janelasAbertas.add(telaCadastroDespesa);
			location.add(telaCadastroDespesa);
		}
		maximiza(telaCadastroDespesa);
		return telaCadastroDespesa;
	}
	
	public static JInternalFrame createTelaCadastroFontePagadora(Container location) {
		if(telaCadastroFontePagadora == null || telaCadastroFontePagadora.isClosed()) {
			telaCadastroFontePagadora = new FormFontePagadora();
			janelasAbertas.add(telaCadastroFontePagadora);
			location.add(telaCadastroFontePagadora);
		}
		maximiza(telaCadastroFontePagadora);
		return telaCadastroFontePagadora;
	}
	
	public static JInternalFrame createTelaCadastroReceita(Container location) {
		if(telaCadastroReceita == null || telaCadastroReceita.isClosed()) {
			telaCadastroReceita = new FormPagamentoOS();
			janelasAbertas.add(telaCadastroReceita);
			location.add(telaCadastroReceita);
		}
		maximiza(telaCadastroReceita);
		return telaCadastroReceita;
	}	
	
	public static JInternalFrame createTelaContaReceber(Container location) {
		if(telaContaReceber == null || telaContaReceber.isClosed()) {
			telaContaReceber = new FormReceita();
			janelasAbertas.add(telaContaReceber);
			location.add(telaContaReceber);
		}
		maximiza(telaContaReceber);
		return telaContaReceber;
	}	
	
	public static JInternalFrame createTelaDevolucao(Container location) {
		if(telaDevolucao == null || telaDevolucao.isClosed()) {
			telaDevolucao = new FormDevolverEquipamento();
			janelasAbertas.add(telaDevolucao);
			location.add(telaDevolucao);
		}
		maximiza(telaDevolucao);
		return telaDevolucao;
	}
	
	public static JInternalFrame createTelaDescarte(Container location) {
		if(telaDescarteEquipamento == null || telaDescarteEquipamento.isClosed()) {
			telaDescarteEquipamento = new FormDescarteEquipamento();
			janelasAbertas.add(telaDescarteEquipamento);
			location.add(telaDescarteEquipamento);
		}
		maximiza(telaDescarteEquipamento);
		return telaDescarteEquipamento;
	}
	
	public static JInternalFrame createTelaManutencaoPreventiva(Container location) {
		if(telaManutencaoPreventiva == null || telaManutencaoPreventiva.isClosed()) {
			telaManutencaoPreventiva = new FormManutencaoPreventiva();
			janelasAbertas.add(telaManutencaoPreventiva);
			location.add(telaManutencaoPreventiva);
		}
		maximiza(telaManutencaoPreventiva);
		return telaManutencaoPreventiva;
	}
	
	public static JInternalFrame createTelaManutencaoCorretiva(Container location) {
		if(telaManutencaoCorretiva == null || telaManutencaoCorretiva.isClosed()) {
			telaManutencaoCorretiva = new FormManutencaoCorretiva();
			janelasAbertas.add(telaManutencaoCorretiva);
			location.add(telaManutencaoCorretiva);
		}
		maximiza(telaManutencaoCorretiva);
		return telaManutencaoCorretiva;
	}
	
	public static JInternalFrame createTelaOrdemServicoEmergencial(Container location) {
		if(telaOrdemServicoEmergencial == null || telaOrdemServicoEmergencial.isClosed()) {
			telaOrdemServicoEmergencial = new FormOrdemServicoEmergencia();
			janelasAbertas.add(telaOrdemServicoEmergencial);
			location.add(telaOrdemServicoEmergencial);
		}
		maximiza(telaOrdemServicoEmergencial);
		return telaOrdemServicoEmergencial;
	}
	
	public static JInternalFrame createTelaAgendarRecolhimento(Container location) {
		if(telaAgendarRecolhimento == null || telaAgendarRecolhimento.isClosed()) {
			telaAgendarRecolhimento = new FormAgendarRecolhimento();
			janelasAbertas.add(telaAgendarRecolhimento);
			location.add(telaAgendarRecolhimento);
		}
		maximiza(telaAgendarRecolhimento);
		return telaAgendarRecolhimento;
	}
	
/*	public static JInternalFrame createTelaAgendarRecolhimentoExportar(Container location) {
		if(telaAgendarRecolhimento2 == null || telaAgendarRecolhimento2.isClosed()) {
			telaAgendarRecolhimento2 = new FormAgendarRecolhimento2();
			janelasAbertas.add(telaAgendarRecolhimento2);
			location.add(telaAgendarRecolhimento2);
		}
		maximiza(telaAgendarRecolhimento2);
		return telaAgendarRecolhimento2;
	}*/
	
	public static JInternalFrame createTelaUnidade(Container location) {
		if(telaUnidade == null || telaUnidade.isClosed()) {
			telaUnidade = new FormUnidade();
			janelasAbertas.add(telaUnidade);
			location.add(telaUnidade);
		}
		maximiza(telaUnidade);
		return telaUnidade;
	}
	
	
	
	public static JInternalFrame createTelaOrcamento(Container location){
		if(telaOrcamento == null || telaOrcamento.isClosed()){
			telaOrcamento = new FormOrcamento();
			janelasAbertas.add(telaOrcamento);
			location.add(telaOrcamento);
		}
		maximiza(telaOrcamento);
		return telaOrcamento;
	}

	public static JInternalFrame createTelaLocal(Container location){
		if(telaLocal == null || telaLocal.isClosed()){
			telaLocal = new FormLocal();
			janelasAbertas.add(telaLocal);
			location.add(telaLocal);
		}
		maximiza(telaLocal);
		return telaLocal;
	}
	
	public static JInternalFrame createTelaPauta(Container location){
		if(telaPauta == null || telaPauta.isClosed()){
			telaPauta = new FormPauta();
			janelasAbertas.add(telaPauta);
			location.add(telaPauta);
		}
		maximiza(telaPauta);
		return telaPauta;
	}
	
	public static JInternalFrame createTelaContrato(Container location){
		if(telaContrato == null || telaContrato.isClosed()){
			telaContrato = new FormContrato();
			janelasAbertas.add(telaContrato);
			location.add(telaContrato);
		}
		maximiza(telaContrato);
		return telaContrato;
	}
	
	public static JInternalFrame createTelaUsuario(Container location){
		if(telaUsuario == null || telaUsuario.isClosed()){
			telaUsuario = new FormUsuario();
			janelasAbertas.add(telaUsuario);
			location.add(telaUsuario);
		}
		maximiza(telaUsuario);
		return telaUsuario;
		
	}
	
	
	
	public static JInternalFrame createTelaLixeiraGrupo(Container location){
		if(telaLixeiraGrupo == null || telaLixeiraGrupo.isClosed()){
			telaLixeiraGrupo = new FormLixeiraGrupo();
			janelasAbertas.add(telaLixeiraGrupo);
			location.add(telaLixeiraGrupo);
		}
		maximiza(telaLixeiraGrupo);
		return telaLixeiraGrupo;
		
	}
	
	
	public static JInternalFrame createTelaLixeiraSubgrupo(Container location){
		if(telaLixeiraSubgrupo == null || telaLixeiraSubgrupo.isClosed()){
			telaLixeiraSubgrupo = new FormLixeiraSubgrupo();
			janelasAbertas.add(telaLixeiraSubgrupo);
			location.add(telaLixeiraSubgrupo);
		}
		maximiza(telaLixeiraSubgrupo);
		return telaLixeiraSubgrupo;
		
	}
	
	public static JInternalFrame createTelaLixeiraEquipamento(Container location){
		if(telaLixeiraEquipamento == null || telaLixeiraEquipamento.isClosed()){
			telaLixeiraEquipamento = new FormLixeiraEquipamento();
			janelasAbertas.add(telaLixeiraEquipamento);
			location.add(telaLixeiraEquipamento);
		}
		maximiza(telaLixeiraEquipamento);
		return telaLixeiraEquipamento;
		
	}
	
	public static JInternalFrame createTelaLixeiraOS(Container location){
		if(telaLixeiraOS == null || telaLixeiraOS.isClosed()){
			telaLixeiraOS = new FormLixeiraOS();
			janelasAbertas.add(telaLixeiraOS);
			location.add(telaLixeiraOS);
		}
		maximiza(telaLixeiraOS);
		return telaLixeiraOS;
		
	}
	
	public static JInternalFrame createTelaLixeiraOrcamento(Container location){
		if(telaLixeiraOrcamento == null || telaLixeiraOrcamento.isClosed()){
			telaLixeiraOrcamento = new FormLixeiraOrcamento();
			janelasAbertas.add(telaLixeiraOrcamento);
			location.add(telaLixeiraOrcamento);
		}
		maximiza(telaLixeiraOrcamento);
		return telaLixeiraOrcamento;
		
	}
	
	public static JInternalFrame createTelaLixeiraCliente(Container location){
		if(telaLixeiraCliente == null || telaLixeiraCliente.isClosed()){
			telaLixeiraCliente = new FormLixeiraCliente();
			janelasAbertas.add(telaLixeiraCliente);
			location.add(telaLixeiraCliente);
		}
		maximiza(telaLixeiraCliente);
		return telaLixeiraCliente;
		
	}
	
	public static JInternalFrame createTelaLixeiraCentroCusto(Container location){
		if(telaLixeiraCentroCusto == null || telaLixeiraCentroCusto.isClosed()){
			telaLixeiraCentroCusto = new FormLixeiraCentroCusto();
			janelasAbertas.add(telaLixeiraCentroCusto);
			location.add(telaLixeiraCentroCusto);
		}
		maximiza(telaLixeiraCentroCusto);
		return telaLixeiraCentroCusto;
		
	}
	
	public static JInternalFrame createTelaLixeiraFuncionario(Container location){
		if(telaLixeiraFuncionario == null || telaLixeiraFuncionario.isClosed()){
			telaLixeiraFuncionario = new FormLixeiraFuncionario();
			janelasAbertas.add(telaLixeiraFuncionario);
			location.add(telaLixeiraFuncionario);
		}
		maximiza(telaLixeiraFuncionario);
		return telaLixeiraFuncionario;
	}
	
	public static JInternalFrame createTelaLixeiraFornecedor(Container location){
		if(telaLixeiraFornecedor == null || telaLixeiraFornecedor.isClosed()){
			telaLixeiraFornecedor = new FormLixeiraFornecedor();
			janelasAbertas.add(telaLixeiraFornecedor);
			location.add(telaLixeiraFornecedor);
		}
		maximiza(telaLixeiraFornecedor);
		return telaLixeiraFornecedor;
	}
	
	public static JInternalFrame createTelaLixeiraFornecedorTerceirizado(Container location){
		if(telaLixeiraFornecedorTerceirizado == null || telaLixeiraFornecedorTerceirizado.isClosed()){
			telaLixeiraFornecedorTerceirizado = new FormLixeiraFornecedorTerceirizado();
			janelasAbertas.add(telaLixeiraFornecedorTerceirizado);
			location.add(telaLixeiraFornecedorTerceirizado);
		}
		maximiza(telaLixeiraFornecedorTerceirizado);
		return telaLixeiraFornecedorTerceirizado;
	}
	
	public static JInternalFrame createTelaLixeiraFreelancer(Container location){
		if(telaLixeiraFreelancer == null || telaLixeiraFreelancer.isClosed()){
			telaLixeiraFreelancer = new FormLixeiraFreelancer();
			janelasAbertas.add(telaLixeiraFreelancer);
			location.add(telaLixeiraFreelancer);
		}
		maximiza(telaLixeiraFreelancer);
		return telaLixeiraFreelancer;
	}
	
	public static JInternalFrame createTelaTipoUsuario(Container location){
		if(telaTipoUsuario == null || telaTipoUsuario.isClosed()){
			telaTipoUsuario = new FormTipoUsuario();
			janelasAbertas.add(telaTipoUsuario);
			location.add(telaTipoUsuario);
		}
		maximiza(telaTipoUsuario);
		return telaTipoUsuario;
		
	}
	
	public static JInternalFrame createTelaFornecedor(Container location){
		if(telaFornecedor == null || telaFornecedor.isClosed()){
			telaFornecedor = new FormFornecedor();
			janelasAbertas.add(telaFornecedor);
			location.add(telaFornecedor);
		}
		maximiza(telaFornecedor);
		return telaFornecedor;
		
	}
	
	public static JInternalFrame createTelaFornecedorTerceirizado(Container location){
		if(telaFornecedorTerceirizado == null || telaFornecedorTerceirizado.isClosed()){
			telaFornecedorTerceirizado = new FormFornecedorTerceirizado();
			janelasAbertas.add(telaFornecedorTerceirizado);
			location.add(telaFornecedorTerceirizado);
		}
		maximiza(telaFornecedorTerceirizado);
		return telaFornecedorTerceirizado;
		
	}
	
	public static JInternalFrame createTelaHistoricoCancelamento(Container location){
		if(telaHistoricoCancelamento == null || telaHistoricoCancelamento.isClosed()){
			telaHistoricoCancelamento = new FormHistoricoCancelamento();
			janelasAbertas.add(telaHistoricoCancelamento);
			location.add(telaHistoricoCancelamento);
		}
		maximiza(telaHistoricoCancelamento);
		return telaHistoricoCancelamento;
		
	}
	
	public static JInternalFrame createTelaHistoricoEstorno(Container location){
		if(telaHistoricoEstorno == null || telaHistoricoEstorno.isClosed()){
			telaHistoricoEstorno = new FormHistoricoOSEstornada();
			janelasAbertas.add(telaHistoricoEstorno);
			location.add(telaHistoricoEstorno);
		}
		maximiza(telaHistoricoEstorno);
		return telaHistoricoEstorno;
		
	}
	

	public static JInternalFrame createTelaGrupo(Container location) {
		if(telaGrupo == null || telaGrupo.isClosed()) {
			telaGrupo = new FormGrupo();
			janelasAbertas.add(telaGrupo);
			location.add(telaGrupo);
		}
		maximiza(telaGrupo);
		return telaGrupo;
	}
	
	public static JInternalFrame createTelaCliente(Container location) {
		if(telaCliente == null || telaCliente.isClosed()) {
			telaCliente = new FormCliente();
			janelasAbertas.add(telaCliente);
			location.add(telaCliente);
		}
		maximiza(telaCliente);
		return telaCliente;
	}
	
	public static JInternalFrame createTelaFuncionario(Container location) {
		if(telaFuncionario == null || telaFuncionario.isClosed()) {
			telaFuncionario = new FormFuncionario();
			janelasAbertas.add(telaFuncionario);
			location.add(telaFuncionario);
		}
		maximiza(telaFuncionario);
		return telaFuncionario;
	}
	
	public static JInternalFrame createTelaFreelancer(Container location) {
		if(telaFreelancer == null || telaFreelancer.isClosed()) {
			
			telaFreelancer = new FormFreelancer();
			janelasAbertas.add(telaFreelancer);
			location.add(telaFreelancer);
		}
		maximiza(telaFreelancer);
		return telaFreelancer;
	}
	
	public static JInternalFrame createTelaOS(Container location) {
		if(telaOS == null || telaOS.isClosed()) {
			telaOS = new FormOrdemServico();
			janelasAbertas.add(telaOS);
			location.add(telaOS);
		}
		maximiza(telaOS);
		return telaOS;		
	}
	
	public static JInternalFrame createTelaOSExtra(Container location) {
		if(telaOSExtra == null || telaOSExtra.isClosed()) {
			telaOSExtra = new FormOrdemServicoExtra();
			janelasAbertas.add(telaOSExtra);
			location.add(telaOSExtra);
		}
		maximiza(telaOSExtra);
		return telaOSExtra;		
	}
	
	public static JInternalFrame createTelaOSSemEquipamento(Container location) {
		if(telaOSSemEquipamento == null || telaOSSemEquipamento.isClosed()) {
			telaOSSemEquipamento = new FormOrdemServicoSemEquipamento();
			janelasAbertas.add(telaOSSemEquipamento);
			location.add(telaOSSemEquipamento);
		}
		maximiza(telaOSSemEquipamento);
		return telaOSSemEquipamento;		
	}
	
	
	public static JInternalFrame createTelaComodato(Container location) {
		if(telaComodato == null || telaComodato.isClosed()) {
			telaComodato = new FormComodato();
			janelasAbertas.add(telaComodato);
			location.add(telaComodato);
		}
		maximiza(telaComodato);
		return telaComodato;		
	}
	
	
	public static JInternalFrame createTelaOSPassagem(Container location) {
		if(telaOSPassagem == null || telaOSPassagem.isClosed()) {
			telaOSPassagem = new FormOsPassagem();
			janelasAbertas.add(telaOSPassagem);
			location.add(telaOSPassagem);
		}
		maximiza(telaOSPassagem);
		return telaOSPassagem;		
	}
	
	public static JInternalFrame createTelaEquipamento(Container location) {
		if(telaEquipamento == null || telaEquipamento.isClosed()) {
			telaEquipamento = new FormEquipamento();
			janelasAbertas.add(telaEquipamento);
			location.add(telaEquipamento);
		}
		maximiza(telaEquipamento);
		return telaEquipamento;
	}
	
	public static JInternalFrame createTelaDescricaoEquipamento(Container location) {
		if(telaDescricaoEquipamento == null || telaDescricaoEquipamento.isClosed()) {
			telaDescricaoEquipamento = new FormRecurso();
			janelasAbertas.add(telaDescricaoEquipamento);
			location.add(telaDescricaoEquipamento);
		}
		maximiza(telaDescricaoEquipamento);
		return telaDescricaoEquipamento;		
	}
	
	public static JInternalFrame createTelaRecursoTerceirizado(Container location) {
		if(telaRecursoTerceirizado == null || telaRecursoTerceirizado.isClosed()) {
			telaRecursoTerceirizado = new FormRecursoTerceirizado();
			janelasAbertas.add(telaRecursoTerceirizado);
			location.add(telaRecursoTerceirizado);
		}
		maximiza(telaRecursoTerceirizado);
		return telaRecursoTerceirizado;		
	}
	
	public static JInternalFrame createTelaEquipamentoEnviado(Container location) {
		if(telaEquipamentoEnviado == null || telaEquipamentoEnviado.isClosed()) {
			telaEquipamentoEnviado = new FormEnviarEquipamento();
			janelasAbertas.add(telaEquipamentoEnviado);
			location.add(telaEquipamentoEnviado);
		}
		maximiza(telaEquipamentoEnviado);
		return telaEquipamentoEnviado;		
	}	
	
	public static JInternalFrame createTelaEquipamentoSublocado(Container location) {
		if(telaEquipamentoSublocado == null || telaEquipamentoSublocado.isClosed()) {
			telaEquipamentoSublocado = new FormEquipamentoSublocado();
			janelasAbertas.add(telaEquipamentoSublocado);
			location.add(telaEquipamentoSublocado);
		}
		maximiza(telaEquipamentoSublocado);
		return telaEquipamentoSublocado;		
	}
	
	public static JInternalFrame createTelaDevolucaoSublocados(Container location) {
		if(telaDevolucaoSublocado == null || telaDevolucaoSublocado.isClosed()) {
			telaDevolucaoSublocado = new FormDevolucaoSublocados();
			janelasAbertas.add(telaDevolucaoSublocado);
			location.add(telaDevolucaoSublocado);
		}
		maximiza(telaDevolucaoSublocado);
		return telaDevolucaoSublocado;		
	}
	
	
	
	public static JInternalFrame createTelaContagemEstoque(Container location) {
		if(telaContagem == null || telaContagem.isClosed()) {
			telaContagem = new FormContagemEquipamento();
			janelasAbertas.add(telaContagem);
			location.add(telaContagem);
		}
		maximiza(telaContagem);
		return telaContagem;		
	}	
	
	public static JInternalFrame createTelaRastreamento(Container location) {
		if(telaRastreamento == null || telaRastreamento.isClosed()) {
			telaRastreamento = new FormRastreamento();
			janelasAbertas.add(telaRastreamento);
			location.add(telaRastreamento);
		}
		maximiza(telaRastreamento);
		return telaRastreamento;		
	}
	
	public static TelaLogin createTelaLogin(InterfaceTelaPrincipal tela) {
		if(telaLogin == null) {
			telaLogin = new TelaLogin(tela);
		}
		return telaLogin;
	}
	

	
	private  static void maximiza(JInternalFrame window) {
		try {
			Dimension d = Facade.getInstance().getTelaPrincipal().getDesktop().getSize();
//			JOptionPane.showMessageDialog(null, d.toString());
		
			window.setBounds(0, 0, (int)d.getWidth(), (int)d.getHeight());
//			window.setMaximizable(true);
//			window.setMaximum(true);
//			window.setVisible(false);
			window.setSelected(true);
			window.setClosable(true);
//			window.setResizable(false);
//			window.setIconifiable(true);
			window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//	window.setMaximum(true);
			window.setVisible(true);

		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}	
		
	}
	
	public static void redimensionarJanelas(Dimension d) {
		for(JInternalFrame janela: janelasAbertas) {
			janela.setBounds(0, 0, (int)d.getWidth(), (int)d.getHeight());
		}
	}
	
	public static void fecharTodasJanelas() {
		for(JInternalFrame janela: janelasAbertas) {
			janela.setVisible(false);
			janela.dispose();
		}
		janelasAbertas=new ArrayList<JInternalFrame>();
	}
	
}
