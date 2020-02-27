package com.nelioalves.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.security.UserSS;

@Service
public class UserService {

	// Retorna o usuário logado (autenticado) - usuario do Spring Security
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
