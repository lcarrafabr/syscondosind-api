package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.CategoriasFornecedores;
import com.carrafasoft.syscondosind.api.repository.CategoriasFornecedoresRepository;

@Service
public class CategoriasFornecedoresService {
	
	@Autowired
	private CategoriasFornecedoresRepository categoriasFornecedoresRepository;
	
	
	public CategoriasFornecedores atualizarCategoriaFornecedores(Long codigo, CategoriasFornecedores categoria) {
		
		CategoriasFornecedores categoriaSalva = buscaPorId(codigo);
		BeanUtils.copyProperties(categoria, categoriaSalva, "categoriaFornecedorId");
		
		return categoriasFornecedoresRepository.save(categoriaSalva);
	}
	
	public void atualizaStatusAtivo(Long codigo, Boolean ativo) {
		
		CategoriasFornecedores categoriaSalva = buscaPorId(codigo);		
		categoriaSalva.setStatusAtivo(ativo);
		categoriasFornecedoresRepository.save(categoriaSalva);
	}
	
	
	private CategoriasFornecedores buscaPorId(Long codigo) {
		
		CategoriasFornecedores categoriaSalva = categoriasFornecedoresRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return categoriaSalva;
	}

}
