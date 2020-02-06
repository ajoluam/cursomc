package com.nelioalves.cursomc.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.dto.CategoriaDTO;
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
		// Caso a categoria não tenha o campo id preenchido será feito um save
		// Caso a categoria já tenha o campo id preenchido será feito um update
		categoria = categoriaRepository.save(categoria);

		return categoria;
	}

	public void delete(Integer id) {

		this.find(id);
		try {
			categoriaRepository.deleteById(id);
			// Ao invés de estourar a excessao do SpringData , vou lançar a minha excessão
			// tratada
		} catch (DataIntegrityViolationException ex) {
			throw new DataIntegrityViolationException("Não é possível excluir uma categoria que possui produtos.");

		}

	}

	public List<CategoriaDTO> findAll() {
		List<Categoria> lista = categoriaRepository.findAll();
		List<CategoriaDTO> listaDTO = lista.stream().map(c -> new CategoriaDTO(c)).collect(Collectors.toList());

		return listaDTO;

	}

}
