package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.RepresentantesCondominio;
import com.carrafasoft.syscondosind.api.repository.RepresentanteCondRepository;

@Service
public class RepresentantesCondService {
	
	@Autowired
	private RepresentanteCondRepository representanteCondRepository;
	
	public RepresentantesCondominio atualizaRepresentante(Long codigo, RepresentantesCondominio representante) {
		
		RepresentantesCondominio representanteSalvo = buscaPorId(codigo);
		
		BeanUtils.copyProperties(representante, representanteSalvo, "representanteCondominioId");
		return representanteCondRepository.save(representanteSalvo);
	}
	
	private RepresentantesCondominio buscaPorId(Long codigo) {
		
		RepresentantesCondominio representanteSalvo = representanteCondRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return representanteSalvo;
	}

}
