package com.nelioalves.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	public static String decodeParam(String string) {
		try {
			return URLDecoder.decode(string, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			return "";
		}
	}

	public static List<Integer> decodeIntList(String categorias) {

		String[] vetor = categorias.split(",");
		List<Integer> listaInteger = new ArrayList<>();
		for (String s : vetor) {
			listaInteger.add(Integer.parseInt(s));
		}
		// Caso quisessemos trabalhar com lambda e diminuir nosso trabalho poderíamos
		// deiar op código desta forma
		// return Arrays.asList(categorias.split(",")).stream().map(x ->
		// Integer.parseInt(x)).collect(Collectors.toList());

		return listaInteger;
	}

}
