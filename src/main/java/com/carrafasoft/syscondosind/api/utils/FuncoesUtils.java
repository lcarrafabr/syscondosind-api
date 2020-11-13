package com.carrafasoft.syscondosind.api.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FuncoesUtils {
	
	
	/**
	 * Função que converte datas de string para LocalDate
	 * Formato yyyy-MM-dd HH:mm:ss
	 @param LocalDate data
	  **/
	public static LocalDateTime converterStringParaLocalDateTime(String data) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(data, formatter);
		
		return dateTime;
	}
	
	
	/**
	 * Gera um sequencia de caracteres com 20 digitos aleatoriamente;
	 * 
	 @param LocalDate data
	 * @return 
	  **/
	public static String gerarHash() {
		
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		//System.out.println(myRandom.substring(0,20));
		return myRandom.substring(0,20);
	}

}
