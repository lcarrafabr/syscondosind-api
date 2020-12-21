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
import com.carrafasoft.syscondosind.api.model.CategoriasFornecedores;
import com.carrafasoft.syscondosind.api.repository.CategoriasFornecedoresRepository;
import com.carrafasoft.syscondosind.api.service.CategoriasFornecedoresService;

@RestController
@RequestMapping("/categorias-fornecedores")
public class CategoriasFornecedoresResource {
	
	@Autowired
	private CategoriasFornecedoresRepository categoriasFornecedoresRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private CategoriasFornecedoresService categoriaService;
	

	@GetMapping
	public List<CategoriasFornecedores> listarTodos() {
		
		return categoriasFornecedoresRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<CategoriasFornecedores> cadastrarCategoria(@Valid @RequestBody CategoriasFornecedores categoria, HttpServletResponse response) {
		
		CategoriasFornecedores categoriaSalva = categoriasFornecedoresRepository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCategoriaFornecedorId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<CategoriasFornecedores> buscaPorId(@PathVariable Long codigo) {
		
		Optional<CategoriasFornecedores> categoriaSalva = categoriasFornecedoresRepository.findById(codigo);
		
		return categoriaSalva.isPresent() ? ResponseEntity.ok(categoriaSalva.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerCategoria(@PathVariable Long codigo) {
		
		categoriasFornecedoresRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<CategoriasFornecedores> atualizarCategoria(@PathVariable Long codigo, @Valid @RequestBody CategoriasFornecedores categoria) {
		
		CategoriasFornecedores categoriaSalva = categoriaService.atualizarCategoriaFornecedores(codigo, categoria);
		
		return ResponseEntity.ok(categoriaSalva);
	}
	
	@PutMapping("/{codigo}/ativo")
	public void atualizaStatusAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		
		categoriaService.atualizaStatusAtivo(codigo, ativo);
	}

}
