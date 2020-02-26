package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.repository.CidadeRepository;
import com.nelioalves.cursomc.repository.ClienteRepository;
import com.nelioalves.cursomc.repository.EnderecoRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	

	public Cliente find(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente update(Cliente dadosNovos) {

		// pesquisa no bannco pelo Id os dados que já existiam antes
		Cliente dadosAntigos = this.find(dadosNovos.getId());
		// Caso a cliente não tenha o campo id preenchido será feito um save
		// Caso a cliente já tenha o campo id preenchido será feito um update
		// o metodo abaixo atualiza o objeto que veio do banco para os dados novos que
		// queremos
		this.updateData(dadosAntigos, dadosNovos);
		dadosNovos = clienteRepository.save(dadosAntigos);

		return dadosNovos;
	}

	public void delete(Integer id) {

		this.find(id);
		try {
			clienteRepository.deleteById(id);
			// Ao invés de estourar a excessao do SpringData , vou lançar a minha excessão
			// tratada
		} catch (DataIntegrityViolationException ex) {
			throw new DataIntegrityViolationException(
					"Não é possível excluir uma cliente que possui entidades relacionadas.");

		}

	}

	public List<ClienteDTO> findAll() {
		List<Cliente> lista = clienteRepository.findAll();
		List<ClienteDTO> listaDTO = lista.stream().map(c -> new ClienteDTO(c)).collect(Collectors.toList());

		return listaDTO;

	}

	// usando paginação para consulta de clientes
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO clienteDTO) {

		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
	}

	private void updateData(Cliente dadosAntigos, Cliente dadosNovos) {

		dadosAntigos.setNome(dadosNovos.getNome());
		dadosAntigos.setEmail(dadosNovos.getEmail());
	}

	public Cliente insert(Cliente cliente) {

		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());

		return cliente;
	}

	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		Cliente cliente = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(),
				clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipo()), pe.encode(clienteNewDTO.getSenha()));
		Optional<Cidade> cid = cidadeRepository.findById(clienteNewDTO.getCidadeId());
		Endereco end = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(),
				clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cid.get(), cliente);
		cliente.getEnderecos().add(end);
		cliente.getTelefones().add(clienteNewDTO.getTelefone1());
		if (clienteNewDTO.getTelefone2() != null) cliente.getTelefones().add(clienteNewDTO.getTelefone2());
		if (clienteNewDTO.getTelefone3() != null) cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		
		return cliente;
	}

}
