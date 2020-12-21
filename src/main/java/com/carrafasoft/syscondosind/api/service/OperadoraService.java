package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Operadoras;
import com.carrafasoft.syscondosind.api.repository.OperadoraRepository;

@Service
public class OperadoraService {
	
	@Autowired
	private OperadoraRepository operadoraRepository;
	
	
	public Operadoras atualizaOperadora(Operadoras operadora, Long codigo) {
		
		Operadoras operadoraSalva = buscaPorId(codigo);
		BeanUtils.copyProperties(operadora, operadoraSalva, "operadoraId");
		
		return operadoraRepository.save(operadoraSalva);
	}
	
	private Operadoras buscaPorId(Long codigo) {
		
		Operadoras operadoraSalva = operadoraRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return operadoraSalva;
	}

}
