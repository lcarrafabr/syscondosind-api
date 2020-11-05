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
import com.carrafasoft.syscondosind.api.model.Pessoas;
import com.carrafasoft.syscondosind.api.repository.PessoaRepository;
import com.carrafasoft.syscondosind.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PessoaService pessoaService;
	
	
	@GetMapping
	public List<Pessoas> listarPessoas() {
		
		return pessoaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Pessoas> cadastrarPessoa(@Valid @RequestBody Pessoas pessoas, HttpServletResponse response) {
		
		
		Pessoas pessoaSalva = pessoaRepository.save(pessoas);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getPessoaId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@GetMapping("/{codigo}")
	public Optional<Pessoas> buscaPeloId(@PathVariable Long codigo) {
		
		return pessoaRepository.findById(codigo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerPessoa(@PathVariable Long codigo) {
		
		pessoaRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoas> atualizarPessoa(@PathVariable Long codigo, @Valid @RequestBody Pessoas pessoas) {
		
		Pessoas pessoaSalva = pessoaService.atualizaPessoa(codigo, pessoas);
		
		return ResponseEntity.ok(pessoaSalva);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizaStatusPessoaAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		
		pessoaService.atualizaStatusAtivo(codigo, ativo);
	}
}
