package com.carrafasoft.syscondosind.api.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.enums.StatusOcorrencia;
import com.carrafasoft.syscondosind.api.model.LivroOcorrencia;
import com.carrafasoft.syscondosind.api.repository.LivroOcorrenciaRepository;

@Service
public class LivroOcorrenciaService {
	
	@Autowired
	private LivroOcorrenciaRepository livroOcorrenciaRepository;
	
	public LivroOcorrencia atualizaOcorrencia(Long codigo, LivroOcorrencia ocorrencia) {
		
		LivroOcorrencia ocorrenciaSalva = buscaPorId(codigo);
		
		if(ocorrencia.getStatusOcorrencia().equals(StatusOcorrencia.FECHADO) ||
				ocorrencia.getStatusOcorrencia().equals(StatusOcorrencia.CANCELADO)) {
			
			ocorrencia.setDataFechamento(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
		}
		
		BeanUtils.copyProperties(ocorrencia, ocorrenciaSalva, "ocorrencia_id");
		return livroOcorrenciaRepository.save(ocorrenciaSalva);
	}
	
	private LivroOcorrencia buscaPorId(Long codigo) {
		
		LivroOcorrencia ocorrenciaSalva = livroOcorrenciaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return ocorrenciaSalva;
	}

}
