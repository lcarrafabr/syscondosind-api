package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.VagasGaragem;
import com.carrafasoft.syscondosind.api.repository.VagaGaragemRepository;

@Service
public class VagaGaragemService {
	
	@Autowired
	private VagaGaragemRepository vagaGaragemRepository;
	
	public VagasGaragem atualizaVaga(Long codigo, VagasGaragem vaga) {
		
		vaga.setNomeVaga(vaga.getNomeVaga().toUpperCase());
		
		VagasGaragem vagaSalva = buscaPorId(codigo);
		
		BeanUtils.copyProperties(vaga, vagaSalva, "vagaGaragemId");
		return vagaGaragemRepository.save(vagaSalva);
	}
	
	private VagasGaragem buscaPorId(Long codigo) {
		
		VagasGaragem vagaSalva = vagaGaragemRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return vagaSalva;
	}

}
