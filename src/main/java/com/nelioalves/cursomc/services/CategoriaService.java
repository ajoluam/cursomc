package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

	public Categoria update(Categoria dadosNovos) {

		// pesquisa no bannco pelo Id os dados que já existiam antes
		Categoria dadosAntigos = this.find(dadosNovos.getId());
		// Caso a cliente não tenha o campo id preenchido será feito um save
		// Caso a cliente já tenha o campo id preenchido será feito um update
		// o metodo abaixo atualiza o objeto que veio do banco para os dados novos que
		// queremos
		this.updateData(dadosAntigos, dadosNovos);
		dadosNovos = categoriaRepository.save(dadosAntigos);

		return dadosNovos;
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
		return lista.stream().map(c -> new CategoriaDTO(c)).collect(Collectors.toList());

	}

	// usando paginação para consulta de categorias
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO categoria) {

		return new Categoria(categoria.getId(), categoria.getNome());
	}
	
	private void updateData(Categoria dadosAntigos ,Categoria dadosNovos) {
		dadosAntigos.setNome(dadosNovos.getNome());
	}

}
