package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.CentroDeCustos;
import com.carrafasoft.syscondosind.api.repository.CentroCustoRepository;

@Service
public class CentroCustoService {
	
	@Autowired
	private CentroCustoRepository centroCustoRepository;
	
	public CentroDeCustos atualizarCentroCusto(Long codigo, CentroDeCustos centroCustos) {
		
		CentroDeCustos centroCustoSalvo = buscaPorId(codigo);
		BeanUtils.copyProperties(centroCustos, centroCustoSalvo, "centroCustoId");
		
		return centroCustoRepository.save(centroCustoSalvo);
		
	}
	
	public void atualizaStatusAtivo(Long codigo, Boolean ativo) {
		
		CentroDeCustos centroCustoSalvo = buscaPorId(codigo);
		centroCustoSalvo.setStatus(ativo);
		centroCustoRepository.save(centroCustoSalvo);
	}
	
	private CentroDeCustos buscaPorId(Long codigo) {
		
		CentroDeCustos centroCustoSalvo = centroCustoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return centroCustoSalvo;
	}

}
