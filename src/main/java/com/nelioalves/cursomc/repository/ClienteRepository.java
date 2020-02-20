package com.nelioalves.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nelioalves.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	
	@Transactional (readOnly = true)
	Cliente findByEmail(String email);
	// um dos recursos do Spring é padrão de nomes, se fasso um método que retorne um Cliente
	// comece com findBy e o nome do campo, o Spring já entende que é para fazer um select
}
