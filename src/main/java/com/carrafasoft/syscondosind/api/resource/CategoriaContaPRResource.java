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
import com.carrafasoft.syscondosind.api.model.CategoriasContaPR;
import com.carrafasoft.syscondosind.api.repository.CategoriaContaPRRepository;
import com.carrafasoft.syscondosind.api.service.CategoriaContasPRService;

@RestController
@RequestMapping("/categoria-conta")
public class CategoriaContaPRResource {
	
	@Autowired
	private CategoriaContaPRRepository categoriaContaPRRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private CategoriaContasPRService categoriaContaService;
	
	
	@GetMapping
	public List<CategoriasContaPR> listarTodos() {
		return categoriaContaPRRepository.findAll();
	}
	
	
	@PostMapping
	public ResponseEntity<CategoriasContaPR> cadastrarCategoria(@Valid @RequestBody CategoriasContaPR categoria, HttpServletResponse response) {
		
		categoria.setNomeCategoriaConta(categoria.getNomeCategoriaConta().toUpperCase());
		
		CategoriasContaPR categoriaSalva = categoriaContaPRRepository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCategoriaContaId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<CategoriasContaPR> buscaPorId(@PathVariable Long codigo) {
		
		Optional<CategoriasContaPR> categoriaSalva = categoriaContaPRRepository.findById(codigo);
		
		return categoriaSalva.isPresent() ? ResponseEntity.ok(categoriaSalva.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerCategoria(@PathVariable Long codigo) {
		
		categoriaContaPRRepository.deleteById(codigo);
	}
	
	@PutMapping("{codigo}")
	public ResponseEntity<CategoriasContaPR> atualizaCategoria(@PathVariable Long codigo, @Valid @RequestBody CategoriasContaPR categoria) {
		
		CategoriasContaPR categoriaSalva = categoriaContaService.atualizaCategoria(codigo, categoria);
		
		return ResponseEntity.ok(categoriaSalva);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizaStatusAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		
		categoriaContaService.atualizaStatusAtivo(codigo, ativo);
	}

}
