package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.EnderecosEletronicos;
import com.carrafasoft.syscondosind.api.repository.EnderecoEletronicoRepository;

@Service
public class EnderecoEletronicoService {
	
	@Autowired
	private EnderecoEletronicoRepository enderecoEletronicoRepository;
	
	public EnderecosEletronicos atualizarEndEletronico(Long codigo, EnderecosEletronicos endEletronico) {
		
		EnderecosEletronicos endeletronicoSalvo = enderecoEletronicoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(endEletronico, endeletronicoSalvo, "endEletronicoId");
		
		return enderecoEletronicoRepository.save(endeletronicoSalvo);
	}

}
