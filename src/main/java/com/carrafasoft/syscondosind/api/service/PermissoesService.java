package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Permissoes;
import com.carrafasoft.syscondosind.api.repository.PermissoesRepository;

@Service
public class PermissoesService {
	
	@Autowired
	private PermissoesRepository permissoesRepository;
	
	public Permissoes atualizaPermissao(Long codigo, Permissoes permissao) {
		
		permissao.setPermissao(permissao.getPermissao().toUpperCase());
		
		Permissoes permissaoSalva = buscaPorId(codigo);
		
		BeanUtils.copyProperties(permissao, permissaoSalva, "permissaoId");
		return permissoesRepository.save(permissaoSalva);
	}
	
	private Permissoes buscaPorId(Long codigo) {
		
		Permissoes permissaoSalva = permissoesRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return permissaoSalva;
	}

}
