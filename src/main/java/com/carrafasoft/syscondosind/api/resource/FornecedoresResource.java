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
import com.carrafasoft.syscondosind.api.model.Fornecedores;
import com.carrafasoft.syscondosind.api.repository.FornecedorRepository;
import com.carrafasoft.syscondosind.api.service.FornecedoresService;

@RestController
@RequestMapping("/fornecedores")
public class FornecedoresResource {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private FornecedoresService fornecedorService;
	
	
	@GetMapping
	public List<Fornecedores> listarTodos() {
		
		return fornecedorRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Fornecedores> cadastrarFornecedor(@Valid @RequestBody Fornecedores fornecedor, HttpServletResponse response) {
		
		Fornecedores fornecedorSalvo = fornecedorRepository.save(fornecedor);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalvo.getFornecedorId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Fornecedores> buscaPorId(@PathVariable Long codigo) {
		
		Optional<Fornecedores> fornecedorSalvo = fornecedorRepository.findById(codigo);
		
		return fornecedorSalvo.isPresent() ? ResponseEntity.ok(fornecedorSalvo.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	public void removerFornecedor(@PathVariable Long codigo) {
		
		fornecedorRepository.deleteById(codigo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Fornecedores> atualizarFornecedor(@PathVariable Long codigo, @Valid @RequestBody Fornecedores fornecedor) {
		
		Fornecedores fornecdorsalvo = fornecedorService.atualizarFornecedor(codigo, fornecedor);
		
		return ResponseEntity.ok(fornecdorsalvo);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizaStatusAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		
		fornecedorService.atualizaStatusAtivo(codigo, ativo);
	}
}
