package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Beneficiario;
import com.carrafasoft.syscondosind.api.repository.BeneficiarioRepository;

@Service
public class BeneficiarioService {
	
	@Autowired
	private BeneficiarioRepository beneficiarioRepository;
	
	
	public Beneficiario atualizaBeneficiario(Beneficiario beneficiario, Long codigo) {
		
		Beneficiario beneficiarioSalvo = buscaPorId(codigo);
		BeanUtils.copyProperties(beneficiario, beneficiarioSalvo, "beneficiarioId");
		
		return beneficiarioRepository.save(beneficiarioSalvo);
	}
	
	private Beneficiario buscaPorId(Long codigo) {
		
		Beneficiario beneficiarioSalvo = beneficiarioRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return beneficiarioSalvo;
	}

}
