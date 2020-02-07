package com.nelioalves.cursomc.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {

		Categoria obj = categoriaService.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Categoria categoria) {
// O @RequestBody faz com que automaticamente o Json enviado seja convertido para o objeto Categoria

		categoria = categoriaService.insert(categoria);

		// criação da URI
		// o método fromCurrentRequest pega o path até /categorias
		// o resto e para inclusão do Id através de /{id}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId())
				.toUri();

		// como boa prática , devemos passar o código adequado juntamente com a nova URI
		// do código criado
		return ResponseEntity.created(uri).build();

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody Categoria categoria) {

		categoria = categoriaService.update(categoria);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		categoriaService.delete(id);

		return ResponseEntity.noContent().build();

	}

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {

		List<CategoriaDTO> lista = categoriaService.findAll();

		return ResponseEntity.ok().body(lista);
	}

	@GetMapping(value = "/page")
	public ResponseEntity<Page<Categoria>> findPage(Integer page , Integer linesPerPage, String orderBy, String direction) {

		Page<Categoria> lista = categoriaService.findPage(page, linesPerPage, direction, orderBy);

		return ResponseEntity.ok().body(lista);
	}

	
}
