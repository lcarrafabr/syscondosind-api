package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Produtos;
import com.carrafasoft.syscondosind.api.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Produtos atualizarProduto(Long codigo, Produtos produto) {
		
		Produtos produtoSalvo = produtoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(produto, produtoSalvo, "produtoId");
		
		return produtoRepository.save(produtoSalvo);
	}

}
