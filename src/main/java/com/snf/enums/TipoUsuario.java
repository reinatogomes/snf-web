package com.snf.enums;

import java.util.List;

import com.snf.model.Role;
import com.snf.util.CollectionsUtils;

public enum TipoUsuario {
	GERENTE, CAIXA, FUNCIONARIO, INEXISTENTE;

	public static TipoUsuario getTipoByRoles(List<Role> roles) {
		if (CollectionsUtils.isNullOrEmpty(roles))
			return TipoUsuario.INEXISTENTE;
		for (Role role : roles) {
			switch (role.getTipoUsuario().getPermissao()) {
			case GERENTE:
				return TipoUsuario.GERENTE;
			case CAIXA:
				return TipoUsuario.CAIXA;
			case FUNCIONARIO:
				return TipoUsuario.FUNCIONARIO;
			}
		}
		return TipoUsuario.INEXISTENTE;
	}
}
