package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.UsuarioPermissao;
import com.carrafasoft.syscondosind.api.repository.UsuarioPermissaoRepository;

@Service
public class UsuarioPermissaoService {
	
	private UsuarioPermissaoRepository usuarioPermissaoRepository;
	
	public UsuarioPermissao atualizausuarioPermissao(Long codigo, UsuarioPermissao usuarioPermissao) {
		
		System.out.println("Codigo: " + codigo);
		
		UsuarioPermissao userPermitSalvo = buscaPorId(codigo);
		
		BeanUtils.copyProperties(usuarioPermissao, userPermitSalvo, "usuarioPermissao");
		return usuarioPermissaoRepository.save(userPermitSalvo);
	}
	
	private UsuarioPermissao buscaPorId(Long codigo) {
		
		//UsuarioPermissao userPermitSalvo = usuarioPermissaoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		UsuarioPermissao userPermitSalvo = (UsuarioPermissao) usuarioPermissaoRepository.buscaUsuarioPermissaoPorId(codigo);
		return userPermitSalvo;
	}

}
