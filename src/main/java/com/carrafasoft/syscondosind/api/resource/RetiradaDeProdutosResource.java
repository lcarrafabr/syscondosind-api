package com.carrafasoft.syscondosind.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.model.RetiradaDeProdutos;
import com.carrafasoft.syscondosind.api.repository.RetiradaDeProdutoRepository;
import com.carrafasoft.syscondosind.api.service.RetiradaProdutosService;

@RestController
@RequestMapping("retirar-produto")
public class RetiradaDeProdutosResource {

	@Autowired
	private RetiradaDeProdutoRepository retiradaDeProdutoRepository;
		
	@Autowired
	private RetiradaProdutosService retiradaDeProdutoService;
	
	@GetMapping
	public List<RetiradaDeProdutos> listarTodos() {
		
		return retiradaDeProdutoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrarRetirada(@Valid @RequestBody RetiradaDeProdutos retiradaProd, HttpServletResponse response) {
		
		ResponseEntity<?> retornoResponse = retiradaDeProdutoService.cadastraRetiradaProduto(retiradaProd, response);
		
		return retornoResponse;
	}
	
	@GetMapping("/{codigo}/estornar-retirada")
	public ResponseEntity<RetiradaDeProdutos> estornoRetiradaProduto(@PathVariable Long codigo, HttpServletResponse response, @RequestParam("pessoaID") String codigoPessoa) {
		
		RetiradaDeProdutos retiradaSalva = retiradaDeProdutoService.estornoRetiradaProduto(codigo, response, codigoPessoa);
		
		return ResponseEntity.ok(retiradaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<RetiradaDeProdutos> buscaPorId(@PathVariable Long codigo) {
		
		Optional<RetiradaDeProdutos> retiradaSalva = retiradaDeProdutoRepository.findById(codigo);
		
		return retiradaSalva.isPresent() ? ResponseEntity.ok(retiradaSalva.get()) : ResponseEntity.notFound().build();
	}
	
	
	
}
