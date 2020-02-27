package com.nelioalves.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.repository.ClienteRepository;
import com.nelioalves.cursomc.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override // como nosso usuário será o email , o email deverá ser fornecido
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = clienteRepository.findByEmail(email);
		if (cli == null) {
			throw new UsernameNotFoundException(email);
		}
		// se o email pertencer a um usuário existente então retornaremos o UserDetail UserSS
		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}

}
