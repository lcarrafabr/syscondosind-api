package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.CategoriasContaPR;
import com.carrafasoft.syscondosind.api.repository.CategoriaContaPRRepository;

@Service
public class CategoriaContasPRService {
	
	@Autowired
	private CategoriaContaPRRepository categoriaContaPRRepository;
	
	public CategoriasContaPR atualizaCategoria(Long codigo, CategoriasContaPR categoria) {
		
		categoria.setNomeCategoriaConta(categoria.getNomeCategoriaConta().toUpperCase());
		
		CategoriasContaPR categoriaSalva = busacaPorId(codigo);
		
		BeanUtils.copyProperties(categoria, categoriaSalva, "categoriaContaId");
		
		return categoriaContaPRRepository.save(categoriaSalva);
	}
	
	public void atualizaStatusAtivo(Long codigo, Boolean ativo) {
		
		CategoriasContaPR categoriaSalva = busacaPorId(codigo);
		categoriaSalva.setStatus(ativo);
		categoriaContaPRRepository.save(categoriaSalva);
	}
	
	private CategoriasContaPR busacaPorId(Long codigo) {
		
		CategoriasContaPR categoriaSalva = categoriaContaPRRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return categoriaSalva;
	}

}
