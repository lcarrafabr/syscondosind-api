package com.carrafasoft.syscondosind.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.ConfigBoletos;
import com.carrafasoft.syscondosind.api.repository.ConfigBoletoRepository;

@Service
public class ConfigBoletoService {
	
	@Autowired
	private ConfigBoletoRepository configBoletoRepository;
	
	
	public ConfigBoletos atualizarConfig(ConfigBoletos config, Long codigo) {
		
		ConfigBoletos configSalva = buscaPorId(codigo);
		
		BeanUtils.copyProperties(config, configSalva, "configBoletoId");
		
		return configBoletoRepository.save(configSalva);
	}
	
	public ConfigBoletos cadastrarERetornarParaModeloBoleto(ConfigBoletos config) {
		
		ConfigBoletos configSalvo = configBoletoRepository.save(config);
		return configSalvo;
	}
	
	public ConfigBoletos buscaPorId(Long codigo) {
		
		ConfigBoletos configSalva = configBoletoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return configSalva;
	}

}
