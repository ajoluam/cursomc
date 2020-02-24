package com.nelioalves.cursomc.resources;


import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoService;

	@GetMapping(value= "/{id}")
	public ResponseEntity<Pedido> lista(@PathVariable Integer id) {
		
		Pedido obj = pedidoService.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	

	@PostMapping
	@Transactional
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido) {
// O @RequestBody faz com que automaticamente o Json enviado seja convertido para o objeto Categoria

		pedido = pedidoService.insert(pedido);

		// criação da URI
		// o método fromCurrentRequest pega o path até /categorias
		// o resto e para inclusão do Id através de /{id}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId())
				.toUri();

		// como boa prática , devemos passar o código adequado juntamente com a nova URI
		// do código criado
		return ResponseEntity.created(uri).build();

	}
}
