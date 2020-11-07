package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Portaria;
import com.carrafasoft.syscondosind.api.repository.PortariasRepository;

@Service
public class PortariaService {
	
	@Autowired
	private PortariasRepository portariasRepository;
	
	public Portaria atualizarPortaria(Long codigo, Portaria portaria) {
		
		Portaria portariaSalva = buscaPorId(codigo);
		
		BeanUtils.copyProperties(portaria, portariaSalva, "controlePortariaId");
		return portariasRepository.save(portariaSalva);
	}
	
	private Portaria buscaPorId(Long codigo) {
		
		Portaria portariaSalva = portariasRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return portariaSalva;
	}

}
