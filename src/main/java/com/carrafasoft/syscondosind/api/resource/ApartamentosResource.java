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
import com.carrafasoft.syscondosind.api.model.Apartamentos;
import com.carrafasoft.syscondosind.api.repository.ApartamentoRepository;
import com.carrafasoft.syscondosind.api.service.ApartamentosService;

@RestController
@RequestMapping("/apartamentos")
public class ApartamentosResource {
	
	@Autowired
	private ApartamentoRepository apartamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ApartamentosService apartamentoService;
	
	@GetMapping
	public List<Apartamentos> listarTodos() {
		
		return apartamentoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Apartamentos> cadastrarApartamento(@Valid @RequestBody Apartamentos apartamentos, HttpServletResponse response) {
		
		apartamentos.setNomeApartamento(apartamentos.getNomeApartamento().toUpperCase());
		
		Apartamentos apSalvo = apartamentoRepository.save(apartamentos);	
		publisher.publishEvent(new RecursoCriadoEvent(this, response, apSalvo.getApartamentoId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(apSalvo);
	}
	
	@GetMapping("/{codigo}")
	public Optional<Apartamentos> buscarPorId(@PathVariable Long codigo) {
		
		return apartamentoRepository.findById(codigo);
		
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerAP(@PathVariable Long codigo) {
		
		apartamentoRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Apartamentos> atualizaApartamento(@PathVariable Long codigo, @Valid @RequestBody Apartamentos apartamentos) {
		
		Apartamentos apSalvo = apartamentoService.atualizaApartamento(codigo, apartamentos);
		
		return ResponseEntity.ok(apSalvo);
	}

}
