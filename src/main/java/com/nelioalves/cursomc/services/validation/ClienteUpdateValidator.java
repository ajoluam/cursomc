package com.nelioalves.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.repository.ClienteRepository;
import com.nelioalves.cursomc.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(ClienteUpdate clienteUpdate) {
	}

	@Override
	// Esse método é o que vai validar o CPF ou CNPJ, agora o @Valid tb dependerá
	// dele
	public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		//Para ter acesso ao id passado na chamada do endpoint , vamos chamar os atributos que vieram na request
		//que deu origem a processamento
		Map<String, String > map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>(); // FieldMessage foi a classe que criamos só com código e a mensagem
													 // para capturar erro
		
		
		//Começo da lógica para validar email
		Cliente cli = clienteRepository.findByEmail(clienteDTO.getEmail());
		//Se encontrar algum cliente com o email em questão e  Id dessa pessoa for diferente do cliente a ser atualizado
		//entao estourar erro de email já existente
		if (cli != null && !cli.getId().equals(uriId)) list.add(new FieldMessage("email", "email já existente.")); 
		//Final da lógica para validar email
		
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
