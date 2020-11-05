package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Veiculos;
import com.carrafasoft.syscondosind.api.repository.VeiculosRepository;

@Service
public class VeiculoService {
	
	@Autowired
	private VeiculosRepository veiculosRepository;
	
	
	public Veiculos atualizarVeiculo(Long codigo, Veiculos veiculo) {
		
		Veiculos veiculoSalvo = buscaPorId(codigo);
		
		BeanUtils.copyProperties(veiculo, veiculoSalvo, "veiculoId");
		return veiculosRepository.save(veiculoSalvo);
	}
	
	private Veiculos buscaPorId(Long codigo) {
		
		Veiculos veiculoSalvo = veiculosRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return veiculoSalvo;
	}

}
