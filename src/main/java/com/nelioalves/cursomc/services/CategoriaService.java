package com.nelioalves.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.repository.CategoriaRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria find(Integer id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria categoria) {

		categoria = categoriaRepository.save(categoria);

		return categoria;
	}

	public Categoria update(Categoria categoria) {

		this.find(categoria.getId());
		//Caso a categoria não tenha o campo id preenchido será feito um save
		//Caso a categoria já tenha o campo id preenchido será feito um update
		categoria = categoriaRepository.save(categoria);

		return categoria;
	}

}
