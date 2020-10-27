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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.Condominios;
import com.carrafasoft.syscondosind.api.repository.CondominioRepository;

@RestController
@RequestMapping("/condominios")
public class CondominioResource {
	
	@Autowired
	private CondominioRepository condominioRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Condominios> listar() {
		
		return condominioRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Condominios> criarCondominio(@Valid @RequestBody Condominios condominios, HttpServletResponse response) {
		
		Condominios condominioSalvo =  condominioRepository.save(condominios);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, condominioSalvo.getCondominioId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(condominioSalvo);
		
	}
	
	@GetMapping("/{codigo}")
	public Optional<Condominios> buscarPeloId(@PathVariable Long codigo) {
		
		return condominioRepository.findById(codigo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerCondominio(@PathVariable Long codigo) {
		
		condominioRepository.deleteById(codigo);
	}

}
