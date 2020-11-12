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
import com.carrafasoft.syscondosind.api.model.ContasBancarias;
import com.carrafasoft.syscondosind.api.repository.ContaBancariaRepository;
import com.carrafasoft.syscondosind.api.service.ContasBancariasService;

@RestController
@RequestMapping("/contas-bancarias")
public class ContasBancariasResource {
	
	@Autowired
	private ContaBancariaRepository contaBancariaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ContasBancariasService contasBancariasService;
	
	@GetMapping
	private List<ContasBancarias> listarTodos() {
		
		return contaBancariaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<ContasBancarias> cadastrarContabancaria(@Valid @RequestBody ContasBancarias conta, HttpServletResponse response) {
		
		ContasBancarias contaSalva = contaBancariaRepository.save(conta);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, contaSalva.getContaBancariaId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(contaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<ContasBancarias> buscaPorId(@PathVariable Long codigo) {
		
		Optional<ContasBancarias> contaSalva = contaBancariaRepository.findById(codigo);
		return contaSalva.isPresent() ? ResponseEntity.ok(contaSalva.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerConta(@PathVariable Long codigo) {
		contaBancariaRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<ContasBancarias> atualizaContaBancaria(@PathVariable Long codigo, @Valid @RequestBody ContasBancarias conta) {
		
		ContasBancarias contaSalva = contasBancariasService.atualizaContabancaria(codigo, conta);
		return ResponseEntity.ok(contaSalva);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizaStatusAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		
		contasBancariasService.atualizaStatusAtivo(codigo, ativo);
	}

}
