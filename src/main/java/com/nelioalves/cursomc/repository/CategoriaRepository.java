package com.nelioalves.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nelioalves.cursomc.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

}
