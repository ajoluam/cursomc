package com.nelioalves.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.nelioalves.cursomc.domain.Pedido;

//Queremos enviar email em duas situações, de um modo Mockado e de um modo Real
//As duas maneiras utilizarão os mesmos métodos, só mudando um pouco de implemtnação
//O padrão de Projeto Strategy soluciona o problema, pois trabalhar com a ideia de 
//criamos um interface que será implementada por outras  classes da maneira que a classe precise

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);

}
