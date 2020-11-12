package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.ContasBancarias;
import com.carrafasoft.syscondosind.api.repository.ContaBancariaRepository;

@Service
public class ContasBancariasService {
	
	@Autowired
	private ContaBancariaRepository contaBancariaRepository;
	
	public ContasBancarias atualizaContabancaria(Long codigo, ContasBancarias conta) {
		
		ContasBancarias contaSalva = buscaPorId(codigo);
		
		BeanUtils.copyProperties(conta, contaSalva, "contaBancariaId");
		return contaBancariaRepository.save(contaSalva);
	}
	
	public void atualizaStatusAtivo(Long codigo, Boolean ativo) {
		
		ContasBancarias contaSalva = buscaPorId(codigo);
		contaSalva.setStatus(ativo);
		contaBancariaRepository.save(contaSalva);
	}
	
	private ContasBancarias buscaPorId(Long codigo) {
		
		ContasBancarias contaSalva = contaBancariaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return contaSalva;
	}

}
