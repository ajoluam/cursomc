package com.nelioalves.cursomc.domain.enums;

public enum Perfil {

	//O Spring Security exige o prefixo ROLE_
	ADMIN(1, "ROLE_ADMIN"), 
	CLIENTE(2, "ROLE_CLIENTE");

	private int cod;
	private String descricao;

	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Perfil toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		
		for (Perfil tipo : Perfil.values()) {
			if (id.equals(tipo.getCod())) {
					return tipo;
			}
		}
		
		throw new IllegalArgumentException("Id inválido " + id);
	}
}
