package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.GaragemMorador;
import com.carrafasoft.syscondosind.api.repository.GaragemMoradorRepository;

@Service
public class GaragemMoradorService {
	
	@Autowired
	private GaragemMoradorRepository garagemMoradorRepository;
	
	public GaragemMorador atualizarGaragemMorador(Long codigo, GaragemMorador vagaMorador) {
		
		GaragemMorador vagaMoradorSalvo = buscaPorId(codigo);
		
		BeanUtils.copyProperties(vagaMorador, vagaMoradorSalvo, "garagemMoradorId");
		return garagemMoradorRepository.save(vagaMoradorSalvo);
	}
	
	private GaragemMorador buscaPorId(Long codigo) {
		
		GaragemMorador vagaSalva = garagemMoradorRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return vagaSalva;
	}

}
