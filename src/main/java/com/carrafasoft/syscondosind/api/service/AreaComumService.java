package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.AreasComuns;
import com.carrafasoft.syscondosind.api.repository.AreasComunsRepository;

@Service
public class AreaComumService {
	
	@Autowired
	private AreasComunsRepository areasComunsRepository;
	
	public AreasComuns atualizaAreaComum(Long codigo, AreasComuns areaComum) {
		
		areaComum.setNomeArea(areaComum.getNomeArea().toUpperCase());
		
		AreasComuns areaSalva = buscaPorId(codigo);
		BeanUtils.copyProperties(areaComum, areaSalva, "areaComumId");
		return areasComunsRepository.save(areaSalva);
	}
	
	public void atualizaLiberarParaAgendar(Long codigo, Boolean ativo) {
		
		AreasComuns areaSalva = buscaPorId(codigo);
		areaSalva.setLiberarParaAgendar(ativo);
		areasComunsRepository.save(areaSalva);
	}
	
	private AreasComuns buscaPorId(Long codigo) {
		
		AreasComuns areaSalva = areasComunsRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return areaSalva;
	}

}
