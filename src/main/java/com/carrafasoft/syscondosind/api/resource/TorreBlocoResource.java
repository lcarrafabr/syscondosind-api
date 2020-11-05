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
import com.carrafasoft.syscondosind.api.model.TorresBlocos;
import com.carrafasoft.syscondosind.api.repository.TorreBlocoRepository;
import com.carrafasoft.syscondosind.api.service.TorreBlocoService;

@RestController
@RequestMapping("/blocos")
public class TorreBlocoResource {
	
	@Autowired
	private TorreBlocoRepository torreBlocoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private TorreBlocoService torreBlocoService;
	
	@GetMapping
	public List<TorresBlocos> listarTodos() {
		
		return torreBlocoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<TorresBlocos> cadastrarBloco(@Valid @RequestBody TorresBlocos bloco, HttpServletResponse response) {
		
		bloco.setNomeBloco(bloco.getNomeBloco().toUpperCase());
		
		TorresBlocos blocoSalvo = torreBlocoRepository.save(bloco);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, blocoSalvo.getTorreBlocoId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(blocoSalvo);
	}
	
	@GetMapping("/{codigo}")
	public Optional<TorresBlocos> findById(@PathVariable Long codigo) {
		
		return torreBlocoRepository.findById(codigo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerTorreBloco(@PathVariable Long codigo) {
		
		torreBlocoRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<TorresBlocos> atualizaBloco(@PathVariable Long codigo, @Valid @RequestBody TorresBlocos bloco) {
		
		TorresBlocos blocoSalvo = torreBlocoService.atualizaBloco(codigo, bloco);
		
		return ResponseEntity.ok(blocoSalvo);
	}
	
	

}
