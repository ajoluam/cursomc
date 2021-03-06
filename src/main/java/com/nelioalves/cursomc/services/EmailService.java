package com.nelioalves.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Pedido;

//Queremos enviar email em duas situações, de um modo Mockado e de um modo Real
//As duas maneiras utilizarão os mesmos métodos, só mudando um pouco de implemtnação
//O padrão de Projeto Strategy soluciona o problema, pois trabalhar com a ideia de 
//criamos um interface que será implementada por outras  classes da maneira que a classe precise

//Essa classe será nosso contrato , mostrando quais operações nosso serviço de email deve fornecer

public interface EmailService {

	// Para enviarmos nosso email no formato padrão , com texto plano
	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);

	// Para enviarmos nosso email em formato html
	void sendOrderConfirmationHtmlEmail(Pedido obj);

	void sendHtmlEmail(MimeMessage msg);

	void sendNewPasswordEmail(Cliente cliente, String newPass);

}
