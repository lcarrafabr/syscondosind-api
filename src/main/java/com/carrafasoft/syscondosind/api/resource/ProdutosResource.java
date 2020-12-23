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
import com.carrafasoft.syscondosind.api.model.Produtos;
import com.carrafasoft.syscondosind.api.repository.ProdutoRepository;
import com.carrafasoft.syscondosind.api.service.ProdutoService;

@RestController
@RequestMapping("produtos")
public class ProdutosResource {

	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ProdutoService produtoService;
	
	
	@GetMapping
	public List<Produtos> listarTodos() {
		return produtoRepository.findAll();
		//return produtoRepository.listarProdutosComCalculoDeItensPorPreco();
	}
	
	@PostMapping
	public ResponseEntity<Produtos> cadastrarProduto(@Valid @RequestBody Produtos produto, HttpServletResponse response) {
		
		Produtos produtoSalvo = produtoRepository.save(produto);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalvo.getProdutoId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Produtos> buscaPorId(@PathVariable Long codigo) {
		
		Optional<Produtos> produtoSalvo = produtoRepository.findById(codigo);
		
		return produtoSalvo.isPresent() ? ResponseEntity.ok(produtoSalvo.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerProduto(@PathVariable Long codigo) {
		
		produtoRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Produtos> atualizarProduto(@PathVariable Long codigo,@Valid @RequestBody Produtos produto) {
		
		Produtos produtoSalvo = produtoService.atualizarProduto(codigo, produto);
		
		return ResponseEntity.ok(produtoSalvo);
	}
}
