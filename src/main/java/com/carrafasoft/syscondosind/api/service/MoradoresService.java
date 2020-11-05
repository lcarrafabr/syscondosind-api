package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Moradores;
import com.carrafasoft.syscondosind.api.repository.MoradorRepository;

@Service
public class MoradoresService {
	
	@Autowired
	private MoradorRepository moradorRepository;
	
	public Moradores atualizaMorador(Long codigo, Moradores morador) {
		
		Moradores moradorSalvo = buscaMoradorPorId(codigo);
		
		BeanUtils.copyProperties(morador, moradorSalvo, "moradorId");
		return moradorRepository.save(moradorSalvo);
	}
	
	private Moradores buscaMoradorPorId(Long codigo) {
		
		Moradores moradorSalvo = moradorRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return moradorSalvo;
	}

}
