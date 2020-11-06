package com.carrafasoft.syscondosind.api.resource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.Portarias;
import com.carrafasoft.syscondosind.api.repository.PortariasRepository;
import com.carrafasoft.syscondosind.api.repository.filter.PortariasFilter;
import com.carrafasoft.syscondosind.api.service.PortariaService;

@RestController
@RequestMapping("/portaria")
public class PortariaResource {
	
	@Autowired
	private PortariasRepository portariasRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PortariaService portariaService;
	
	@GetMapping
	public List<Portarias> pesquisar(PortariasFilter portariaFilter) {
		
		//return portariasRepository.findAll();
		return portariasRepository.filtrar(portariaFilter);
	}

	
	@PostMapping
	public ResponseEntity<Portarias> cadastrarVisitante(@Valid @RequestBody Portarias portaria, HttpServletResponse response) {
		
		Portarias portariaSalva = portariasRepository.save(portaria);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, portariaSalva.getControlePortariaId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(portariaSalva);
	}
	
	@GetMapping("/{codigo}")
	public Optional<Portarias> buscaPorId(@PathVariable Long codigo) {
		
		return portariasRepository.findById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Portarias> atualizaVisita(@PathVariable Long codigo, @Valid @RequestBody Portarias portaria) {
		
		Portarias portariaSalva = portariaService.atualizarPortaria(codigo, portaria);
		return ResponseEntity.ok(portariaSalva);
	}
	
	/*****************************************************************************************************************************************************/
	
	
}
