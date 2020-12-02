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
import com.carrafasoft.syscondosind.api.model.Beneficiario;
import com.carrafasoft.syscondosind.api.repository.BeneficiarioRepository;
import com.carrafasoft.syscondosind.api.service.BeneficiarioService;

@RestController
@RequestMapping("/beneficiarios")
public class BeneficiariosResource {
	
	@Autowired
	private BeneficiarioRepository beneficiarioRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private BeneficiarioService beneficiarioService;
	
	
	@GetMapping
	public List<Beneficiario> listarTodos() {
		
		return beneficiarioRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Beneficiario> cadastrarBeneficiario(@Valid @RequestBody Beneficiario beneficiario, HttpServletResponse response) {
		
		Beneficiario beneficiarioSalvo = beneficiarioRepository.save(beneficiario);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, beneficiarioSalvo.getBeneficiarioId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(beneficiarioSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Beneficiario> buscaPorId(@PathVariable Long codigo) {
		
		Optional<Beneficiario> beneficiarioSalvo = beneficiarioRepository.findById(codigo);
		
		return beneficiarioSalvo.isPresent() ? ResponseEntity.ok(beneficiarioSalvo.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerBeneficiario(@PathVariable Long codigo) {
		
		beneficiarioRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Beneficiario> atualizaBeneficiario(@PathVariable Long codigo, @Valid @RequestBody Beneficiario beneficiario) {
		
		Beneficiario beneficiarioSalvo = beneficiarioService.atualizaBeneficiario(beneficiario, codigo);
		
		return ResponseEntity.ok(beneficiarioSalvo);
	}

}
