package com.nelioalves.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nelioalves.cursomc.domain.Pedido;

@Repository //Para criar o Repositry da classe pai que já supre para os filhos
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
