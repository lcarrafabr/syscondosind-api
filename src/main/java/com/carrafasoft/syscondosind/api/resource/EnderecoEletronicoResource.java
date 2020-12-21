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
import com.carrafasoft.syscondosind.api.model.EnderecosEletronicos;
import com.carrafasoft.syscondosind.api.repository.EnderecoEletronicoRepository;
import com.carrafasoft.syscondosind.api.service.EnderecoEletronicoService;

@RestController
@RequestMapping("/enderecos-eletronicos")
public class EnderecoEletronicoResource {
	
	@Autowired
	private EnderecoEletronicoRepository enderecoEletronicoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private EnderecoEletronicoService enderecoEletronicoSerevice;
	
	
	@GetMapping
	public List<EnderecosEletronicos> listarTodos() {
		
		return enderecoEletronicoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<EnderecosEletronicos> cadastrarEnderecoEletronico(@Valid @RequestBody EnderecosEletronicos endElet, HttpServletResponse response) {
		
		EnderecosEletronicos endEletSalvo = enderecoEletronicoRepository.save(endElet);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, endEletSalvo.getEndEletronicoId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(endEletSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<EnderecosEletronicos> buscaPorId(@PathVariable Long codigo) {
		
		Optional<EnderecosEletronicos> endEletSalvo = enderecoEletronicoRepository.findById(codigo);
		
		return endEletSalvo.isPresent() ? ResponseEntity.ok(endEletSalvo.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerEndEletronico(@PathVariable Long codigo) {
		
		enderecoEletronicoRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<EnderecosEletronicos> atualizarEndEletronico(@PathVariable Long codigo, @Valid @RequestBody EnderecosEletronicos endEletronico) {
		
		EnderecosEletronicos endEletronicoSalvo = enderecoEletronicoSerevice.atualizarEndEletronico(codigo, endEletronico);
		
		return ResponseEntity.ok(endEletronicoSalvo);
	}

}
