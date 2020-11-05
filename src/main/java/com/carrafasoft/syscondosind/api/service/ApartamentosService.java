package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Apartamentos;
import com.carrafasoft.syscondosind.api.repository.ApartamentoRepository;

@Service
public class ApartamentosService {
	
	@Autowired
	private ApartamentoRepository apartamentoRepository;
	
	public Apartamentos atualizaApartamento(Long codigo, Apartamentos apartamentos) {
		
		apartamentos.setNomeApartamento(apartamentos.getNomeApartamento().toUpperCase());
		
		Apartamentos apSalvo = buscaApPorId(codigo);
		
		BeanUtils.copyProperties(apartamentos, apSalvo, "apartamentoId");
		return apartamentoRepository.save(apSalvo);
	}
	
	private Apartamentos buscaApPorId(Long codigo) {
		
		Apartamentos apSalvo = apartamentoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return apSalvo;
	}

}
