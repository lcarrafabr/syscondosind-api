package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Condominios;
import com.carrafasoft.syscondosind.api.repository.CondominioRepository;

@Service
public class CondominioService {
	
	@Autowired
	private CondominioRepository condominioRepository;
	
	
	public Condominios atualizarCondominio(Long codigo, Condominios condominio) {
		
		Condominios condominioSalvo = buscarCondominioPeloCodigo(codigo);
		
		condominio.setNomeCondomonio(condominio.getNomeCondomonio().toUpperCase());
		condominio.setEmail(condominio.getEmail().toLowerCase());
		
		BeanUtils.copyProperties(condominio, condominioSalvo, "condominioId");
		return condominioRepository.save(condominioSalvo);
	}

	public void atualizarStatusAtivo(Long codigo, Boolean ativo) {
		
		Condominios condominioSalvo = buscarCondominioPeloCodigo(codigo);
		condominioSalvo.setStatus(ativo);
		condominioRepository.save(condominioSalvo);
		
	}
	
	private Condominios buscarCondominioPeloCodigo(Long codigo) {
		Condominios condominioSalvo = condominioRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return condominioSalvo;
	}


}
