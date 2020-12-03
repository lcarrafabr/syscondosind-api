package com.carrafasoft.syscondosind.api.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.ConfigBoletos;
import com.carrafasoft.syscondosind.api.model.ModeloBoletos;
import com.carrafasoft.syscondosind.api.repository.ModelosBoletoRepository;

@Service
public class ModeloBoletoService {
	
	@Autowired
	private ModelosBoletoRepository modelosBoletoRepository;
	
	@Autowired
	private ConfigBoletoService configService;
	
	
	public ModeloBoletos cadastrarModeloBoleto(ModeloBoletos modelo, HttpServletResponse response) {
		
		ConfigBoletos configSalvo = new ConfigBoletos();
		
		configSalvo = cadastraeERetornarCodigoConfigBoleto(modelo);
		modelo.setConfigBoletos(configSalvo);
			
		return modelo;
	}
	
	public ModeloBoletos atualizarModeloBoleto(ModeloBoletos modelo, Long codigo) {
		
		ModeloBoletos modeloSalvo = buscaPorId(codigo);
		BeanUtils.copyProperties(modelo, modeloSalvo, "modeloBoletoId");
		
		return modelosBoletoRepository.save(modeloSalvo);
	}
	
	
	
	private ModeloBoletos buscaPorId(Long codigo) {
		
		ModeloBoletos modeloSalvo = modelosBoletoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return modeloSalvo;
	}
	
	
	private ConfigBoletos cadastraeERetornarCodigoConfigBoleto(ModeloBoletos modelo) {
		
		ConfigBoletos configSalvo = new ConfigBoletos();
		ConfigBoletos retornoConfig = new ConfigBoletos();
		
		configSalvo = modelo.getConfigBoletos();
		configSalvo.setCondominio(modelo.getCondominio());
		
		retornoConfig = configService.cadastrarERetornarParaModeloBoleto(configSalvo);
		
		return retornoConfig;
	}

}
