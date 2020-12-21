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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.Telefones;
import com.carrafasoft.syscondosind.api.repository.TelefoneRepository;
import com.carrafasoft.syscondosind.api.service.TelefoneService;

@RestController
@RequestMapping("/telefones")
public class TelefonesResource {

	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private TelefoneService telefoneService;
	
	
	@GetMapping("/listar-todos")
	public List<Telefones> listarTodos() {
		
		return telefoneRepository.findAll();
	}
	
	@GetMapping
	public List<Telefones> listarTodosByCondominioByPessoa(@RequestParam("condominioID") String condominioId, @RequestParam("pessoaID") String pessoaID) {
		
		return telefoneRepository.findByPessoaByCondominio(
				Long.parseLong(pessoaID), 
				Long.parseLong(condominioId)
				);
	}
	
	@PostMapping
	public ResponseEntity<Telefones> cadastrarTelefone(@Valid @RequestBody Telefones telefone, HttpServletResponse response) {
		
		Telefones telefoneSalvo = telefoneRepository.save(telefone);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, telefoneSalvo.getTelefoneId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(telefoneSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Telefones> buscaPorId(@PathVariable Long codigo) {
		
		Optional<Telefones> telefoneSalvo = telefoneRepository.findById(codigo);
		
		return telefoneSalvo.isPresent() ? ResponseEntity.ok(telefoneSalvo.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerTelefone(@PathVariable Long codigo) {
		
		telefoneRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Telefones> atualizaTelefone(@PathVariable Long codigo, @Valid @RequestBody Telefones telefone) {
		
		Telefones telefoneSalvo = telefoneService.atualizaTelefone(codigo, telefone);
		
		return ResponseEntity.ok(telefoneSalvo);
	}
}
