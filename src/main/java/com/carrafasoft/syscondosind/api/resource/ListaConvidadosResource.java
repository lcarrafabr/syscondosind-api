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
import com.carrafasoft.syscondosind.api.model.ListaConvidados;
import com.carrafasoft.syscondosind.api.repository.ListaConvidadosRepository;
import com.carrafasoft.syscondosind.api.service.ListaConvidadosService;

@RestController
@RequestMapping("lista-convidados")
public class ListaConvidadosResource {
	
	@Autowired
	private ListaConvidadosRepository listaConvidadosRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ListaConvidadosService listaConvidadosService;
	
	@GetMapping
	public List<ListaConvidados> listarTodos() {
		return listaConvidadosRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<ListaConvidados> cadastrarListaConvidados(@Valid @RequestBody ListaConvidados lista, HttpServletResponse response) {
		
		ListaConvidados listaSalva = listaConvidadosRepository.save(lista);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, listaSalva.getListaId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(listaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<ListaConvidados> buscaPorId(@PathVariable Long codigo) {
		
		Optional<ListaConvidados> listaSalva = listaConvidadosRepository.findById(codigo);
		
		return listaSalva.isPresent() ? ResponseEntity.ok(listaSalva.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerListaConvidados(@PathVariable Long codigo) {
		
		listaConvidadosRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<ListaConvidados> atualizaListaConvidados(@PathVariable Long codigo, @Valid @RequestBody ListaConvidados lista) {
		
		ListaConvidados listaSalva = listaConvidadosService.atualizaListaConvidados(codigo, lista);
		
		return ResponseEntity.ok(listaSalva);
	}

}
