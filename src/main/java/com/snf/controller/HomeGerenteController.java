package com.snf.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.snf.model.Caixa;
import com.snf.service.CaixaService;
import com.snf.util.MessagesUtils;
import com.snf.vm.AberturaCaixaVM;

@Named
@ViewScoped
public class HomeGerenteController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	static final Logger log = Logger.getLogger(HomeGerenteController.class);

	@Inject
	private CaixaService caixaService;
	
	@Inject
	private AberturaCaixaVM aberturaCaixaVM;
	
	@PostConstruct
	public void init() {
		aberturaCaixaVM.setCaixas(caixaService.getAllOrderByDataAbertura());
		if(caixaService.getCaixaAberto()!=null){
			aberturaCaixaVM.setCaixa(caixaService.getCaixaAberto());
			aberturaCaixaVM.setExisteCaixaAberto(true);
		} else {
			aberturaCaixaVM.setCaixa(new Caixa());
			aberturaCaixaVM.setExisteCaixaAberto(false);
		}
		
	}
	
	public void salvarCaixa() {
		try{
			Caixa caixa = aberturaCaixaVM.getCaixa();
			caixaService.abrirCaixa(caixa);
			init();
			MessagesUtils.exibirMensagemSucesso("mensagem.sucesso.salvar.registro");
		} catch (Exception e){
			log.error(e.toString());
			MessagesUtils.exibirMensagemErro("mensagem.erro.salvar.registro");
		}
	}
	
	public void fecharCaixa() {
		try{
			Caixa caixa = aberturaCaixaVM.getCaixa();
			caixaService.fecharCaixa(caixa);
			init();
			MessagesUtils.exibirMensagemSucesso("mensagem.sucesso.salvar.registro");
		} catch (Exception e){
			log.error(e.toString());
			MessagesUtils.exibirMensagemErro("mensagem.erro.salvar.registro");
		}
	}

	public AberturaCaixaVM getAberturaCaixaVM() {
		return aberturaCaixaVM;
	}

	public void setAberturaCaixaVM(AberturaCaixaVM aberturaCaixaVM) {
		this.aberturaCaixaVM = aberturaCaixaVM;
	}

}
