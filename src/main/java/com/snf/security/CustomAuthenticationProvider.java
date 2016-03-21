package com.snf.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.snf.library.Encripta;
import com.snf.model.Usuario;
import com.snf.service.CustomUserService;
import com.snf.util.MessagesUtils;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private Encripta enc;
	
	@Autowired
	private CustomUserService customUserService;
	
	public CustomAuthenticationProvider() {
		super();
	}
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String username = auth.getName();
		String password = (String) auth.getCredentials();
		
		try{
			Usuario usuario = (Usuario) customUserService.loadUserByUsername(username);
			
			if(usuario.getSenha().equals(enc.encripta(password))){
				Collection<GrantedAuthority> grantedAuths = new ArrayList<>();
				grantedAuths.addAll(usuario.getAuthorities());
				auth = getAuth(usuario);
			}else{
				throw new BadCredentialsException(MessagesUtils.getMessage("mensagem.erro.login"));
			}
			
		}catch(NoResultException e){
			throw new BadCredentialsException(MessagesUtils.getMessage("mensagem.nenhum.registro.encontrado"));
		}catch(PersistenceException persistenceException){
			throw new BadCredentialsException(MessagesUtils.getMessage("mensagem.erro.conexao.login"));
		}
		
		return auth ;
	}
	
	private Authentication getAuth(Usuario usuario) {
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(), usuario.getRoles());
		auth.setDetails(usuario);
		return auth;
	}

	@Override
	public boolean supports(Class<?> arg0) {
	        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(arg0)
	                && arg0.equals(UsernamePasswordAuthenticationToken.class);
	}

}
