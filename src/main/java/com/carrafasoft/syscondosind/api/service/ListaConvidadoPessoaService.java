package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.ListaConvidadosPessoas;
import com.carrafasoft.syscondosind.api.repository.ListaConvidadoPessoaRepository;

@Service
public class ListaConvidadoPessoaService {
	
	@Autowired
	private ListaConvidadoPessoaRepository listaConvidadoPessoaRepository;
	
	public ListaConvidadosPessoas atualizarPessoaLista(Long codigo, ListaConvidadosPessoas lista) {
		
		ListaConvidadosPessoas listaSalva = listaConvidadoPessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		BeanUtils.copyProperties(lista, listaSalva, "listaConvidadoPessoaId");
		
		return listaConvidadoPessoaRepository.save(listaSalva);
	}

}
