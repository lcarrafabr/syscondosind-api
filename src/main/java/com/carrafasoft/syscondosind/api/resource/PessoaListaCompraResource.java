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
import com.carrafasoft.syscondosind.api.model.PessoaListaCompras;
import com.carrafasoft.syscondosind.api.repository.PessoaListaCompraRepository;
import com.carrafasoft.syscondosind.api.service.PessoaListaCompraService;

@RestController
@RequestMapping("/pessoa-lista-compra")
public class PessoaListaCompraResource {
	
	@Autowired
	private PessoaListaCompraRepository pessoaListaCompraRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PessoaListaCompraService pessoaListaCompraService;
	
	
	@GetMapping
	public List<PessoaListaCompras> listarTodos() {
		
		return pessoaListaCompraRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<PessoaListaCompras> cadastrarPessoaLista(@Valid @RequestBody PessoaListaCompras pessoaList, HttpServletResponse response) {
		
		PessoaListaCompras pessoaListSalva = pessoaListaCompraRepository.save(pessoaList);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaListSalva.getPessoaListaCompraID()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaListSalva);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<PessoaListaCompras> buscaPorId(@PathVariable Long codigo) {
		
		Optional<PessoaListaCompras> pessoaListSalva = pessoaListaCompraRepository.findById(codigo);
		
		return pessoaListSalva.isPresent() ? ResponseEntity.ok(pessoaListSalva.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerPessoaListaCompra(@PathVariable Long codigo) {
		
		pessoaListaCompraRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<PessoaListaCompras> atualizarPessoaListCompras(@PathVariable Long codigo, @Valid @RequestBody PessoaListaCompras pessoaList) {
		
		PessoaListaCompras pessoaListSalva = pessoaListaCompraService.atualizarPessoaListaCompra(codigo, pessoaList);
		
		return ResponseEntity.ok(pessoaListSalva);
	}
	
	@PutMapping("/{codigo}/permite-aprovar")
	public void atualizaPermiteAprovar(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		
		pessoaListaCompraService.atualizaStatusAtivoPermiteAprovar(codigo, ativo);
	}
	
	@PutMapping("/{codigo}/permite-criar-solicitacao")
	public void atualizaSatusPermiteCriarSolicitacao(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		
		pessoaListaCompraService.atualizaStatusAtivoPermiteCriarSolicitacao(codigo, ativo);
	}

}
