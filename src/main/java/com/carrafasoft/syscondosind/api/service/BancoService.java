package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Bancos;
import com.carrafasoft.syscondosind.api.repository.BancosRepository;

@Service
public class BancoService {
	
	@Autowired
	private BancosRepository bancosRepository;
	
	public Bancos atualizaBanco(Long codigo, Bancos banco) {
		
		Bancos bancoSalvo = buscaPorId(codigo);
		
		BeanUtils.copyProperties(banco, bancoSalvo, "bancoId");
		return bancosRepository.save(bancoSalvo);
	}
	
	public void atualizaStatusAtivo(Long codigo, Boolean ativo) {
		
		Bancos bancoSalvo = buscaPorId(codigo);
		bancoSalvo.setStatus(ativo);
		bancosRepository.save(bancoSalvo);
	}
	
	private Bancos buscaPorId(Long codigo) {
		
		Bancos bancoSalvo = bancosRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return bancoSalvo;
	}

}
