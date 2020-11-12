package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Pessoas;
import com.carrafasoft.syscondosind.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	
	public Pessoas atualizaPessoa(Long codigo, Pessoas pessoas) {
		
		Pessoas pessoaSalva = buscaPessoaPeloCodigo(codigo);
		
		BeanUtils.copyProperties(pessoas, pessoaSalva, "pessoaId");
		return pessoaRepository.save(pessoaSalva);
	}
	
	public void atualizaStatusAtivo(Long codigo, Boolean ativo) {
		
		Pessoas pessoaSalva = buscaPessoaPeloCodigo(codigo);
		pessoaSalva.setStatus(ativo);
		pessoaRepository.save(pessoaSalva);
	}
	
	private Pessoas buscaPessoaPeloCodigo(Long codigo) {
		
		Pessoas pessoaSalva = pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return pessoaSalva;
	}
	
	
	
	

}
