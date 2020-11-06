package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Portarias;
import com.carrafasoft.syscondosind.api.repository.PortariasRepository;

@Service
public class PortariaService {
	
	@Autowired
	private PortariasRepository portariasRepository;
	
	public Portarias atualizarPortaria(Long codigo, Portarias portaria) {
		
		Portarias portariaSalva = buscaPorId(codigo);
		
		BeanUtils.copyProperties(portaria, portariaSalva, "controlePortariaId");
		return portariasRepository.save(portariaSalva);
	}
	
	private Portarias buscaPorId(Long codigo) {
		
		Portarias portariaSalva = portariasRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return portariaSalva;
	}

}
