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
import com.carrafasoft.syscondosind.api.model.Permissoes;
import com.carrafasoft.syscondosind.api.repository.PermissoesRepository;
import com.carrafasoft.syscondosind.api.service.PermissoesService;

@RestController
@RequestMapping("/permissoes-sistema")
public class PermissaoResource {
	
	@Autowired
	private PermissoesRepository permissoesRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PermissoesService permissoesService;
	
	@GetMapping
	public List<Permissoes> listarTodos() {
		
		return permissoesRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Permissoes> cadastrarPermissoes(@Valid @RequestBody Permissoes permissao, HttpServletResponse response) {
		
		permissao.setPermissao(permissao.getPermissao().toUpperCase());
		
		Permissoes permissaoSalva = permissoesRepository.save(permissao);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, permissaoSalva.getPermissaoId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(permissaoSalva);
	}
	
	@GetMapping("/{codigo}")
	public Optional<Permissoes> buscaPorId(@PathVariable Long codigo) {
		
		return permissoesRepository.findById(codigo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removePermissao(@PathVariable Long codigo) {
		
		permissoesRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Permissoes> atualizaPermissao(@PathVariable Long codigo, @Valid @RequestBody Permissoes permissao) {
		
		Permissoes permissaoSalva = permissoesService.atualizaPermissao(codigo, permissao);
		return ResponseEntity.ok(permissaoSalva);
	}

}
