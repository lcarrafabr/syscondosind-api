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
import com.carrafasoft.syscondosind.api.model.RepresentantesCondominio;
import com.carrafasoft.syscondosind.api.repository.RepresentanteCondRepository;
import com.carrafasoft.syscondosind.api.service.RepresentantesCondService;

@RestController
@RequestMapping("representantes-condominio")
public class RepresentanteCondResource {
	
	@Autowired
	private RepresentanteCondRepository representanteCondRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private RepresentantesCondService representanteService;
	
	@GetMapping
	public List<RepresentantesCondominio> listarTodos() {
		
		return representanteCondRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<RepresentantesCondominio> cadastrarRepresentante(@Valid @RequestBody RepresentantesCondominio representantesCondominio, HttpServletResponse response) {
		
		RepresentantesCondominio representanteSalvo = representanteCondRepository.save(representantesCondominio);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, representanteSalvo.getRepresentanteCondominioId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(representanteSalvo);
	}
	
	@GetMapping("/{codigo}")
	public Optional<RepresentantesCondominio> buscaPeloId(@PathVariable Long codigo) {
		
		return representanteCondRepository.findById(codigo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerRepresentante(@PathVariable Long codigo) {
		
		representanteCondRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<RepresentantesCondominio> atualizaRepresentante(@PathVariable Long codigo, @Valid @RequestBody RepresentantesCondominio representante) {
		
		RepresentantesCondominio representanteSalvo = representanteService.atualizaRepresentante(codigo, representante);
		
		return ResponseEntity.ok(representanteSalvo);
	}
	
	/*************************************************************************************************************************************************************************************/
	
	@GetMapping("/ativos")
	public List<RepresentantesCondominio> listarRepresentantesAtivos() {
		
		return representanteCondRepository.findRepresentanteAtivo();
	}
	
	@GetMapping("/inativos")
	public List<RepresentantesCondominio> listarRepresentantesInativos() {
		
		return representanteCondRepository.findRepresentantesInativos();
	}

}
