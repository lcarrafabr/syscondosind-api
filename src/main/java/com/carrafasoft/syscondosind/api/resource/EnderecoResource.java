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
import com.carrafasoft.syscondosind.api.model.Enderecos;
import com.carrafasoft.syscondosind.api.repository.EnderecoRepository;
import com.carrafasoft.syscondosind.api.service.EnderecosService;

@RestController
@RequestMapping("/enderecos")
public class EnderecoResource {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private EnderecosService enderecoService;
	
	@GetMapping
	public List<Enderecos> listarTodos() {
		
		return enderecoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Enderecos> cadastrarEndereco(@Valid @RequestBody Enderecos endereco, HttpServletResponse response) {
		
		Enderecos enderecoSalvo = enderecoRepository.save(endereco);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, enderecoSalvo.getEnderecoId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(enderecoSalvo);
	}

	
	@GetMapping("/{codigo}")
	public ResponseEntity<Enderecos> buscaPorId(@PathVariable Long codigo) {
		
		Optional<Enderecos> enderecoSalvo = enderecoRepository.findById(codigo);
		
		return enderecoSalvo.isPresent() ? ResponseEntity.ok(enderecoSalvo.get()) : ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerEndereco(@PathVariable Long codigo) {
		
		enderecoRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Enderecos> atualizaEndereco(@PathVariable Long codigo, @Valid @RequestBody Enderecos endereco ) {
		
		Enderecos enderecoSalvo = enderecoService.atualizaEndereco(endereco, codigo);
		
		return ResponseEntity.ok(enderecoSalvo);
	}
}
