package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.ListaConvidados;
import com.carrafasoft.syscondosind.api.repository.ListaConvidadosRepository;

@Service
public class ListaConvidadosService {
	
	@Autowired
	private ListaConvidadosRepository listaConvidadosRepository;
	
	public ListaConvidados atualizaListaConvidados(Long codigo, ListaConvidados lista) {
		
		ListaConvidados listaSalva = listaConvidadosRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		BeanUtils.copyProperties(lista, listaSalva, "listaId");
		
		return listaConvidadosRepository.save(listaSalva);
	}

}
