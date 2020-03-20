package com.nelioalves.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nelioalves.cursomc.domain.Estado;
import com.nelioalves.cursomc.repository.EstadoRepository;

public class EstadoService {
	@Autowired
	private EstadoRepository repo;
	
	public List<Estado> findAll() {
		return repo.findAllByOrderByNome();
	}
}
