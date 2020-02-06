package com.nelioalves.cursomc.dto;

import java.io.Serializable;

import com.nelioalves.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;

	public CategoriaDTO() {
	}

	//Para falicitar a conversao de Categoria para CategoriaDTO, vamos criar um construtor para isso
	public CategoriaDTO (Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}
	
	
	public CategoriaDTO(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
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

}
