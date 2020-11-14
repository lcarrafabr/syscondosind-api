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
import com.carrafasoft.syscondosind.api.model.ListaConvidadosPessoas;
import com.carrafasoft.syscondosind.api.repository.ListaConvidadoPessoaRepository;
import com.carrafasoft.syscondosind.api.service.ListaConvidadoPessoaService;

@RestController
@RequestMapping("/lista")
public class ListaPessoaConvidadoResource {
	
	@Autowired
	private ListaConvidadoPessoaRepository listaConvidadoPessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ListaConvidadoPessoaService listaConvidadoPessoaService;
	
	
	@GetMapping
	public List<ListaConvidadosPessoas> listarTodos() {
		return listaConvidadoPessoaRepository.findAll();
	}
	
	@GetMapping("/{codigo}/listar")
	public List<ListaConvidadosPessoas> listarTodosPorLista(@PathVariable Long codigo) {
		
		return listaConvidadoPessoaRepository.buscaPorLista(codigo);
	}
	
	@PostMapping
	public ResponseEntity<ListaConvidadosPessoas> cadastrarLista(@Valid @RequestBody ListaConvidadosPessoas lista, HttpServletResponse response) {
		
		ListaConvidadosPessoas listaSalva = listaConvidadoPessoaRepository.save(lista);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, listaSalva.getListaConvidadoPessoaId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(listaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<ListaConvidadosPessoas> buscaPorId(@PathVariable Long codigo) {
		
		Optional<ListaConvidadosPessoas> listaSalva = listaConvidadoPessoaRepository.findById(codigo);
		
		return listaSalva.isPresent() ? ResponseEntity.ok(listaSalva.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerPessoaLista(@PathVariable Long codigo) {
		
		listaConvidadoPessoaRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<ListaConvidadosPessoas> atualizarPessoaLista(@PathVariable Long codigo, @Valid @RequestBody ListaConvidadosPessoas lista) {
		
		ListaConvidadosPessoas listaSalva = listaConvidadoPessoaService.atualizarPessoaLista(codigo, lista);
		
		return ResponseEntity.ok(listaSalva);
	}

}
