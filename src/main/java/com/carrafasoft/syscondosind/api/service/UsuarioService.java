package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.UsuarioSistema;
import com.carrafasoft.syscondosind.api.repository.UsuariosRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	
	public UsuarioSistema atualizaUsuario(Long codigo, UsuarioSistema usuario) {
		
		UsuarioSistema usuarioSalvo = buscaPorId(codigo);
		
		BeanUtils.copyProperties(usuario, usuarioSalvo, "usuarioSistemaId");
		return usuariosRepository.save(usuarioSalvo);
	}
	
	public void atualizaStatusAtivo(Long codigo, Boolean ativo) {
		
		UsuarioSistema usuarioSalvo = buscaPorId(codigo);
		usuarioSalvo.setStatus(ativo);
		usuariosRepository.save(usuarioSalvo);
	}
	
	private UsuarioSistema buscaPorId(Long codigo) {
		
		UsuarioSistema usuarioSalvo = usuariosRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return usuarioSalvo;
	}
	
	

}
