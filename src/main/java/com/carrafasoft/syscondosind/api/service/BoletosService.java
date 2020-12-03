package com.carrafasoft.syscondosind.api.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.Boletos;
import com.carrafasoft.syscondosind.api.model.ConfigBoletos;
import com.carrafasoft.syscondosind.api.model.ModeloBoletos;
import com.carrafasoft.syscondosind.api.repository.BoletosRepository;

@Service
public class BoletosService {
	
	@Autowired
	private BoletosRepository boletosRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ConfigBoletoService configBoletoService;
	
	@Autowired
	private ModeloBoletoService modeloBoletoService;
	
	
	public ResponseEntity<Boletos> cadastrarBoleto(Boletos boleto, HttpServletResponse response) {
		
		Boletos novoBoleto = new Boletos();
		ConfigBoletos configAtual = dadosConfigBoleto(boleto);
		
		Long ultimoNumeroDocConfigBoleto = configAtual.getUltimoNumeroDoc();
		
		Long ultimoNumeroDoc = configAtual.getUltimoNumeroDoc();
		
		
		if(ultimoNumeroDocConfigBoleto == ultimoNumeroDoc) {
			
			novoBoleto.setCondominio(boleto.getCondominio());
			novoBoleto.setModeloBoleto(boleto.getModeloBoleto());
			novoBoleto.setMorador(boleto.getMorador());
			novoBoleto.setDataDocumento(boleto.getDataDocumento());
			novoBoleto.setDataProcessamento(boleto.getDataProcessamento());
			novoBoleto.setDataVencimento(boleto.getDataVencimento());
			novoBoleto.setValor(boleto.getValor());
			novoBoleto.setNumeroDocumento(ultimoNumeroDoc + 1);
			novoBoleto.setNossoNumero(configAtual.getNossoNumeroAtual() + 1);
			
			Boletos boletoSalvo = boletosRepository.save(novoBoleto);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, boletoSalvo.getBoletoId()));
			
			configAtual.setNossoNumeroAtual(configAtual.getNossoNumeroAtual() + 1);
			configAtual.setUltimoNumeroDoc(ultimoNumeroDoc + 1);
			
			atualizarConfig(configAtual);
			
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoBoleto);
	}
	
//	private Boletos buscaPorId(Long codigo) {
//		
//		Boletos boletoSalvo = boletosRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
//		return boletoSalvo;
//	}
	
	private ConfigBoletos dadosConfigBoleto(Boletos boleto) {
		
		ModeloBoletos modeloBoletoSalvo = modeloBoletoService.buscaPorId(boleto.getModeloBoleto().getModeloBoletoId());
		
		ConfigBoletos configSalva = configBoletoService.buscaPorId(modeloBoletoSalvo.getConfigBoletos().getConfigBoletoId());
		return configSalva;
	}
	
	private void atualizarConfig(ConfigBoletos config) {
		
		Long codigo = config.getConfigBoletoId();
		
		configBoletoService.atualizarConfig(config, codigo);
	}

}
