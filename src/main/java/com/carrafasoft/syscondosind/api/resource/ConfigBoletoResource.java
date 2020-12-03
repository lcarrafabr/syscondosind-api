package com.carrafasoft.syscondosind.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.ConfigBoletos;
import com.carrafasoft.syscondosind.api.repository.ConfigBoletoRepository;
import com.carrafasoft.syscondosind.api.service.ConfigBoletoService;

@RestController
@RequestMapping("/configurar-boleto")
public class ConfigBoletoResource {
	
	@Autowired
	private ConfigBoletoRepository configBoletoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ConfigBoletoService configService;
	
	
	@GetMapping
	public List<ConfigBoletos> listarTodos() {
		
		return configBoletoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<ConfigBoletos> cadastrarConfiguracaoBoleto(@Valid @RequestBody ConfigBoletos config, HttpServletResponse response) {
		
		ConfigBoletos configSalvo = configBoletoRepository.save(config);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, configSalvo.getConfigBoletoId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(configSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<ConfigBoletos> buscaPorId(@PathVariable Long codigo) {
		
		Optional<ConfigBoletos> configSalva = configBoletoRepository.findById(codigo);
		
		return configSalva.isPresent() ? ResponseEntity.ok(configSalva.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerConfig(@PathVariable Long codigo) {
		
		configBoletoRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<ConfigBoletos> atualizarConfig(@PathVariable Long codigo, @Valid @RequestBody ConfigBoletos config) {
		
		ConfigBoletos configSalva = configService.atualizarConfig(config, codigo);
		
		return ResponseEntity.ok(configSalva);
	}

}
