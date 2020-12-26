package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.PessoaListaCompras;
import com.carrafasoft.syscondosind.api.repository.PessoaListaCompraRepository;

@Service
public class PessoaListaCompraService {
	
	@Autowired
	private PessoaListaCompraRepository pessoaListaCompraRepository;
	
	
	public PessoaListaCompras atualizarPessoaListaCompra(Long codigo, PessoaListaCompras pessoaList) {
		
		PessoaListaCompras pessoaListSalva = buscaPorId(codigo);
		BeanUtils.copyProperties(pessoaList, pessoaListSalva, "pessoaListaCompraID");
		
		return pessoaListaCompraRepository.save(pessoaListSalva);
	}
	
	public void atualizaStatusAtivoPermiteAprovar(Long codigo, Boolean ativo) {
		
		PessoaListaCompras pessoaListSalva = buscaPorId(codigo);
		pessoaListSalva.setPermiteAprovar(ativo);
		pessoaListaCompraRepository.save(pessoaListSalva);
		
	}
	
	public void atualizaStatusAtivoPermiteCriarSolicitacao(Long codigo, Boolean ativo) {
		
		PessoaListaCompras pessoaListSalva = buscaPorId(codigo);
		pessoaListSalva.setPermiteCriarSolicitacao(ativo);
		pessoaListaCompraRepository.save(pessoaListSalva);
	}
	
	private PessoaListaCompras buscaPorId(Long codigo) {
		
		PessoaListaCompras pessoaListSalva = pessoaListaCompraRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return pessoaListSalva;
	}

}
