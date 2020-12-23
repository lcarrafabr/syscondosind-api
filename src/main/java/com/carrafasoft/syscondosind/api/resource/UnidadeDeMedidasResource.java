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
import com.carrafasoft.syscondosind.api.model.UnidadeDeMedidas;
import com.carrafasoft.syscondosind.api.repository.UnidadeDeMedidaRepository;
import com.carrafasoft.syscondosind.api.service.UnidadeMedidaService;

@RestController
@RequestMapping("unidade-de-medidas")
public class UnidadeDeMedidasResource {
	
	@Autowired
	private UnidadeDeMedidaRepository unidadeDeMedidaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private UnidadeMedidaService unidadeMedidaService;
	
	
	@GetMapping
	public List<UnidadeDeMedidas> listarTodos() {
		
		return unidadeDeMedidaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<UnidadeDeMedidas> cadastrarUnMedida(@Valid @RequestBody UnidadeDeMedidas unidadeMedida, HttpServletResponse response) {
		
		UnidadeDeMedidas unidadeMedidaSalva = unidadeDeMedidaRepository.save(unidadeMedida);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, unidadeMedidaSalva.getUnidadeDeMedidaId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(unidadeMedidaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<UnidadeDeMedidas> buscaPorId(@PathVariable Long codigo) {
		
		Optional<UnidadeDeMedidas> unidadeMedidaSalva = unidadeDeMedidaRepository.findById(codigo);
		
		return unidadeMedidaSalva.isPresent() ? ResponseEntity.ok(unidadeMedidaSalva.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerUnidadeMedida(@PathVariable Long codigo) {
		
		unidadeDeMedidaRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<UnidadeDeMedidas> atualizarUnidadeMedida(@PathVariable Long codigo, @Valid @RequestBody UnidadeDeMedidas unidadeMedida) {
		
		UnidadeDeMedidas unidadeMedidaSalva = unidadeMedidaService.atualizarUnidadeMedida(codigo, unidadeMedida);
		
		return ResponseEntity.ok(unidadeMedidaSalva);
	}

}
