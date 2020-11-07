package com.carrafasoft.syscondosind.api.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

}
