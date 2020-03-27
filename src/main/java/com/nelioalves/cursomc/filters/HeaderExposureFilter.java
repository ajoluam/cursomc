package com.nelioalves.cursomc.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;


/*
 * Quando fazemos qualquer requisição, como por exemplo um PUT incluindo um
 * novo recurso, em nossa resposta é apresentado um LOCATION, que nada mais 
 * é do que o endereço desse novo cara que foio criado, mas isso tá ainda
 * implicito no meu BACKEND , o FRONTEND não ira encontrar essa informação.
 * Esse FILTER é para justamente tornar visível essa informação pao o FRONT 
 * 
 * Esse FILTER pega a requisição, expoê o LOCATION no response e depois
 * encaminha a requisição para o seu ciclo de vida normal
 */

@Component
public class HeaderExposureFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		//O objeto ServletResponse não tem o metodo addHeader, por isso 
		//precisamos transformar o meu response em um objeto que suporte 
		//o método que queremos , no caso o HttpServletResponse
		//Como minha aplicação é REST , com certeza esse CAST não dará problema
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("access-control-expose-headers", "location");
		
		//Encaminha para o ciclo de vida normal, segue o fluxo
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
