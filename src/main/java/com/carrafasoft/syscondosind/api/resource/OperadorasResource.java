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
import com.carrafasoft.syscondosind.api.model.Operadoras;
import com.carrafasoft.syscondosind.api.repository.OperadoraRepository;
import com.carrafasoft.syscondosind.api.service.OperadoraService;

@RestController
@RequestMapping("/operadoras")
public class OperadorasResource {
	
	@Autowired
	private OperadoraRepository operadoraRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private OperadoraService operadoraService;
	
	
	@GetMapping
	public List<Operadoras> listarTodos() {
		
		return operadoraRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Operadoras> cadastrarOperadora(@Valid @RequestBody Operadoras operadora, HttpServletResponse response) {
		
		Operadoras operadoraSalva = operadoraRepository.save(operadora);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, operadoraSalva.getOperadoraId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(operadoraSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Operadoras> buscaPorId(@PathVariable Long codigo) {
		
		Optional<Operadoras> operadoraSalva = operadoraRepository.findById(codigo);
		
		return operadoraSalva.isPresent() ? ResponseEntity.ok(operadoraSalva.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerOperadora(@PathVariable Long codigo) {
		
		operadoraRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Operadoras> atualizaOperadora(@PathVariable Long codigo, @Valid @RequestBody Operadoras operadora) {
		
		Operadoras operadoraSalva = operadoraService.atualizaOperadora(operadora, codigo);
		
		return ResponseEntity.ok(operadoraSalva);
		
	}

}
