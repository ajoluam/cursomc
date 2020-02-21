package com.nelioalves.cursomc.dto;

import java.io.Serializable;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "nome não pode ser vazio.")
	@Length(min = 5, max = 120, message = "nome deve ter entre 5 e 120 caracteres.")
	private String nome;
	
	@NotEmpty(message = "email não pode ser vazio.")
	@Email(message = "email inválido.")
	private String email;
	
	
	public ClienteDTO() {
		
	}
	
	public ClienteDTO(Integer id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
	}
	
	public ClienteDTO (Cliente cliente) {
		id = cliente.getId();
		nome = cliente.getNome();
		email = cliente.getEmail();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
