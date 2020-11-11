package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.SetoresGaragem;
import com.carrafasoft.syscondosind.api.repository.SetorGaragemRepository;

@Service
public class SetorGaragemService {
	
	@Autowired
	private SetorGaragemRepository setorGaragemRepository;
	
	public SetoresGaragem atualizaSetorGaragem(Long codigo, SetoresGaragem setor) {
		
		setor.setNomeSetor(setor.getNomeSetor().toUpperCase());
		
		SetoresGaragem setorSalvo = buscaPorId(codigo);
		
		BeanUtils.copyProperties(setor, setorSalvo, "setorGaragemId");
		return setorGaragemRepository.save(setorSalvo);
	}
	
	private SetoresGaragem buscaPorId(Long codigo) {
		
		SetoresGaragem setorSalvo = setorGaragemRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return setorSalvo;
	}

}
