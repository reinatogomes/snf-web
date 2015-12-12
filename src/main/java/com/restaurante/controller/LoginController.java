package com.restaurante.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.restaurante.VM.LoginVM;
import com.restaurante.library.Encripta;
import com.restaurante.model.Usuario;
import com.restaurante.service.UsuarioService;
import com.restaurante.util.MessagesUtils;

@Named
@ViewScoped
public class LoginController implements Serializable {
	
	private static final long serialVersionUID = -5093610007145542638L;

	@Inject
	private LoginVM loginVM;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private CommonsController commonsController;
	
	@Inject
	private Encripta encripta;
	
	public void logar() throws IOException {
		try{
			Usuario usuario = usuarioService.getUsuarioByLoginSenha(loginVM.getUsuario().getLogin(),
					encripta.encripta(loginVM.getUsuario().getSenha()));

			commonsController.setUsuarioLogado(usuario);
			commonsController.redirectHome();
		
		}catch(NoResultException e){
			MessagesUtils.exibirMensagemErro("mensagem.erro.login");
			
		}catch(PersistenceException e){
			MessagesUtils.exibirMensagemErro("mensagem.erro.salvar.registro");
		}
		

	}

	public LoginVM getLoginVM() {
		return loginVM;
	}

	public void setLoginVM(LoginVM loginVM) {
		this.loginVM = loginVM;
	}
	
	

}
