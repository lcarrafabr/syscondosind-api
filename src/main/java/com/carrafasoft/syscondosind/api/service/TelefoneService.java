package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Telefones;
import com.carrafasoft.syscondosind.api.repository.TelefoneRepository;

@Service
public class TelefoneService {
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	
	public Telefones atualizaTelefone(Long codigo, Telefones telefone) {
		
		Telefones telefoneSalvo = telefoneRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(telefone, telefoneSalvo, "telefoneId");
		
		return telefoneRepository.save(telefoneSalvo);
	}

}
