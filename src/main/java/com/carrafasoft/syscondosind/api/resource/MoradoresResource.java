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
import com.carrafasoft.syscondosind.api.model.Moradores;
import com.carrafasoft.syscondosind.api.repository.MoradorRepository;
import com.carrafasoft.syscondosind.api.service.MoradoresService;

@RestController
@RequestMapping("/moradores")
public class MoradoresResource {
	
	@Autowired
	private MoradorRepository moradorRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MoradoresService moradorService;
	
	@GetMapping
	public List<Moradores> listarTodos() {
		
		return moradorRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Moradores> cadastrarMorador(@Valid @RequestBody Moradores moradoror, HttpServletResponse response) {
		
		Moradores moradorSalvo = moradorRepository.save(moradoror);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, moradorSalvo.getMoradorId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(moradorSalvo);
	}
	
	@GetMapping("/{codigo}")
	public Optional<Moradores> buscaMoradorPorId(@PathVariable Long codigo) {
		
		return moradorRepository.findById(codigo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerMorador(@PathVariable Long codigo) {
		
		moradorRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Moradores> atualizaMorador(@PathVariable Long codigo, @Valid @RequestBody Moradores morador) {
		
		Moradores moradorSalvo = moradorService.atualizaMorador(codigo, morador);
		return ResponseEntity.ok(moradorSalvo);
	}

}
