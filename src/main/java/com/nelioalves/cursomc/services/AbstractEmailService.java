package com.nelioalves.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail()); // para quem será enviada a mensagem
		sm.setFrom(sender); // quem está enviandoa a mensagem
		sm.setSubject("Pedido confirmado! Código: " + obj.getId()); // qual o subject (assunto) do email
		sm.setSentDate(new Date(System.currentTimeMillis())); // data da mensagem
		sm.setText(obj.toString()); // qual o texto da mensagem
		return sm;
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}

	protected String htmlFromTemplatePedido(Pedido obj) {
		// Esse objeto context é do Thymefeaf, preciso dele para acessar o template que
		// criamos
		Context context = new Context();

		// Como o meu template utiliza algumas variaveis, preciso dizer quais valores
		// serão repassados
		// para estas variáveis aqui pelo context.setVariable;
		context.setVariable("pedido", obj);

		return templateEngine.process("email/confirmacaoPedido", context);

	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		// Vou tentar mandar o email html normalmente, casop dê algum erro eu tento
		// mandar da outra forma
		MimeMessage mm;
		try {
			mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {

		MimeMessage mm = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId()); // qual o subject (assunto) do email
		mmh.setSentDate(new Date(System.currentTimeMillis())); // data da mensagem
		mmh.setText(htmlFromTemplatePedido(obj), true); // qual o texto da mensagem - no nosso caso o html gerado

		return mm;

	}
}