package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Fornecedores;
import com.carrafasoft.syscondosind.api.repository.FornecedorRepository;

@Service
public class FornecedoresService {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	
	public Fornecedores atualizarFornecedor(Long codigo, Fornecedores fornecedor) {
		
		Fornecedores fornecedorSalvo = buscaPorId(codigo);
		BeanUtils.copyProperties(fornecedor, fornecedorSalvo, "fornecedorId");
		
		return fornecedorRepository.save(fornecedorSalvo);
	}
	
	public void atualizaStatusAtivo(Long codigo, Boolean ativo) {
		
		Fornecedores fornecedorSalvo = buscaPorId(codigo);
		fornecedorSalvo.setStatus(ativo);
		fornecedorRepository.save(fornecedorSalvo);
	}
	
	private Fornecedores buscaPorId(Long codigo) {
		
		Fornecedores fornecedorSalvo = fornecedorRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return fornecedorSalvo;
	}

}
