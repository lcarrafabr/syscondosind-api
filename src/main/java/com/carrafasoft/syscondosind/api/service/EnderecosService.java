package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Enderecos;
import com.carrafasoft.syscondosind.api.repository.EnderecoRepository;

@Service
public class EnderecosService {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	public Enderecos atualizaEndereco(Enderecos endereco, Long codigo) {
		
		Enderecos enderecoSalvo = buscaPorId(codigo);
		
		BeanUtils.copyProperties(endereco, enderecoSalvo, "enderecoId");
		return enderecoRepository.save(enderecoSalvo);
	}
	
	
	private Enderecos buscaPorId(Long codigo) {
		
		Enderecos enderecoSalvo = enderecoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		return enderecoSalvo;
	}

}
