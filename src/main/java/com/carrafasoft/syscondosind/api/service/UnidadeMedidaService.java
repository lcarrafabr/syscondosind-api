package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.UnidadeDeMedidas;
import com.carrafasoft.syscondosind.api.repository.UnidadeDeMedidaRepository;

@Service
public class UnidadeMedidaService {
	
	@Autowired
	private UnidadeDeMedidaRepository unidadeDeMedidaRepository;
	
	public UnidadeDeMedidas atualizarUnidadeMedida(Long codigo, UnidadeDeMedidas unidadeMedida) {
		
		UnidadeDeMedidas unidadeMedidaSalva = unidadeDeMedidaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(unidadeMedida, unidadeMedidaSalva, "unidadeDeMedidaId");
		
		return unidadeDeMedidaRepository.save(unidadeMedidaSalva);
	}

}
