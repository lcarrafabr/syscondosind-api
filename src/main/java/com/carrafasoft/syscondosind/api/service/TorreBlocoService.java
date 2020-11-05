package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.TorresBlocos;
import com.carrafasoft.syscondosind.api.repository.TorreBlocoRepository;

@Service
public class TorreBlocoService {
	
	@Autowired
	private TorreBlocoRepository torreBlocoRepository;
	
	public TorresBlocos atualizaBloco(Long codigo, TorresBlocos bloco) {
		
		TorresBlocos blocoSalvo = buscaBlocoPorId(codigo);
		
		bloco.setNomeBloco(bloco.getNomeBloco().toUpperCase());
		
		BeanUtils.copyProperties(bloco, blocoSalvo, "torreBlocoId");
		return torreBlocoRepository.save(blocoSalvo);
	}
	
	private TorresBlocos buscaBlocoPorId(Long codigo) {
		
		TorresBlocos blocoSalvo = torreBlocoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return  blocoSalvo;
	}

}
